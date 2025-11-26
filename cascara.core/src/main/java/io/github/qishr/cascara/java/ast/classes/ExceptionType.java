package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.ClassType;
import io.github.qishr.cascara.java.ast.types.TypeVariable;

/// ExceptionType:
/// ClassType
/// TypeVariable
public class ExceptionType extends ASTNode {
    private ClassType classType = null;
    private TypeVariable typeVariable = null;

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
        addChild(classType);
    }

    public TypeVariable getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(TypeVariable typeVariable) {
        this.typeVariable = typeVariable;
        addChild(typeVariable);
    }

}
