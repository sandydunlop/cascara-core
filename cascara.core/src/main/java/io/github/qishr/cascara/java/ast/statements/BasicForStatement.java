package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// BasicForStatement:
/// for ( [ForInit] ; [Expression] ; [ForUpdate] ) Statement
public class BasicForStatement extends ASTNode implements ScopingConstruct {
    private ForInit forInit = null;
    private Expression expression = null;
    private ForUpdate forUpdate = null;
    private Statement statement = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

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

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        addChild(statement);
    }

}
