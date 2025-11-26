package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceDeclaration;
import io.github.qishr.cascara.java.ast.semantic.Declaration;

/// ClassMemberDeclaration:
/// FieldDeclaration
/// MethodDeclaration
/// ClassDeclaration
/// InterfaceDeclaration
/// ;
public class ClassMemberDeclaration extends ASTNode {
    private FieldDeclaration fieldDeclaration = null;
    private MethodDeclaration methodDeclaration = null;
    private ClassDeclaration classDeclaration = null;
    private InterfaceDeclaration interfaceDeclaration = null;

    public Declaration getDeclaration() {
        if (classDeclaration != null) {
            return classDeclaration.getDeclaration();
        } else if (interfaceDeclaration != null) {
            return interfaceDeclaration.getDeclaration();
        } else if (fieldDeclaration != null) {
            // FieldDeclaration contains a list of declarations
            return null;
        } else if (methodDeclaration != null) {
            return methodDeclaration;
        } else {
            return null;
        }
    }

    public List<Declaration> getFieldDeclarations() {
        if (fieldDeclaration != null) {
            return fieldDeclaration.getDeclarations();
        }
        return new ArrayList<>();
    }

    public FieldDeclaration getFieldDeclaration() {
        return fieldDeclaration;
    }

    public void setFieldDeclaration(FieldDeclaration fieldDeclaration) {
        this.fieldDeclaration = fieldDeclaration;
        addChild(fieldDeclaration);
    }

    public MethodDeclaration getMethodDeclaration() {
        return methodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration methodDeclaration) {
        this.methodDeclaration = methodDeclaration;
        addChild(methodDeclaration);
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }

    public void setClassDeclaration(ClassDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
        addChild(classDeclaration);
    }

    public InterfaceDeclaration getInterfaceDeclaration() {
        return interfaceDeclaration;
    }

    public void setInterfaceDeclaration(InterfaceDeclaration interfaceDeclaration) {
        this.interfaceDeclaration = interfaceDeclaration;
        addChild(interfaceDeclaration);
    }
}
