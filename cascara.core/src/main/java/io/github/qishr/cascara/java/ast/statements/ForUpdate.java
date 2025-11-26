package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ForUpdate:
/// StatementExpressionList
public class ForUpdate extends ASTNode {
    private StatementExpressionList statementExpressionList = null;

    public StatementExpressionList getStatementExpressionList() {
        return statementExpressionList;
    }

    public void setStatementExpressionList(StatementExpressionList statementExpressionList) {
        this.statementExpressionList = statementExpressionList;
        addChild(statementExpressionList);
    }

}
