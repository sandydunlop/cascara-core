package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AndExpression:
/// EqualityExpression
/// AndExpression & EqualityExpression
public class AndExpression extends ASTNode {
    private EqualityExpression equalityExpression = null;
    private AndExpression andExpression = null;

    public EqualityExpression getEqualityExpression() {
        return equalityExpression;
    }

    public void setEqualityExpression(EqualityExpression equalityExpression) {
        this.equalityExpression = equalityExpression;
        addChild(equalityExpression);
    }

    public AndExpression getAndExpression() {
        return andExpression;
    }

    public void setAndExpression(AndExpression andExpression) {
        this.andExpression = andExpression;
        addChild(andExpression);
    }

}
