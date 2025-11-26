package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// BooleanLiteral:
/// (one of)
/// true false
public class BooleanLiteral extends ASTNode {
    private boolean value = false;

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }


}
