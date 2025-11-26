package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// UnaryExpression:
/// PreIncrementExpression
/// PreDecrementExpression
/// + UnaryExpression
/// - UnaryExpression
/// UnaryExpressionNotPlusMinus
public class UnaryExpression extends ASTNode {
    private PreIncrementExpression preIncrementExpression = null;
    private PreDecrementExpression preDecrementExpression = null;
    private boolean add = false;
    private boolean subtract = false;
    private UnaryExpressionNotPlusMinus unaryExpressionNotPlusMinus = null;
    private UnaryExpression unaryExpression = null;

    public UnaryExpression getUnaryExpression() {
        return unaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
    }

    public PreIncrementExpression getPreIncrementExpression() {
        return preIncrementExpression;
    }

    public void setPreIncrementExpression(PreIncrementExpression preIncrementExpression) {
        this.preIncrementExpression = preIncrementExpression;
        addChild(preIncrementExpression);
    }

    public PreDecrementExpression getPreDecrementExpression() {
        return preDecrementExpression;
    }

    public void setPreDecrementExpression(PreDecrementExpression preDecrementExpression) {
        this.preDecrementExpression = preDecrementExpression;
        addChild(preDecrementExpression);
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isSubtract() {
        return subtract;
    }

    public void setSubtract(boolean subtract) {
        this.subtract = subtract;
    }

    public UnaryExpressionNotPlusMinus getUnaryExpressionNotPlusMinus() {
        return unaryExpressionNotPlusMinus;
    }

    public void setUnaryExpressionNotPlusMinus(UnaryExpressionNotPlusMinus unaryExpressionNotPlusMinus) {
        this.unaryExpressionNotPlusMinus = unaryExpressionNotPlusMinus;
        addChild(unaryExpressionNotPlusMinus);
    }


}
