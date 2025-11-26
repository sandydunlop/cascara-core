package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Expression:
/// LambdaExpression
/// AssignmentExpression
public class Expression extends ASTNode {
    private LambdaExpression lambdaExpression = null;
    private AssignmentExpression assignmentExpression = null;

    public LambdaExpression getLambdaExpression() {
        return lambdaExpression;
    }

    public void setLambdaExpression(LambdaExpression lambdaExpression) {
        this.lambdaExpression = lambdaExpression;
        addChild(lambdaExpression);
    }

    public AssignmentExpression getAssignmentExpression() {
        return assignmentExpression;
    }

    public void setAssignmentExpression(AssignmentExpression assignmentExpression) {
        this.assignmentExpression = assignmentExpression;
        addChild(assignmentExpression);
    }


}
