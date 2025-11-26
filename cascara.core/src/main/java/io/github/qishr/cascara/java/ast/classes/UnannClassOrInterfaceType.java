package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// UnannClassOrInterfaceType:
/// UnannClassType
/// UnannInterfaceType
public class UnannClassOrInterfaceType extends ASTNode {
    private UnannClassType unannClassType = null;
    private UnannInterfaceType unannInterfaceType = null;

    public UnannClassType getUnannClassType() {
        return unannClassType;
    }

    public void setUnannClassType(UnannClassType unannClassType) {
        this.unannClassType = unannClassType;
        addChild(unannClassType);
    }

    public UnannInterfaceType getUnannInterfaceType() {
        return unannInterfaceType;
    }

    public void setUnannInterfaceType(UnannInterfaceType unannInterfaceType) {
        this.unannInterfaceType = unannInterfaceType;
        addChild(unannInterfaceType);
    }

    @Override
    public String toString() {
        if (unannClassType != null) {
            return unannClassType.toString();
        } else if (unannInterfaceType != null) {
            return unannInterfaceType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
