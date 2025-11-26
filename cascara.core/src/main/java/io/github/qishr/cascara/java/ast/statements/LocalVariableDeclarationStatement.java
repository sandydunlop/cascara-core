package io.github.qishr.cascara.java.ast.statements;

/// LocalVariableDeclarationStatement:
/// LocalVariableDeclaration ;
public class LocalVariableDeclarationStatement extends BlockStatement {
    private LocalVariableDeclaration localVariableDeclaration = null;

    public LocalVariableDeclaration getLocalVariableDeclaration() {
        return localVariableDeclaration;
    }

    public void setLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration) {
        this.localVariableDeclaration = localVariableDeclaration;
        addChild(localVariableDeclaration);
    }


}
