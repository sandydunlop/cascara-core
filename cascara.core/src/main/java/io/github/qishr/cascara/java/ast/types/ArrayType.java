package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ArrayType:
/// PrimitiveType Dims
/// ClassOrInterfaceType Dims
/// TypeVariable Dims
public class ArrayType extends ASTNode {
    private PrimitiveType primitiveType = null;
    private ClassOrInterfaceType classOrInterfaceType = null;
    private TypeVariable typeVariable = null;
    private Dims dims = null;

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
    }

    public PrimitiveType getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
        addChild(primitiveType);
    }

    public ClassOrInterfaceType getClassOrInterfaceType() {
        return classOrInterfaceType;
    }

    public void setClassOrInterfaceType(ClassOrInterfaceType classOrInterfaceType) {
        this.classOrInterfaceType = classOrInterfaceType;
        addChild(classOrInterfaceType);
    }

    public TypeVariable getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(TypeVariable typeVariable) {
        this.typeVariable = typeVariable;
        addChild(typeVariable);
    }

}
