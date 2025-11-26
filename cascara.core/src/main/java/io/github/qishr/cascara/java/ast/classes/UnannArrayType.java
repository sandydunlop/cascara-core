package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.Dims;

/// UnannArrayType:
/// UnannPrimitiveType Dims
/// UnannClassOrInterfaceType Dims
/// UnannTypeVariable Dims
public class UnannArrayType extends ASTNode {
    private UnannPrimitiveType unannPrimitiveType = null;
    private UnannClassOrInterfaceType unannClassOrInterfaceType = null;
    private UnannTypeVariable unannTypeVariable = null;
    private Dims dims = null;

    public UnannPrimitiveType getUnannPrimitiveType() {
        return unannPrimitiveType;
    }

    public void setUnannPrimitiveType(UnannPrimitiveType unannPrimitiveType) {
        this.unannPrimitiveType = unannPrimitiveType;
        addChild(unannPrimitiveType);
    }

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

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
        addChild(dims);
    }

    @Override
    public String toString() {
        if (unannPrimitiveType != null) {
            return unannPrimitiveType.toString() + dims.toString();
        } else if (unannClassOrInterfaceType != null) {
            return unannClassOrInterfaceType.toString() + dims.toString();
        } else if (unannTypeVariable != null) {
            return unannTypeVariable.toString() + dims.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
