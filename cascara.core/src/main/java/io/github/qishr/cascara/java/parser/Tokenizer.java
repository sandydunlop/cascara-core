package io.github.qishr.cascara.java.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.analyzer.SemanticType;

/**
 * A definitive tokenizer (lexer) for Java source code.
 * This version processes the source as a single string and handles line/column tracking internally,
 * ensuring newlines are correctly tokenized as part of WHITESPACE.
 */
public class Tokenizer {
    private static final SemanticType UNKNOWN = SemanticType.NONE;
    /**
     * Defines the types of tokens recognized by the lexer.
     */
    public enum TokenType {
        // Reserved Keywords (51 words)
        KEYWORD_ABSTRACT, KEYWORD_ASSERT, KEYWORD_BOOLEAN, KEYWORD_BREAK,
        KEYWORD_BYTE, KEYWORD_CASE, KEYWORD_CATCH, KEYWORD_CHAR,
        KEYWORD_CLASS, KEYWORD_CONST, KEYWORD_CONTINUE, KEYWORD_DEFAULT,
        KEYWORD_DO, KEYWORD_DOUBLE, KEYWORD_ELSE, KEYWORD_ENUM,
        KEYWORD_EXTENDS, KEYWORD_FINAL, KEYWORD_FINALLY, KEYWORD_FLOAT,
        KEYWORD_FOR, KEYWORD_GOTO, KEYWORD_IF, KEYWORD_IMPLEMENTS,
        KEYWORD_IMPORT, KEYWORD_INSTANCEOF, KEYWORD_INT, KEYWORD_INTERFACE,
        KEYWORD_LONG, KEYWORD_NATIVE, KEYWORD_NEW, KEYWORD_PACKAGE,
        KEYWORD_PRIVATE, KEYWORD_PROTECTED, KEYWORD_PUBLIC, KEYWORD_RETURN,
        KEYWORD_SHORT, KEYWORD_STATIC, KEYWORD_STRICTFP, KEYWORD_SUPER,
        KEYWORD_SWITCH, KEYWORD_SYNCHRONIZED, KEYWORD_THIS, KEYWORD_THROW,
        KEYWORD_THROWS, KEYWORD_TRANSIENT, KEYWORD_TRY, KEYWORD_VOID,
        KEYWORD_VOLATILE, KEYWORD_WHILE, KEYWORD_OPEN,

        // Contextual Keywords
        CONTEXTUAL_EXPORTS, CONTEXTUAL_MODULE, CONTEXTUAL_NON_SEALED,
        CONTEXTUAL_OPENS, CONTEXTUAL_PERMITS, CONTEXTUAL_PROVIDES,
        CONTEXTUAL_RECORD, CONTEXTUAL_REQUIRES, CONTEXTUAL_SEALED,
        CONTEXTUAL_TO, CONTEXTUAL_TRANSITIVE, CONTEXTUAL_USES,
        CONTEXTUAL_VAR, CONTEXTUAL_WITH, CONTEXTUAL_YIELD,
        CONTEXTUAL_WHEN, // <-- ADDED CONTEXTUAL_WHEN

        // Literals
        IDENTIFIER, INTEGER_LITERAL, FLOAT_LITERAL, CHAR_LITERAL,
        STRING_LITERAL, TEXT_BLOCK_LITERAL, BOOLEAN_LITERAL,
        KEYWORD_NULL,

        // Punctuation
        LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE, LEFT_BRACKET, RIGHT_BRACKET,
        SEMICOLON, COMMA, DOT, AT_SIGN, COLON, QUESTION_MARK,

        // Single operators (used as fallback)
        EQUALS, PLUS, MINUS, STAR, SLASH, PERCENT,
        LESS_THAN, GREATER_THAN, AMPERSAND, PIPE, CARET, TILDE, BANG,

        // Multi-character operators
        ARROW, COLON_COLON, DOT_DOT_DOT,
        EQUALS_EQUALS, BANG_EQUALS, LESS_EQUALS, GREATER_EQUALS,
        AMPERSAND_AMPERSAND, PIPE_PIPE,
        LEFT_SHIFT, RIGHT_SHIFT, UNSIGNED_RIGHT_SHIFT,
        PLUS_EQUALS, MINUS_EQUALS, STAR_EQUALS, SLASH_EQUALS, PERCENT_EQUALS,
        AMPERSAND_EQUALS, PIPE_EQUALS, CARET_EQUALS,
        LEFT_SHIFT_EQUALS, RIGHT_SHIFT_EQUALS, UNSIGNED_RIGHT_SHIFT_EQUALS,
        PLUS_PLUS, MINUS_MINUS,

        // Special (Non-Semantic)
        WHITESPACE, SINGLE_LINE_COMMENT, BLOCK_COMMENT_CONTENT,
        RESERVED_UNDERSCORE,
        UNRECOGNIZED, EOF
    }

    /**
     * Represents a single token found during lexical analysis.
     */
    public record Token(TokenType type, String lexeme, int startIndex, int line, int column, SemanticType semtype) {
        @Override
        public String toString() {
            return String.format("[%-18s | '%-15s' | L:%d C:%d]",
                type,
                // Simple escaping for display
                lexeme.replace("\n", "\\n").replace("\r", "\\r").replace("\"", "\\\""),
                line,
                column);
        }
    }

    private static final Map<String, TokenType> KEYWORDS = new HashMap<>();
    static {
        // Reserved Keywords
        KEYWORDS.put("abstract", TokenType.KEYWORD_ABSTRACT); KEYWORDS.put("assert", TokenType.KEYWORD_ASSERT);
        KEYWORDS.put("boolean", TokenType.KEYWORD_BOOLEAN); KEYWORDS.put("break", TokenType.KEYWORD_BREAK);
        KEYWORDS.put("byte", TokenType.KEYWORD_BYTE); KEYWORDS.put("case", TokenType.KEYWORD_CASE);
        KEYWORDS.put("catch", TokenType.KEYWORD_CATCH); KEYWORDS.put("char", TokenType.KEYWORD_CHAR);
        KEYWORDS.put("class", TokenType.KEYWORD_CLASS); KEYWORDS.put("const", TokenType.KEYWORD_CONST);
        KEYWORDS.put("continue", TokenType.KEYWORD_CONTINUE); KEYWORDS.put("default", TokenType.KEYWORD_DEFAULT);
        KEYWORDS.put("do", TokenType.KEYWORD_DO); KEYWORDS.put("double", TokenType.KEYWORD_DOUBLE);
        KEYWORDS.put("else", TokenType.KEYWORD_ELSE); KEYWORDS.put("enum", TokenType.KEYWORD_ENUM);
        KEYWORDS.put("extends", TokenType.KEYWORD_EXTENDS); KEYWORDS.put("final", TokenType.KEYWORD_FINAL);
        KEYWORDS.put("finally", TokenType.KEYWORD_FINALLY); KEYWORDS.put("float", TokenType.KEYWORD_FLOAT);
        KEYWORDS.put("for", TokenType.KEYWORD_FOR); KEYWORDS.put("goto", TokenType.KEYWORD_GOTO);
        KEYWORDS.put("if", TokenType.KEYWORD_IF); KEYWORDS.put("implements", TokenType.KEYWORD_IMPLEMENTS);
        KEYWORDS.put("import", TokenType.KEYWORD_IMPORT); KEYWORDS.put("instanceof", TokenType.KEYWORD_INSTANCEOF);
        KEYWORDS.put("int", TokenType.KEYWORD_INT); KEYWORDS.put("interface", TokenType.KEYWORD_INTERFACE);
        KEYWORDS.put("long", TokenType.KEYWORD_LONG); KEYWORDS.put("native", TokenType.KEYWORD_NATIVE);
        KEYWORDS.put("new", TokenType.KEYWORD_NEW); KEYWORDS.put("package", TokenType.KEYWORD_PACKAGE);
        KEYWORDS.put("private", TokenType.KEYWORD_PRIVATE); KEYWORDS.put("protected", TokenType.KEYWORD_PROTECTED);
        KEYWORDS.put("public", TokenType.KEYWORD_PUBLIC); KEYWORDS.put("return", TokenType.KEYWORD_RETURN);
        KEYWORDS.put("short", TokenType.KEYWORD_SHORT); KEYWORDS.put("static", TokenType.KEYWORD_STATIC);
        KEYWORDS.put("strictfp", TokenType.KEYWORD_STRICTFP); KEYWORDS.put("super", TokenType.KEYWORD_SUPER);
        KEYWORDS.put("switch", TokenType.KEYWORD_SWITCH); KEYWORDS.put("synchronized", TokenType.KEYWORD_SYNCHRONIZED);
        KEYWORDS.put("this", TokenType.KEYWORD_THIS); KEYWORDS.put("throw", TokenType.KEYWORD_THROW);
        KEYWORDS.put("throws", TokenType.KEYWORD_THROWS); KEYWORDS.put("transient", TokenType.KEYWORD_TRANSIENT);
        KEYWORDS.put("try", TokenType.KEYWORD_TRY); KEYWORDS.put("void", TokenType.KEYWORD_VOID);
        KEYWORDS.put("volatile", TokenType.KEYWORD_VOLATILE); KEYWORDS.put("while", TokenType.KEYWORD_WHILE);
        KEYWORDS.put("open", TokenType.KEYWORD_OPEN);

        // Contextual Keywords
        KEYWORDS.put("exports", TokenType.CONTEXTUAL_EXPORTS); KEYWORDS.put("module", TokenType.CONTEXTUAL_MODULE);
        KEYWORDS.put("non-sealed", TokenType.CONTEXTUAL_NON_SEALED); KEYWORDS.put("opens", TokenType.CONTEXTUAL_OPENS);
        KEYWORDS.put("permits", TokenType.CONTEXTUAL_PERMITS); KEYWORDS.put("provides", TokenType.CONTEXTUAL_PROVIDES);
        KEYWORDS.put("record", TokenType.CONTEXTUAL_RECORD); KEYWORDS.put("requires", TokenType.CONTEXTUAL_REQUIRES);
        KEYWORDS.put("sealed", TokenType.CONTEXTUAL_SEALED); KEYWORDS.put("to", TokenType.CONTEXTUAL_TO);
        KEYWORDS.put("transitive", TokenType.CONTEXTUAL_TRANSITIVE); KEYWORDS.put("uses", TokenType.CONTEXTUAL_USES);
        KEYWORDS.put("var", TokenType.CONTEXTUAL_VAR); KEYWORDS.put("with", TokenType.CONTEXTUAL_WITH);
        KEYWORDS.put("yield", TokenType.CONTEXTUAL_YIELD);
        KEYWORDS.put("when", TokenType.CONTEXTUAL_WHEN); // <-- ADDED 'when'

        // Literals
        KEYWORDS.put("true", TokenType.BOOLEAN_LITERAL); KEYWORDS.put("false", TokenType.BOOLEAN_LITERAL);
        KEYWORDS.put("null", TokenType.KEYWORD_NULL);
    }

    private static class LexerState {
        boolean isInsideBlockComment = false;
        boolean isInsideTextBlock = false;
    }

    /**
     * Peeks at the character at the given offset from the current position.
     */
    private static char peek(String source, int current, int offset) {
        if (current + offset >= source.length()) {
            return '\0';
        }
        return source.charAt(current + offset);
    }

    // --- MODIFIED: Uses a single String source and manages line/column internally ---
    /**
     * Converts the entire source code string into a sequence of Tokens.
     */
    public static List<Token> tokenize(Reporter reporter, String source, boolean verbose, boolean includeNonSemanticTokens) {
        String sourceCode = UnicodeUnescaper.unescapeUnicode(source);
        List<Token> tokens = new ArrayList<>();
        if (sourceCode == null || sourceCode.isEmpty()) {
            return tokens;
        }

        LexerState state = new LexerState();
        int current = 0;
        int lineNumber = 1;
        int column = 1;

        while (current < sourceCode.length()) {
            char c = sourceCode.charAt(current);
            int start = current;
            int startLine = lineNumber;
            int startCol = column;
            Token token = null;

            // --- 1. Comment/Whitespace/TextBlock State Handling ---

            // BLOCK COMMENT
            if (state.isInsideBlockComment) {
                int endCommentIndex = sourceCode.indexOf("*/", current);
                if (endCommentIndex != -1) {
                    int endCommentEnd = endCommentIndex + 2;
                    String lexeme = sourceCode.substring(start, endCommentEnd);

                    // Update position for block comment content
                    for (int i = start; i < endCommentEnd; i++) {
                        char charAtI = sourceCode.charAt(i);
                        if (charAtI == '\n') { lineNumber++; column = 1; }
                        else if (charAtI == '\r') { /* Ignore \r if followed by \n */ }
                        else { column++; }
                    }

                    current = endCommentEnd;
                    state.isInsideBlockComment = false;
                    token = new Token(TokenType.BLOCK_COMMENT_CONTENT, lexeme, start, startLine, startCol, UNKNOWN);
                } else {
                    // Comment spans to EOF
                    String lexeme = sourceCode.substring(start);
                    for (int i = start; i < sourceCode.length(); i++) {
                         char charAtI = sourceCode.charAt(i);
                        if (charAtI == '\n') { lineNumber++; column = 1; }
                        else if (charAtI == '\r') { /* Ignore \r if followed by \n */ }
                        else { column++; }
                    }
                    current = sourceCode.length();
                    token = new Token(TokenType.BLOCK_COMMENT_CONTENT, lexeme, start, startLine, startCol, UNKNOWN);
                }
            }

            // WHITESPACE (Includes newlines)
            else if (Character.isWhitespace(c)) {
                // Consume all contiguous whitespace characters
                while (current < sourceCode.length() && Character.isWhitespace(sourceCode.charAt(current))) {
                    char nextC = sourceCode.charAt(current);

                    if (nextC == '\n') {
                        // Consumed \n
                        lineNumber++;
                        column = 1;
                    } else if (nextC == '\r') {
                        // Consumed \r. Check for \r\n sequence to avoid double counting
                        if (current + 1 < sourceCode.length() && sourceCode.charAt(current + 1) == '\n') {
                            // \r\n is a single line break
                            lineNumber++;
                            column = 1;
                        } else {
                            // \r by itself is a line break
                            lineNumber++;
                            column = 1;
                        }
                    } else {
                        // Space or Tab
                        column++;
                    }
                    current++;
                }

                String lexeme = sourceCode.substring(start, current);
                token = new Token(TokenType.WHITESPACE, lexeme, start, startLine, startCol, UNKNOWN);
            }

            // BLOCK COMMENT START
            else if (c == '/' && peek(sourceCode, current, 1) == '*') {
                current += 2;
                column += 2;
                state.isInsideBlockComment = true;
                continue; // Restart loop to handle comment content
            }

            // SINGLE-LINE COMMENT
            else if (c == '/' && peek(sourceCode, current, 1) == '/') {
                int endOfLine = sourceCode.indexOf('\n', current);
                if (endOfLine == -1) {
                    endOfLine = sourceCode.length();
                }

                String lexeme = sourceCode.substring(start, endOfLine);
                current = endOfLine;
                column += lexeme.length(); // Update column based on comment length
                // Note: Line/Column for next token will be updated by the WHITESPACE handling when it consumes the \n
                token = new Token(TokenType.SINGLE_LINE_COMMENT, lexeme, start, startLine, startCol, UNKNOWN);
            }

            // --- Conditional Filtering for Non-Semantic Tokens ---
            if (token != null && isNonSemantic(token.type())) {
                if (!includeNonSemanticTokens) {
                    token = null; // Discard token for parser mode
                    continue; // Skip the token addition block
                }
            }

            // --- 2. Multi-character Operator Checks (Greedy Matching) ---
            if (token == null) {
                switch (c) {
                    case '=':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.EQUALS_EQUALS, "==", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.EQUALS, "=", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '!':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.BANG_EQUALS, "!=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.BANG, "!", start, startLine, startCol, UNKNOWN); }
                        break;
                    // ... (All other multi-character operators follow, ensuring current and column are updated)
                    case '-':
                        if (peek(sourceCode, current, 1) == '>') { current += 2; column += 2; token = new Token(TokenType.ARROW, "->", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.MINUS_EQUALS, "-=", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '-') { current += 2; column += 2; token = new Token(TokenType.MINUS_MINUS, "--", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.MINUS, "-", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '+':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.PLUS_EQUALS, "+=", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '+') { current += 2; column += 2; token = new Token(TokenType.PLUS_PLUS, "++", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.PLUS, "+", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '*':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.STAR_EQUALS, "*=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.STAR, "*", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '%':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.PERCENT_EQUALS, "%=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.PERCENT, "%", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '&':
                        if (peek(sourceCode, current, 1) == '&') { current += 2; column += 2; token = new Token(TokenType.AMPERSAND_AMPERSAND, "&&", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.AMPERSAND_EQUALS, "&=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.AMPERSAND, "&", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '|':
                        if (peek(sourceCode, current, 1) == '|') { current += 2; column += 2; token = new Token(TokenType.PIPE_PIPE, "||", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.PIPE_EQUALS, "|=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.PIPE, "|", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '^':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.CARET_EQUALS, "^=", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.CARET, "^", start, startLine, startCol, UNKNOWN); }
                        break;
                    case ':':
                        if (peek(sourceCode, current, 1) == ':') { current += 2; column += 2; token = new Token(TokenType.COLON_COLON, "::", start, startLine, startCol, UNKNOWN); }
                        else { current++; column++; token = new Token(TokenType.COLON, ":", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '<':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.LESS_EQUALS, "<=", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '<') {
                            if (peek(sourceCode, current, 2) == '=') { current += 3; column += 3; token = new Token(TokenType.LEFT_SHIFT_EQUALS, "<<=", start, startLine, startCol, UNKNOWN); }
                            else { current += 2; column += 2; token = new Token(TokenType.LEFT_SHIFT, "<<", start, startLine, startCol, UNKNOWN); }
                        } else { current++; column++; token = new Token(TokenType.LESS_THAN, "<", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '>':
                        if (peek(sourceCode, current, 1) == '=') { current += 2; column += 2; token = new Token(TokenType.GREATER_EQUALS, ">=", start, startLine, startCol, UNKNOWN); }
                        else if (peek(sourceCode, current, 1) == '>') {
                            if (peek(sourceCode, current, 2) == '=') { current += 3; column += 3; token = new Token(TokenType.RIGHT_SHIFT_EQUALS, ">>=", start, startLine, startCol, UNKNOWN); }
                            else if (peek(sourceCode, current, 2) == '>') {
                                if (peek(sourceCode, current, 3) == '=') { current += 4; column += 4; token = new Token(TokenType.UNSIGNED_RIGHT_SHIFT_EQUALS, ">>>=", start, startLine, startCol, UNKNOWN); }
                                else { current += 3; column += 3; token = new Token(TokenType.UNSIGNED_RIGHT_SHIFT, ">>>", start, startLine, startCol, UNKNOWN); }
                            } else { current += 2; column += 2; token = new Token(TokenType.RIGHT_SHIFT, ">>", start, startLine, startCol, UNKNOWN); }
                        } else { current++; column++; token = new Token(TokenType.GREATER_THAN, ">", start, startLine, startCol, UNKNOWN); }
                        break;
                    case '.':
                        if (peek(sourceCode, current, 1) == '.' && peek(sourceCode, current, 2) == '.') { current += 3; column += 3; token = new Token(TokenType.DOT_DOT_DOT, "...", start, startLine, startCol, UNKNOWN); }
                        else if (Character.isDigit(peek(sourceCode, current, 1))) { /* Let numeric logic handle it */ }
                        else { current++; column++; token = new Token(TokenType.DOT, ".", start, startLine, startCol, UNKNOWN); }
                        break;
                    default:
                        // No multi-char operator found
                        break;
                }
            }


            // --- 3. Literals and Single-character Punctuation/Operators ---
            if (token == null) {
                // TEXT BLOCK
                if (c == '"' && peek(sourceCode, current, 1) == '"' && peek(sourceCode, current, 2) == '"') {
                    current += 3; column += 3;
                    int endBlockIndex = sourceCode.indexOf("\"\"\"", current);

                    if (endBlockIndex != -1) {
                        String lexeme = sourceCode.substring(start, endBlockIndex + 3);

                        // Update position
                        for (int i = start + 3; i < endBlockIndex + 3; i++) {
                            char charAtI = sourceCode.charAt(i);
                            if (charAtI == '\n') { lineNumber++; column = 1; }
                            else if (charAtI == '\r') { /* Ignore \r if followed by \n */ }
                            else { column++; }
                        }

                        current = endBlockIndex + 3;
                        token = new Token(TokenType.TEXT_BLOCK_LITERAL, lexeme, start, startLine, startCol, UNKNOWN);
                    } else {
                        // Error: Text block not closed (Treat as spanning to EOF)
                        state.isInsideTextBlock = true;
                        token = new Token(TokenType.UNRECOGNIZED, sourceCode.substring(start), start, startLine, startCol, UNKNOWN);
                        current = sourceCode.length();
                        // Position tracking is simplified here since it's an error state
                    }
                }

                // IDENTIFIER or KEYWORD
                else if (Character.isLetter(c) || c == '_') {
                    while (current < sourceCode.length() &&
                           (Character.isLetterOrDigit(sourceCode.charAt(current)) || sourceCode.charAt(current) == '_')) {
                        current++;
                        column++;
                    }
                    String lexeme = sourceCode.substring(start, current);

                    if (lexeme.equals("_")) {
                        token = new Token(TokenType.RESERVED_UNDERSCORE, lexeme, start, startLine, startCol, UNKNOWN);
                    } else {
                        TokenType type = KEYWORDS.getOrDefault(lexeme, TokenType.IDENTIFIER);
                        token = new Token(type, lexeme, start, startLine, startCol, UNKNOWN);
                    }
                }

                // NUMERIC LITERAL
                else if (Character.isDigit(c) || (c == '.' && Character.isDigit(peek(sourceCode, current, 1)))) {
                    // ... (Numeric literal consumption logic remains the same, ensuring current and column are updated)
                    boolean isFloat = (c == '.');

                    if (c == '0' && current + 1 < sourceCode.length()) {
                        char prefix = sourceCode.charAt(current + 1);
                        if (prefix == 'x' || prefix == 'X' || prefix == 'b' || prefix == 'B') {
                            current += 2; column += 2;
                            while (current < sourceCode.length() &&
                                   (Character.isDigit(sourceCode.charAt(current)) || sourceCode.charAt(current) == '_' ||
                                    ("xX".indexOf(prefix) != -1 && "abcdefABCDEF".indexOf(sourceCode.charAt(current)) != -1))) {
                                current++; column++;
                            }
                        } else if (Character.isDigit(prefix)) {
                            current++; column++;
                            while (current < sourceCode.length() && (Character.isDigit(sourceCode.charAt(current)) || sourceCode.charAt(current) == '_')) {
                                current++; column++;
                            }
                        }
                    }

                    while (current < sourceCode.length() &&
                           (Character.isDigit(sourceCode.charAt(current)) || sourceCode.charAt(current) == '_')) {
                        current++; column++;
                    }

                    if (peek(sourceCode, current, 0) == '.' && Character.isDigit(peek(sourceCode, current, 1))) {
                        isFloat = true;
                        current++; column++;
                        while (current < sourceCode.length() && Character.isDigit(sourceCode.charAt(current))) {
                            current++; column++;
                        }
                    }

                    if (peek(sourceCode, current, 0) == 'e' || peek(sourceCode, current, 0) == 'E') {
                        isFloat = true;
                        current++; column++;
                        if (peek(sourceCode, current, 0) == '+' || peek(sourceCode, current, 0) == '-') { current++; column++; }
                        while (current < sourceCode.length() && Character.isDigit(sourceCode.charAt(current))) {
                            current++; column++;
                        }
                    }

                    char suffix = peek(sourceCode, current, 0);
                    if (suffix == 'f' || suffix == 'F' || suffix == 'd' || suffix == 'D' || suffix == 'l' || suffix == 'L') {
                        current++; column++;
                        if (suffix == 'f' || suffix == 'F' || suffix == 'd' || suffix == 'D') {
                            isFloat = true;
                        }
                    }

                    String lexeme = sourceCode.substring(start, current);
                    token = new Token(isFloat ? TokenType.FLOAT_LITERAL : TokenType.INTEGER_LITERAL, lexeme, start, startLine, startCol, UNKNOWN);
                }

                // STRING LITERAL
                else if (c == '"') {
                    current++; column++;
                    while (current < sourceCode.length() && sourceCode.charAt(current) != '"' && sourceCode.charAt(current) != '\n' && sourceCode.charAt(current) != '\r') {
                        current++; column++;
                    }
                    if (current < sourceCode.length() && sourceCode.charAt(current) == '"') {
                        current++; column++;
                    }
                    String lexeme = sourceCode.substring(start, current);
                    token = new Token(TokenType.STRING_LITERAL, lexeme, start, startLine, startCol, UNKNOWN);
                }

                // CHARACTER LITERAL
                else if (c == '\'') {
                    current++; column++;
                    if (current < sourceCode.length()) {
                        if (sourceCode.charAt(current) == '\\' && current + 1 < sourceCode.length()) {
                            current += 2; column += 2;
                        } else {
                            current++; column++;
                        }
                    }
                    if (current < sourceCode.length() && sourceCode.charAt(current) == '\'') {
                        current++; column++;
                    }
                    String lexeme = sourceCode.substring(start, current);
                    token = new Token(TokenType.CHAR_LITERAL, lexeme, start, startLine, startCol, UNKNOWN);
                }

                // SINGLE PUNCTUATION/OPERATORS
                else {
                    switch (c) {
                        case '(': token = new Token(TokenType.LEFT_PAREN, "(", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case ')': token = new Token(TokenType.RIGHT_PAREN, ")", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '{': token = new Token(TokenType.LEFT_BRACE, "{", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '}': token = new Token(TokenType.RIGHT_BRACE, "}", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '[': token = new Token(TokenType.LEFT_BRACKET, "[", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case ']': token = new Token(TokenType.RIGHT_BRACKET, "]", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case ';': token = new Token(TokenType.SEMICOLON, ";", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case ',': token = new Token(TokenType.COMMA, ",", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '@': token = new Token(TokenType.AT_SIGN, "@", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '~': token = new Token(TokenType.TILDE, "~", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        case '?': token = new Token(TokenType.QUESTION_MARK, "?", current, startLine, startCol, UNKNOWN); current++; column++; break;
                        default:
                            current++; column++;
                            token = new Token(TokenType.UNRECOGNIZED, String.valueOf(c), current, startLine, startCol, UNKNOWN);
                            break;
                    }
                }
            }

            if (token != null) {
                if (verbose) {
                    System.out.println("  -> Found: " + token);
                }
                tokens.add(token);
            }
        }

        // Add EOF token
        tokens.add(new Token(TokenType.EOF, "", current, lineNumber, column, UNKNOWN));

        return tokens;
    }

    /**
     * Utility method to check if a token type is non-semantic (ignored by the parser).
     */
    private static boolean isNonSemantic(TokenType type) {
        return type == TokenType.WHITESPACE ||
               type == TokenType.SINGLE_LINE_COMMENT ||
               type == TokenType.BLOCK_COMMENT_CONTENT;
    }

    public static void main(String[] args) {
        // Source code is now a single string, containing the actual newlines.
        String sourceCode =
            "public class CompilerTest {\n" +
            "    // Literals: Octal, Long, Hex, Float, Text Block\r\n" +
            "    int octal = 017;\n" +
            "    /* This is a block comment */\n" +
            "    public static void main(String... args) {\n" +
            "        String s = \"test\";\n" +
            "        int result = switch(s) {\n" +
            "            case \"A\" -> 1;\n" +
            "            case \"B\" when s.length() > 3 -> 2; // Test for 'when'\n" +
            "            default -> 0;\n" +
            "        };\n" +
            "}";

        System.out.println("==================================================");
        System.out.println("Tokenizing for HIGHLIGHTER (Non-Semantic Tokens INCLUDED)");
        System.out.println("==================================================");
        // Using true to get the stream the highlighter needs
        Reporter reporter = new Reporter();
        List<Token> tokensForHighlighter = tokenize(reporter, sourceCode, false, true);

        for (Token token : tokensForHighlighter) {
            System.out.println(token);
        }
    }
}
