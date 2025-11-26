package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// IfThenElseStatementNoShortIf:
/// if ( Expression ) StatementNoShortIf else StatementNoShortIf
public class IfThenElseStatementNoShortIf extends ASTNode {
    private Expression expression = null;
    private StatementNoShortIf statementNoShortIf = null;
    private StatementNoShortIf lastStatementNoShortIf = null;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public StatementNoShortIf getStatementNoShortIf() {
        return statementNoShortIf;
    }

    public void setStatementNoShortIf(StatementNoShortIf statementNoShortIf) {
        this.statementNoShortIf = statementNoShortIf;
        addChild(statementNoShortIf);
    }

    public StatementNoShortIf getLastStatementNoShortIf() {
        return lastStatementNoShortIf;
    }

    public void setLastStatementNoShortIf(StatementNoShortIf lastStatementNoShortIf) {
        this.lastStatementNoShortIf = lastStatementNoShortIf;
        addChild(lastStatementNoShortIf);
    }


}
