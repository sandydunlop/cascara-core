package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ConditionalAndExpression:
/// InclusiveOrExpression
/// ConditionalAndExpression && InclusiveOrExpression
public class ConditionalAndExpression extends ASTNode {
    private InclusiveOrExpression inclusiveOrExpression = null;
    private ConditionalAndExpression conditionalAndExpression = null;

    public InclusiveOrExpression getInclusiveOrExpression() {
        return inclusiveOrExpression;
    }

    public void setInclusiveOrExpression(InclusiveOrExpression inclusiveOrExpression) {
        this.inclusiveOrExpression = inclusiveOrExpression;
        addChild(inclusiveOrExpression);
    }

    public ConditionalAndExpression getConditionalAndExpression() {
        return conditionalAndExpression;
    }

    public void setConditionalAndExpression(ConditionalAndExpression conditionalAndExpression) {
        this.conditionalAndExpression = conditionalAndExpression;
        addChild(conditionalAndExpression);
    }


}
