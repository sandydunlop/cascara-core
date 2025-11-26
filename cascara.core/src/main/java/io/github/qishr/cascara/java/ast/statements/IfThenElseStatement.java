package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// IfThenElseStatement:
/// if ( Expression ) StatementNoShortIf else Statement
public class IfThenElseStatement extends ASTNode {
    private Expression expression = null;
    private StatementNoShortIf statementNoShortIf = null;
    private Statement statement = null;

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

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        addChild(statement);
    }

}
