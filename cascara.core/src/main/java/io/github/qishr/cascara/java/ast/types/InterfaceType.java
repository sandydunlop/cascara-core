package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// InterfaceType:
/// ClassType
public class InterfaceType extends ASTNode {
    private ClassType classType = null;

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
        addChild(classType);
    }

    @Override
    public String toString() {
        if (classType != null) {
            return classType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
