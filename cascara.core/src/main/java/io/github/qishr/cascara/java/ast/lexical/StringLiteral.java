package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// StringLiteral:
/// " {StringCharacter} "
public class StringLiteral extends ASTNode {
    private String value = null;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
