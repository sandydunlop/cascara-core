package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// PreDecrementExpression:
/// -- UnaryExpression
public class PreDecrementExpression extends ASTNode {
    private UnaryExpression unaryExpression = null;

    public UnaryExpression getUnaryExpression() {
        return unaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
        addChild(unaryExpression);
    }

}
