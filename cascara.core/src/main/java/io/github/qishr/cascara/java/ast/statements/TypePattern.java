package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// TypePattern:
/// LocalVariableDeclaration
public class TypePattern extends ASTNode {
    private LocalVariableDeclaration localVariableDeclaration = null;

    public LocalVariableDeclaration getLocalVariableDeclaration() {
        return localVariableDeclaration;
    }

    public void setLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration) {
        this.localVariableDeclaration = localVariableDeclaration;
        addChild(localVariableDeclaration);
    }

}
