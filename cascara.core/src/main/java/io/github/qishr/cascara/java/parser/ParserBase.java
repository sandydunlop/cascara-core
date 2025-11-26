package io.github.qishr.cascara.java.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import io.github.qishr.cascara.anno.Here;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.ast.expressions.MethodInvocation;
import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.expressions.PrimaryNoNewArray;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public abstract class ParserBase {
    private static final Token EOF = new Token(TokenType.EOF, "", 0, 0, 0, null);
    private static final String PARSER_CLASS_NAME = Parser.class.getName();

    private String path = null;

    // Reporting settings
    protected Reporter reporter = null;
    private boolean reportStack = false;
    private boolean reportTokens = false;
    private boolean reportTrace = true;

    // Package-visible for unit tests
    List<Token> tokens;
    int tokenIndex;
    int highestIndex = 0;
    int highestLine = 0;
    int stackLimit = 512;

    Stack<Integer> savedTokenPositions = new Stack<>();
    private int stackSize = 0;
    private HashMap<String,Integer> pushByMethod = new HashMap<>();

    protected ParserBase(Reporter reporter) {
        this.reporter = reporter;
    }

    public void stats() {
       // Sort the entries by value and collect the keys
        List<String> sortedKeys = pushByMethod.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
        for (String key : sortedKeys) {
            reporter.reportInfo("%s %d", key, pushByMethod.get(key));
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<Token> getTokens() {
        return tokens;
    }

    public CompilationUnit parse(String source) throws SyntaxException {
        boolean verbose = false;

        this.tokens = Tokenizer.tokenize(reporter, source, verbose, false);
        this.tokenIndex = 0;

        // Start the parsing from the top-level grammar rule
        return parseCompilationUnit();
    }

    protected abstract CompilationUnit parseCompilationUnit() throws SyntaxException;

    protected Token consume() throws SyntaxException {
        if (isAtEnd()) {
            throw new SyntaxException("Unexpected end of file.");
        }
        Token currentToken = peek();
        advance();
        // reportToken();
        return currentToken;
    }

    private String codeLocation() {
        if (path == null) {
            return "line " + peek().line();
        } else {
            return path + ":" + peek().line();
        }
    }

    /// Consumes the current token and returns it, OR throws an error.
    protected Token consume(TokenType expectedType) throws SyntaxException {
        if (isAtEnd()) {
            throw new SyntaxException("Unexpected end of file. Expected " + expectedType);
        }
        Token currentToken = peek();
        if (currentToken.type() != expectedType) {
            reporter.reportDebug("tokenIndex = %d", tokenIndex);
            reporter.reportDebug("Upcoming tokens: " + upcomingTokens());
            throw new SyntaxException("Expected token " + expectedType +
                    " but found " + currentToken.type() +
                    " at " + codeLocation());
        }
        // Only advance the index if the token is valid
        advance();
        // reportToken();
        return currentToken;
    }

    private void advance() {
        tokenIndex++;
        if (tokenIndex > highestIndex) {
            highestIndex = tokenIndex;
        }
        if (peek().line() > highestLine) {
            highestLine = peek().line();
        }
    }

    private void reportToken() {
        if (reportTokens) {
            Token token = peek();
            reporter.reportTrace("TOKEN %d: %s", tokenIndex, token.toString());
            // token = peek(2);
            // reporter.reportDebug("NEXT %d: %s", tokenIndex + 1, token.toString());
        }
    }

    protected void unexpectedToken() throws SyntaxException {
        Token currentToken = peek();
        reporter.reportDebug("tokenIndex = %d", tokenIndex);
        throw new SyntaxException("Unexpected " + currentToken.type() +
                " at " + codeLocation());
    }

    /// If token is the specified type, consume and return true.
    /// Otherwise return false. If there are no more tokens return false.
    protected boolean acceptAndConsume(TokenType type) throws SyntaxException {
        if (isAtEnd()) return false;
        if (peek().type() == type) {
            consume();
            return true;
        }
        return false;
    }

    // TODO: Check everything that calls this is prepared for NULL
    // use special EOF instead of NULL
    protected Token peek() {
        if (isAtEnd()) return EOF;
        return tokens.get(tokenIndex);
    }

    protected Token peek(int howFar) {
        if (tokenIndex + howFar - 1 >= tokens.size()) return EOF;
        return tokens.get(tokenIndex + howFar - 1);
    }

    protected boolean isAtEnd() {
        return tokenIndex >= tokens.size();
    }

    protected boolean isTokenType(TokenType tokenType) {
        return !isAtEnd() && peek().type() == tokenType;
    }

    /// save the currnet token position on the stack
    protected void save() throws SyntaxException {
        savedTokenPositions.push(tokenIndex);

        // String caller = getCaller();
        // if (pushByMethod.containsKey(caller)) {
        //     Integer pushes = pushByMethod.get(caller);
        //     pushes++;
        //     pushByMethod.put(caller, pushes);
        // } else {
        //     pushByMethod.put(caller, 1);
        // }

        if (reportStack) {
            reporter.reportDebug("Push [%d]", savedTokenPositions.size());
        }
        if (savedTokenPositions.size() > stackLimit) {
            stats();
            throw new SyntaxException("Artificial stack limit exceeded");
        }
        if (savedTokenPositions.size() > 512) {
            reporter.reportWarning("High stack");
        }
    }

    /// Go back to the saved token position, don't change the stack
    protected void rewind() {
        if (savedTokenPositions.isEmpty()) {
            reporter.reportError("Stack is empty");
        }
        tokenIndex = savedTokenPositions.getLast().intValue();
        if (reportTrace) {
            reporter.reportTrace(tokenLocation("rewind"));
        }
    }

    /// Go back to where the last saved position and remove it from the stack

    protected void revert() {
        if (savedTokenPositions.isEmpty()) {
            reporter.reportError("Stack is empty");
        }
        tokenIndex = savedTokenPositions.getLast().intValue();
        pop();
    }

    protected void pop() {
        if (savedTokenPositions.isEmpty()) {
            reporter.reportError("Stack is empty");
        }
        savedTokenPositions.pop();
        // String caller = getCaller();
        // if (pushByMethod.containsKey(caller)) {
        //     Integer pushes = pushByMethod.get(caller);
        //     pushes--;
        //     pushByMethod.put(caller, pushes);
        // } else {
        //     pushByMethod.put(caller, 0);
        // }
        if (reportStack) {
            reporter.reportDebug("Pop [%d]", savedTokenPositions.size());
        }
        if (savedTokenPositions.size() != stackSize) {
            // reporter.reportError("Stack inconsistency");
        }
    }

    protected String tokenLocation(String methodName) {
        return String.format("D%d L%d H%d T%d %s: %s", getCallDepth(), peek().line(), highestLine, tokenIndex, methodName, upcomingTokens());
    }

    protected void trace(String methodName) {
        if (reportTrace) {
            int callDepth = getCallDepth();
            String trace = " ".repeat(callDepth) + tokenLocation(methodName);
            // byte[] bytes = trace.getBytes(Charset.defaultCharset());
            // System.err.println(">" + trace);
            reporter.reportTrace(trace );
        }
        stackSize = savedTokenPositions.size();
    }

    protected String upcomingTokens() {
        StringBuilder sb = new StringBuilder();
        int distance = Math.min(tokens.size() - tokenIndex, 8);
        for (int i = 1; i <= distance; i++) {
            sb.append(peek(i).lexeme());
            sb.append(" ");
        }
        return sb.toString();
    }

    protected void unimplemented(String methodName) throws SyntaxException {
        reporter.reportWarning("Unimplemented: " + methodName);
        peek();
    }

    protected String getCaller() {
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        if (stackTraceElements.length < 3) {
            return null;
        }
        return stackTraceElements[3].getMethodName();
    }

    protected int getCallDepth() {
        int depth = 0;
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement ste : stackTraceElements) {
            if (ste.getClassName().equals(PARSER_CLASS_NAME)) {
                depth++;
            }
        }
        return depth;
    }

    //
    //
    //

    protected void splitToken() throws UnexpectedTokenException {
        Token current = peek();
        if (current.type() == TokenType.RIGHT_SHIFT || current.type() == TokenType.UNSIGNED_RIGHT_SHIFT) {
            String lexeme = current.lexeme();
            String firstLexeme = lexeme.substring(0, 1);
            String secondLexeme = lexeme.substring(1, 2);
            Token firstToken = new Token(TokenType.GREATER_THAN, firstLexeme, current.startIndex(), current.line(), current.column(), null);
            Token secondToken = new Token(TokenType.GREATER_THAN, secondLexeme, current.startIndex(), current.line(), current.column() + 1, null);
            tokens.set(tokenIndex, firstToken);
            tokens.add(tokenIndex + 1, secondToken);
            if (current.type() == TokenType.UNSIGNED_RIGHT_SHIFT) {
                Token thirdToken = new Token(TokenType.GREATER_THAN, secondLexeme, current.startIndex(), current.line(), current.column() + 2, null);
                tokens.add(tokenIndex + 2, thirdToken);
            }
        } else {
            throw new UnexpectedTokenException(current);
        }
    }

    protected void chain(Primary chained, Primary newLeft) {
        reporter.reportDebug("Chaining");
        reporter.reportDebug("    %s", tokenLocation("chain"));
        List<MethodInvocation> mil0 = newLeft.getDescendants(MethodInvocation.class);
        if (!mil0.isEmpty()) {
            reporter.reportDebug("    from %s", mil0.toString());
        }

        List<MethodInvocation> mil1 = chained.getDescendants(MethodInvocation.class);
        if (!mil1.isEmpty()) {
            reporter.reportDebug("    to %s", mil1.toString());
        }

        PrimaryNoNewArray pnna = chained.getPrimaryNoNewArray();
        if (pnna != null) {
            if (pnna.getMethodInvocation() != null) {
                pnna.getMethodInvocation().setPrimary(newLeft);
            }
            if (pnna.getFieldAccess() != null) {
                pnna.getFieldAccess().setPrimary(newLeft);
            }
        }
        // TODO: Other types
    }

    //
    //
    //

    @Here //TODO: This is not how to detect modifiers. @interface is not a modifier.
    protected boolean isClassModifierOrAnnotationToken(TokenType type) {
        return isClassModifierToken(type) || type == TokenType.AT_SIGN;
    }

    protected boolean isClassModifierToken(TokenType type) {
        return switch (type) {
            // Class/Interface Modifiers
            case KEYWORD_PUBLIC, KEYWORD_PROTECTED, KEYWORD_PRIVATE,
                 KEYWORD_ABSTRACT, KEYWORD_STATIC, KEYWORD_FINAL,
                 KEYWORD_STRICTFP,
                 // Contextual Modifiers for inheritance (sealed types)
                 CONTEXTUAL_SEALED, CONTEXTUAL_NON_SEALED -> true;
            default -> false;
        };
    }

    protected int countClassModifiers() {
        int lookahead = 1;
        while (isClassModifierOrAnnotationToken(peek(lookahead).type())) {
            lookahead++;
        }
        return lookahead;
    }

    protected boolean canParseTopLevelDeclaration() {
        int lookahead = countClassModifiers();
        TokenType type = peek(lookahead).type();

        return switch (type) {
            case KEYWORD_CLASS,
                 KEYWORD_INTERFACE,
                 KEYWORD_ENUM,
                 CONTEXTUAL_RECORD -> true;
            case AT_SIGN -> peek(lookahead + 1).type() == TokenType.KEYWORD_INTERFACE;
            default -> false;
        };
    }
    //
    //
    //

    // Assumes a peek(int k) or similar exists to look ahead
    protected boolean canParseClassDeclaration() {
        // Check for "class" keyword or related tokens
        return peek().type() == TokenType.KEYWORD_CLASS ||
               peek().type() == TokenType.KEYWORD_FINAL || // Example modifier
               peek().type() == TokenType.AT_SIGN;      // Example annotation start
    }

    protected boolean canParseNormalClassDeclaration() {
        // Check for "class" keyword or related tokens
        return peek().type() == TokenType.KEYWORD_CLASS ||
               peek().type() == TokenType.KEYWORD_FINAL || // Example modifier
               peek().type() == TokenType.AT_SIGN;      // Example annotation start
    }

    protected boolean canParseRecordClassDeclaration() {
        // Check for "class" keyword or related tokens
        return peek().type() == TokenType.CONTEXTUAL_RECORD ||
               peek().type() == TokenType.KEYWORD_FINAL || // Example modifier
               peek().type() == TokenType.AT_SIGN;      // Example annotation start
    }

    protected boolean canParseEnumClassDeclaration() {
        // Check for "class" keyword or related tokens
        return peek().type() == TokenType.KEYWORD_ENUM ||
               peek().type() == TokenType.KEYWORD_FINAL || // Example modifier
               peek().type() == TokenType.AT_SIGN;      // Example annotation start
    }

    protected boolean canParseInterfaceDeclaration() {
        // Check for "interface" or "@interface" keywords
        return peek().type() == TokenType.KEYWORD_INTERFACE ||
               (peek().type() == TokenType.AT_SIGN && peek(2).type() == TokenType.KEYWORD_INTERFACE);
    }

}
