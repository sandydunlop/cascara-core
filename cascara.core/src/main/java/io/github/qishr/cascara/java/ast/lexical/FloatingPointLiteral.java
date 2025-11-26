package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// FloatingPointLiteral:
/// DecimalFloatingPointLiteral
/// HexadecimalFloatingPointLiteral
public class FloatingPointLiteral extends ASTNode {
    private float value = 0f;

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }



    // private DecimalFloatingPointLiteral decimalFloatingPointLiteral = null;
    // private HexadecimalFloatingPointLiteral hexadecimalFloatingPointLiteral = null;

}
