package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ConditionalOrExpression:
/// ConditionalAndExpression
/// ConditionalOrExpression || ConditionalAndExpression
public class ConditionalOrExpression extends ASTNode {
    private ConditionalAndExpression conditionalAndExpression = null;
    private ConditionalOrExpression conditionalOrExpression = null;

    public ConditionalAndExpression getConditionalAndExpression() {
        return conditionalAndExpression;
    }

    public void setConditionalAndExpression(ConditionalAndExpression conditionalAndExpression) {
        this.conditionalAndExpression = conditionalAndExpression;
        addChild(conditionalAndExpression);
    }

    public ConditionalOrExpression getConditionalOrExpression() {
        return conditionalOrExpression;
    }

    public void setConditionalOrExpression(ConditionalOrExpression conditionalOrExpression) {
        this.conditionalOrExpression = conditionalOrExpression;
        addChild(conditionalOrExpression);
    }
}
