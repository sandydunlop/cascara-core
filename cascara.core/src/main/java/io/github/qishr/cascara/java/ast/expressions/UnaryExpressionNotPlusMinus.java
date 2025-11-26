package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// UnaryExpressionNotPlusMinus:
/// PostfixExpression
/// ~ UnaryExpression
/// ! UnaryExpression
/// CastExpression
/// SwitchExpression
public class UnaryExpressionNotPlusMinus extends ASTNode {
    private PostfixExpression postfixExpression = null;
    private boolean tilde = false;
    private boolean not = false;
    private UnaryExpression UnaryExpression = null;
    private CastExpression castExpression = null;
    private SwitchExpression switchExpression = null;

    public PostfixExpression getPostfixExpression() {
        return postfixExpression;
    }

    public void setPostfixExpression(PostfixExpression postfixExpression) {
        this.postfixExpression = postfixExpression;
        addChild(postfixExpression);
    }

    public boolean isTilde() {
        return tilde;
    }

    public void setTilde(boolean minus) {
        this.tilde = minus;
    }

    public boolean isNot() {
        return not;
    }

    public void setNot(boolean not) {
        this.not = not;
    }

    public UnaryExpression getUnaryExpression() {
        return UnaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        UnaryExpression = unaryExpression;
        addChild(unaryExpression);
    }

    public CastExpression getCastExpression() {
        return castExpression;
    }

    public void setCastExpression(CastExpression castExpression) {
        this.castExpression = castExpression;
        addChild(castExpression);
    }

    public SwitchExpression getSwitchExpression() {
        return switchExpression;
    }

    public void setSwitchExpression(SwitchExpression switchExpression) {
        this.switchExpression = switchExpression;
        addChild(switchExpression);
    }


}
