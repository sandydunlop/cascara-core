package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// BasicForStatementNoShortIf:
/// for ( [ForInit] ; [Expression] ; [ForUpdate] ) StatementNoShortIf
public class BasicForStatementNoShortIf extends ASTNode {
    private ForInit forInit = null;
    private Expression expression = null;
    private ForUpdate forUpdate = null;
    private StatementNoShortIf statementNoShortIf = null;

    public ForInit getForInit() {
        return forInit;
    }

    public void setForInit(ForInit forInit) {
        this.forInit = forInit;
        addChild(forInit);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public ForUpdate getForUpdate() {
        return forUpdate;
    }

    public void setForUpdate(ForUpdate forUpdate) {
        this.forUpdate = forUpdate;
        addChild(forUpdate);
    }

    public StatementNoShortIf getStatementNoShortIf() {
        return statementNoShortIf;
    }

    public void setStatementNoShortIf(StatementNoShortIf statementNoShortIf) {
        this.statementNoShortIf = statementNoShortIf;
        addChild(statementNoShortIf);
    }
}
