package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// IntegralType:
/// (one of)
/// byte short int long char
public class IntegralType extends ASTNode {
    TokenType type = TokenType.UNRECOGNIZED;

    public IntegralType(TokenType type) {
        this.type = type;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String toString() {
        switch(type) {
            case KEYWORD_BYTE:
                return StringConstant.BYTE;
            case KEYWORD_SHORT:
                return StringConstant.SHORT;
            case KEYWORD_INT:
                return StringConstant.INT;
            case KEYWORD_LONG:
                return StringConstant.LONG;
            case KEYWORD_CHAR:
                return StringConstant.CHAR;
            default:
                return StringConstant.UNDEFINED;
        }
    }
}
