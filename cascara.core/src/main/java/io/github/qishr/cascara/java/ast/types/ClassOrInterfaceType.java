package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ClassOrInterfaceType:
/// ClassType
/// InterfaceType
public class ClassOrInterfaceType extends ASTNode {
    private ClassType classType = null;
    private InterfaceType interfaceType = null;

    public ClassOrInterfaceType () {
    }

    public ClassOrInterfaceType (ClassType ct) {
        setClassType(ct);
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
        addChild(classType);
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
        addChild(interfaceType);
    }

    @Override
    public String toString() {
        if (classType != null) {
            return classType.toString();
        } else if (interfaceType != null) {
            return interfaceType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
