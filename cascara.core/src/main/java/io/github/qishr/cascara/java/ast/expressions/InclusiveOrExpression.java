package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// InclusiveOrExpression:
/// ExclusiveOrExpression
/// InclusiveOrExpression | ExclusiveOrExpression
public class InclusiveOrExpression extends ASTNode {
    private ExclusiveOrExpression exclusiveOrExpression = null;
    private InclusiveOrExpression inclusiveOrExpression = null;

    public ExclusiveOrExpression getExclusiveOrExpression() {
        return exclusiveOrExpression;
    }

    public void setExclusiveOrExpression(ExclusiveOrExpression exclusiveOrExpression) {
        this.exclusiveOrExpression = exclusiveOrExpression;
        addChild(exclusiveOrExpression);
    }

    public InclusiveOrExpression getInclusiveOrExpression() {
        return inclusiveOrExpression;
    }

    public void setInclusiveOrExpression(InclusiveOrExpression inclusiveOrExpression) {
        this.inclusiveOrExpression = inclusiveOrExpression;
        addChild(inclusiveOrExpression);
    }

}
