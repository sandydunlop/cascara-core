package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ConditionalExpression:
/// ConditionalOrExpression
/// ConditionalOrExpression ? Expression : ConditionalExpression
/// ConditionalOrExpression ? Expression : LambdaExpression
public class ConditionalExpression extends ASTNode {
    private ConditionalOrExpression conditionalOrExpression = null;
    private Expression expression = null;
    private ConditionalExpression conditionalExpression = null;
    private LambdaExpression lambdaExpression = null;

    public ConditionalOrExpression getConditionalOrExpression() {
        return conditionalOrExpression;
    }

    public void setConditionalOrExpression(ConditionalOrExpression conditionalOrExpression) {
        this.conditionalOrExpression = conditionalOrExpression;
        addChild(conditionalOrExpression);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public ConditionalExpression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(ConditionalExpression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
        addChild(conditionalExpression);
    }

    public LambdaExpression getLambdaExpression() {
        return lambdaExpression;
    }

    public void setLambdaExpression(LambdaExpression lambdaExpression) {
        this.lambdaExpression = lambdaExpression;
        addChild(lambdaExpression);
    }

    @Override
    public String toString() {
        // TODO: Implement this
        return StringConstant.UNDEFINED;
    }
}
