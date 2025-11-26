package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.arrays.ArrayInitializer;
import io.github.qishr.cascara.java.ast.types.ClassOrInterfaceType;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.ast.types.PrimitiveType;

/// ArrayCreationExpressionWithInitializer:
/// new PrimitiveType Dims ArrayInitializer
/// new ClassOrInterfaceType Dims ArrayInitializer
public class ArrayCreationExpressionWithInitializer extends ASTNode {
    private PrimitiveType primitiveType = null;
    private ClassOrInterfaceType classOrInterfaceType = null;
    private Dims dims = null;
    private ArrayInitializer arrayInitializer = null;

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

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
        addChild(dims);
    }

    public ArrayInitializer getArrayInitializer() {
        return arrayInitializer;
    }

    public void setArrayInitializer(ArrayInitializer arrayInitializer) {
        this.arrayInitializer = arrayInitializer;
        addChild(arrayInitializer);
    }

}
