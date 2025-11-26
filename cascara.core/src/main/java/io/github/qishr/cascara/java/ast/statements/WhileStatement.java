package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// WhileStatement:
/// while ( Expression ) Statement
public class WhileStatement extends ASTNode implements ScopingConstruct {
    private Expression expression = null;
    private Statement statement = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        addChild(statement);
    }

}
