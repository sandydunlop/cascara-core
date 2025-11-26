package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Result:
/// UnannType
/// void
public class Result extends ASTNode {
    private boolean isVoid = false;
    private UnannType unannType = null;

    public boolean isVoid() {
        return isVoid;
    }
    public void setVoid(boolean isVoid) {
        this.isVoid = isVoid;
    }
    public UnannType getUnannType() {
        return unannType;
    }
    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }


}
