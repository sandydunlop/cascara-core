package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassDeclaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.types.ConstantDeclaration;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// InterfaceMemberDeclaration:
/// ConstantDeclaration
/// InterfaceMethodDeclaration
/// ClassDeclaration
/// InterfaceDeclaration
/// ;
public class InterfaceMemberDeclaration extends ASTNode {
    private ConstantDeclaration constantDeclaration = null;
    private InterfaceMethodDeclaration interfaceMethodDeclaration = null;
    private ClassDeclaration classDeclaration = null;
    private InterfaceDeclaration interfaceDeclaration = null;

    public ConstantDeclaration getConstantDeclaration() {
        return constantDeclaration;
    }

    public void setConstantDeclaration(ConstantDeclaration constantDeclaration) {
        this.constantDeclaration = constantDeclaration;
        addChild(constantDeclaration);
    }

    public InterfaceMethodDeclaration getInterfaceMethodDeclaration() {
        return interfaceMethodDeclaration;
    }

    public void setInterfaceMethodDeclaration(InterfaceMethodDeclaration interfaceMethodDeclaration) {
        this.interfaceMethodDeclaration = interfaceMethodDeclaration;
        addChild(interfaceMethodDeclaration);
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
