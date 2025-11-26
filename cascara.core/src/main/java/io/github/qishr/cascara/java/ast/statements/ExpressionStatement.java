package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ExpressionStatement:
/// StatementExpression ;
public class ExpressionStatement extends ASTNode {
    private StatementExpression statementExpression = null;

    public StatementExpression getStatementExpression() {
        return statementExpression;
    }

    public void setStatementExpression(StatementExpression statementExpression) {
        this.statementExpression = statementExpression;
        addChild(statementExpression);
    }
}
