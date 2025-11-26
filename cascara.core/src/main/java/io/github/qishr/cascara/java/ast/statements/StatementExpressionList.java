package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// StatementExpressionList:
/// StatementExpression {, StatementExpression}
public class StatementExpressionList extends ASTNode {
    private List<StatementExpression> statementExpressionList = new ArrayList<>();

    public void addStatementExpression(StatementExpression item) {
        statementExpressionList.add(item);
        addChild(item);
    }
    public List<StatementExpression> getStatementExpressionList() {
        return statementExpressionList;
    }
}
