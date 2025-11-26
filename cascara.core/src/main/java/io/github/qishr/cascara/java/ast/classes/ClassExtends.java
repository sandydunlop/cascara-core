package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.ClassType;

/// ClassExtends:
/// extends ClassType
public class ClassExtends extends ASTNode {
    private ClassType classType = null;

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
        addChild(classType);
    }

}
