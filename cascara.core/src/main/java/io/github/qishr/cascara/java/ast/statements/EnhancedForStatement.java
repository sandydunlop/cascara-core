package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// EnhancedForStatement:
/// for ( LocalVariableDeclaration : Expression ) Statement
public class EnhancedForStatement extends ASTNode implements ScopingConstruct {
    private LocalVariableDeclaration localVariableDeclaration = null;
    private Expression expression = null;
    private Statement statement = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

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

    public Statement getStatement() {
        return statement;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
        addChild(statement);
    }
}
