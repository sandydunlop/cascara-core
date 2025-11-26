package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.ConditionalExpression;

/// CaseConstant:
/// ConditionalExpression
public class CaseConstant extends ASTNode {
    private ConditionalExpression conditionalExpression = null;

    public ConditionalExpression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(ConditionalExpression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
        addChild(conditionalExpression);
    }

}
