package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// BlockStatement:
/// LocalClassOrInterfaceDeclaration
/// LocalVariableDeclarationStatement
/// Statement
public class BlockStatement extends ASTNode {
    private LocalClassOrInterfaceDeclaration localClassOrInterfaceDeclaration = null;
    private LocalVariableDeclarationStatement localVariableDeclarationStatement = null;
    private Statement statement = null;

    public LocalClassOrInterfaceDeclaration getLocalClassOrInterfaceDeclaration() {
        return localClassOrInterfaceDeclaration;
    }
    public void setLocalClassOrInterfaceDeclaration(LocalClassOrInterfaceDeclaration localClassOrInterfaceDeclaration) {
        this.localClassOrInterfaceDeclaration = localClassOrInterfaceDeclaration;
        addChild(localClassOrInterfaceDeclaration);
    }
    public LocalVariableDeclarationStatement getLocalVariableDeclarationStatement() {
        return localVariableDeclarationStatement;
    }
    public void setLocalVariableDeclarationStatement(LocalVariableDeclarationStatement localVariableDeclarationStatement) {
        this.localVariableDeclarationStatement = localVariableDeclarationStatement;
        addChild(localVariableDeclarationStatement);
    }
    public Statement getStatement() {
        return statement;
    }
    public void setStatement(Statement statement) {
        this.statement = statement;
        addChild(statement);
    }


}
