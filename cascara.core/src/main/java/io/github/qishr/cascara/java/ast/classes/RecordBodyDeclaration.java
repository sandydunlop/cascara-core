package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RecordBodyDeclaration:
/// ClassBodyDeclaration
/// CompactConstructorDeclaration
public class RecordBodyDeclaration extends ASTNode {
    private ClassBodyDeclaration classBodyDeclaration = null;
    private CompactConstructorDeclaration compactConstructorDeclaration = null;

    public ClassBodyDeclaration getClassBodyDeclaration() {
        return classBodyDeclaration;
    }

    public void setClassBodyDeclaration(ClassBodyDeclaration classBodyDeclaration) {
        this.classBodyDeclaration = classBodyDeclaration;
        addChild(classBodyDeclaration);
    }

    public CompactConstructorDeclaration getCompactConstructorDeclaration() {
        return compactConstructorDeclaration;
    }

    public void setCompactConstructorDeclaration(CompactConstructorDeclaration compactConstructorDeclaration) {
        this.compactConstructorDeclaration = compactConstructorDeclaration;
        addChild(compactConstructorDeclaration);
    }

}
