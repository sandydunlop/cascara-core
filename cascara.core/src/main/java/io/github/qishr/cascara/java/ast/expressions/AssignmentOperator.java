package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// AssignmentOperator:
/// (one of)
/// =  *=  /=  %=  +=  -=  <<=  >>=  >>>=  &=  ^=  |=
public class AssignmentOperator extends ASTNode {
    private TokenType type = TokenType.UNRECOGNIZED;

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }
}
