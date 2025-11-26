package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.SimpleIdentifier;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// Identifier:
/// IdentifierChars but not a Keyword or BooleanLiteral or NullLiteral
/// IdentifierChars:
/// JavaLetter {JavaLetterOrDigit}
/// JavaLetter:
/// any Unicode character that is a "Java letter"
/// JavaLetterOrDigit:
/// any Unicode character that is a "Java letter-or-digit"
public class Identifier extends ASTNode implements Identifiable, SimpleIdentifier {
    private Token nameToken;

    @Override
    public Token getNameToken() {
        return nameToken;
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public Identifier(Token token) {
        this.nameToken = token;
    }
}
