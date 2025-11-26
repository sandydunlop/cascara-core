package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AssignmentExpression:
/// ConditionalExpression
/// Assignment
public class AssignmentExpression extends ASTNode {
    private ConditionalExpression conditionalExpression = null;
    private Assignment assignment = null;

    public ConditionalExpression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(ConditionalExpression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
        addChild(conditionalExpression);
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
        addChild(assignment);
    }

}
