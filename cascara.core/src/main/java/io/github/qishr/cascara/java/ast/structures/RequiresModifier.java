package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RequiresModifier:
/// (one of)
/// transitive static
public class RequiresModifier extends ASTNode {
    private boolean isTransitive = false;
    private boolean isStatic = false;

    public boolean isTransitive() {
        return isTransitive;
    }

    public void setTransitive(boolean isTransitive) {
        this.isTransitive = isTransitive;
    }

    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean isStatic) {
        this.isStatic = isStatic;
    }
}
