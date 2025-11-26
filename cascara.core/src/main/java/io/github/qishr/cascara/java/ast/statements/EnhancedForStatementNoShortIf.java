package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// EnhancedForStatementNoShortIf:
/// for ( LocalVariableDeclaration : Expression ) StatementNoShortIf
public class EnhancedForStatementNoShortIf extends ASTNode {
    private LocalVariableDeclaration localVariableDeclaration = null;
    private Expression expression = null;
    private StatementNoShortIf statementNoShortIf = null;

    public LocalVariableDeclaration getLocalVariableDeclaration() {
        return localVariableDeclaration;
    }

    public void setLocalVariableDeclaration(LocalVariableDeclaration localVariableDeclaration) {
        this.localVariableDeclaration = localVariableDeclaration;
        addChild(localVariableDeclaration);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public StatementNoShortIf getStatement() {
        return statementNoShortIf;
    }

    public void setStatement(StatementNoShortIf statementNoShortIf) {
        this.statementNoShortIf = statementNoShortIf;
        addChild(statementNoShortIf);
    }

}
