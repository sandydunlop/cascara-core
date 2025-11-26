package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassDeclaration;
import io.github.qishr.cascara.java.ast.types.ConstantDeclaration;

/// AnnotationInterfaceMemberDeclaration:
/// AnnotationInterfaceElementDeclaration
/// ConstantDeclaration
/// ClassDeclaration
/// InterfaceDeclaration
/// ;
public class AnnotationInterfaceMemberDeclaration extends ASTNode {
    private AnnotationInterfaceElementDeclaration annotationInterfaceElementDeclaration = null;
    private ConstantDeclaration constantDeclaration = null;
    private ClassDeclaration classDeclaration = null;
    private InterfaceDeclaration interfaceDeclaration = null;

    public AnnotationInterfaceElementDeclaration getAnnotationInterfaceElementDeclaration() {
        return annotationInterfaceElementDeclaration;
    }

    public void setAnnotationInterfaceElementDeclaration(
            AnnotationInterfaceElementDeclaration annotationInterfaceElementDeclaration) {
        this.annotationInterfaceElementDeclaration = annotationInterfaceElementDeclaration;
        addChild(annotationInterfaceElementDeclaration);
    }

    public ConstantDeclaration getConstantDeclaration() {
        return constantDeclaration;
    }

    public void setConstantDeclaration(ConstantDeclaration constantDeclaration) {
        this.constantDeclaration = constantDeclaration;
        addChild(constantDeclaration);
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
