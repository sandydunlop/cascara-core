package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Assignment:
/// LeftHandSide AssignmentOperator Expression
public class Assignment extends ASTNode {
    private LeftHandSide leftHandSide = null;
    private AssignmentOperator assignmentOperator = null;
    private Expression expression = null;

    public LeftHandSide getLeftHandSide() {
        return leftHandSide;
    }

    public void setLeftHandSide(LeftHandSide leftHandSide) {
        this.leftHandSide = leftHandSide;
        addChild(leftHandSide);
    }

    public AssignmentOperator getAssignmentOperator() {
        return assignmentOperator;
    }

    public void setAssignmentOperator(AssignmentOperator assignmentOperator) {
        this.assignmentOperator = assignmentOperator;
        addChild(assignmentOperator);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }
}
