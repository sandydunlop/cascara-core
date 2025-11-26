package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// PreIncrementExpression:
/// ++ UnaryExpression
public class PreIncrementExpression extends ASTNode {
    private UnaryExpression unaryExpression = null;

    public UnaryExpression getUnaryExpression() {
        return unaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
        addChild(unaryExpression);
    }

}
