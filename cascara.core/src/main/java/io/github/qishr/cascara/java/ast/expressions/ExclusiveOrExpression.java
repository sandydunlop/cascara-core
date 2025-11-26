package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ExclusiveOrExpression:
/// AndExpression
/// ExclusiveOrExpression ^ AndExpression
public class ExclusiveOrExpression extends ASTNode {
    private AndExpression andExpression = null;
    private ExclusiveOrExpression exclusiveOrExpression = null;

    public AndExpression getAndExpression() {
        return andExpression;
    }

    public void setAndExpression(AndExpression andExpression) {
        this.andExpression = andExpression;
        addChild(andExpression);
    }

    public ExclusiveOrExpression getExclusiveOrExpression() {
        return exclusiveOrExpression;
    }

    public void setExclusiveOrExpression(ExclusiveOrExpression exclusiveOrExpression) {
        this.exclusiveOrExpression = exclusiveOrExpression;
        addChild(exclusiveOrExpression);
    }

}
