package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.NormalInterfaceDeclaration;

/// LocalClassOrInterfaceDeclaration:
/// ClassDeclaration
/// NormalInterfaceDeclaration
public class LocalClassOrInterfaceDeclaration extends ASTNode {
    private ClassDeclaration classDeclaration = null;
    private NormalInterfaceDeclaration normalInterfaceDeclaration = null;

    // public LocalClassOrInterfaceDeclaration(ASTNode declaration) {
    //     if (declaration instanceof ClassDeclaration decl) {
    //         setClassDeclaration(decl);
    //     } else if (declaration instanceof NormalInterfaceDeclaration decl) {
    //         setNormalInterfaceDeclaration(decl);
    //     }
    // }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }
    public void setClassDeclaration(ClassDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
        addChild(classDeclaration);
    }
    public NormalInterfaceDeclaration getNormalInterfaceDeclaration() {
        return normalInterfaceDeclaration;
    }
    public void setNormalInterfaceDeclaration(NormalInterfaceDeclaration normalInterfaceDeclaration) {
        this.normalInterfaceDeclaration = normalInterfaceDeclaration;
        addChild(normalInterfaceDeclaration);
    }


}
