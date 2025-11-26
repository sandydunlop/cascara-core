package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// IntegerLiteral:
/// DecimalIntegerLiteral
/// HexIntegerLiteral
/// OctalIntegerLiteral
/// BinaryIntegerLiteral
public class IntegerLiteral extends ASTNode {
    private int value = 0;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
