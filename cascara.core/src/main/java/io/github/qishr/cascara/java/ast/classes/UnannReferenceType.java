package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// UnannReferenceType:
/// UnannClassOrInterfaceType
/// UnannTypeVariable
/// UnannArrayType
public class UnannReferenceType extends ASTNode {
    private UnannClassOrInterfaceType unannClassOrInterfaceType = null;
    private UnannTypeVariable unannTypeVariable = null;
    private UnannArrayType unannArrayType = null;

    public UnannClassOrInterfaceType getUnannClassOrInterfaceType() {
        return unannClassOrInterfaceType;
    }

    public void setUnannClassOrInterfaceType(UnannClassOrInterfaceType unannClassOrInterfaceType) {
        this.unannClassOrInterfaceType = unannClassOrInterfaceType;
        addChild(unannClassOrInterfaceType);
    }

    public UnannTypeVariable getUnannTypeVariable() {
        return unannTypeVariable;
    }

    public void setUnannTypeVariable(UnannTypeVariable unannTypeVariable) {
        this.unannTypeVariable = unannTypeVariable;
        addChild(unannTypeVariable);
    }

    public UnannArrayType getUnannArrayType() {
        return unannArrayType;
    }

    public void setUnannArrayType(UnannArrayType unannArrayType) {
        this.unannArrayType = unannArrayType;
        addChild(unannArrayType);
    }

    @Override
    public String toString() {
        if (unannClassOrInterfaceType != null) {
            return unannClassOrInterfaceType.toString();
        } else if (unannTypeVariable != null) {
            return unannTypeVariable.toString();
        } else if (unannArrayType != null) {
            return unannArrayType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
