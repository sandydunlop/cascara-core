package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// FloatingPointType:
/// (one of)
/// float double
public class FloatingPointType extends ASTNode {
    TokenType type = TokenType.UNRECOGNIZED;

    public FloatingPointType(TokenType type) {
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
            case KEYWORD_FLOAT:
                return StringConstant.FLOAT;
            case KEYWORD_DOUBLE:
                return StringConstant.DOUBLE;
            default:
                return StringConstant.UNDEFINED;
        }
    }
}
