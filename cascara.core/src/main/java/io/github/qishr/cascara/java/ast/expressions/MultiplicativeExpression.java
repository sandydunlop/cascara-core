package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// MultiplicativeExpression:
/// UnaryExpression
/// MultiplicativeExpression * UnaryExpression
/// MultiplicativeExpression / UnaryExpression
/// MultiplicativeExpression % UnaryExpression
public class MultiplicativeExpression extends ASTNode {
    private UnaryExpression unaryExpression = null;
    private MultiplicativeExpression multiplicativeExpression = null;
    private boolean multiply = false;
    private boolean divide = false;
    private boolean percent = false;

    public UnaryExpression getUnaryExpression() {
        return unaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
        addChild(unaryExpression);
    }

    public MultiplicativeExpression getMultiplicativeExpression() {
        return multiplicativeExpression;
    }

    public void setMultiplicativeExpression(MultiplicativeExpression multiplicativeExpression) {
        this.multiplicativeExpression = multiplicativeExpression;
        addChild(multiplicativeExpression);
    }

    public boolean isMultiply() {
        return multiply;
    }

    public void setMultiply(boolean multiply) {
        this.multiply = multiply;
        addChild(multiplicativeExpression);
    }

    public boolean isDivide() {
        return divide;
    }

    public void setDivide(boolean divide) {
        this.divide = divide;
    }

    public boolean isPercent() {
        return percent;
    }

    public void setModulo(boolean percent) {
        this.percent = percent;
    }


}
