package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ClassBodyDeclaration:
/// ClassMemberDeclaration
/// InstanceInitializer
/// StaticInitializer
/// ConstructorDeclaration
public class ClassBodyDeclaration extends ASTNode {
    private ClassMemberDeclaration classMemberDeclaration = null;
    private InstanceInitializer instanceInitializer = null;
    private StaticInitializer staticInitializer = null;
    private ConstructorDeclaration constructorDeclaration = null;

    public ClassMemberDeclaration getClassMemberDeclaration() {
        return classMemberDeclaration;
    }

    public void setClassMemberDeclaration(ClassMemberDeclaration classMemberDeclaration) {
        this.classMemberDeclaration = classMemberDeclaration;
        addChild(classMemberDeclaration);
    }

    public InstanceInitializer getInstanceInitializer() {
        return instanceInitializer;
    }

    public void setInstanceInitializer(InstanceInitializer instanceInitializer) {
        this.instanceInitializer = instanceInitializer;
        addChild(instanceInitializer);
    }

    public StaticInitializer getStaticInitializer() {
        return staticInitializer;
    }

    public void setStaticInitializer(StaticInitializer staticInitializer) {
        this.staticInitializer = staticInitializer;
        addChild(staticInitializer);
    }

    public ConstructorDeclaration getConstructorDeclaration() {
        return constructorDeclaration;
    }

    public void setConstructorDeclaration(ConstructorDeclaration constructorDeclaration) {
        this.constructorDeclaration = constructorDeclaration;
        addChild(constructorDeclaration);
    }

}
