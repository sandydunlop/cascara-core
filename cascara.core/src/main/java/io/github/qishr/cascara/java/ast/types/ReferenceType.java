package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ReferenceType:
/// ClassOrInterfaceType
/// TypeVariable
/// ArrayType
public class ReferenceType extends ASTNode {
    private ClassOrInterfaceType classOrInterfaceType = null;
    private TypeVariable typeVariable = null;
    private ArrayType arrayType = null;

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

    public ArrayType getArrayType() {
        return arrayType;
    }

    public void setArrayType(ArrayType arrayType) {
        this.arrayType = arrayType;
        addChild(arrayType);
    }

    @Override
    public String toString() {
        if (classOrInterfaceType != null) {
            return classOrInterfaceType.toString();
        } else if (typeVariable != null) {
            return typeVariable.toString();
        } else if (arrayType != null) {
            return arrayType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
