package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ForInit:
/// StatementExpressionList
/// LocalVariableDeclaration
public class ForInit extends ASTNode {
    private StatementExpressionList statementExpressionList = null;
    private LocalVariableDeclaration localVariableDeclaration = null;

    public StatementExpressionList getStatementExpressionList() {
        return statementExpressionList;
    }

    public void setStatementExpressionList(StatementExpressionList statementExpressionList) {
        this.statementExpressionList = statementExpressionList;
        addChild(statementExpressionList);
    }

    public LocalVariableDeclaration getLocalVariableDeclaration() {
        return localVariableDeclaration;
    }

    public void setLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration) {
        this.localVariableDeclaration = localVariableDeclaration;
        addChild(localVariableDeclaration);
    }

}
