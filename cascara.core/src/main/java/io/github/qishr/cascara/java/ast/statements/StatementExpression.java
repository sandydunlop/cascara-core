package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Assignment;
import io.github.qishr.cascara.java.ast.expressions.ClassInstanceCreationExpression;
import io.github.qishr.cascara.java.ast.expressions.MethodInvocation;
import io.github.qishr.cascara.java.ast.expressions.PostDecrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PostIncrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PreDecrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PreIncrementExpression;

/// StatementExpression:
/// Assignment
/// PreIncrementExpression
/// PreDecrementExpression
/// PostIncrementExpression
/// PostDecrementExpression
/// MethodInvocation
/// ClassInstanceCreationExpression
public class StatementExpression extends ASTNode {
    private Assignment assignment = null;
    private PreIncrementExpression preIncrementExpression = null;
    private PreDecrementExpression preDecrementExpression = null;
    private PostIncrementExpression postIncrementExpression = null;
    private PostDecrementExpression postDecrementExpression = null;
    private MethodInvocation methodInvocation = null;
    private ClassInstanceCreationExpression classInstanceCreationExpression = null;

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
        addChild(assignment);
    }

    public PreIncrementExpression getPreIncrementExpression() {
        return preIncrementExpression;
    }

    public void setPreIncrementExpression(PreIncrementExpression preIncrementExpression) {
        this.preIncrementExpression = preIncrementExpression;
        addChild(preIncrementExpression);
    }

    public PreDecrementExpression getPreDecrementExpression() {
        return preDecrementExpression;
    }

    public void setPreDecrementExpression(PreDecrementExpression preDecrementExpression) {
        this.preDecrementExpression = preDecrementExpression;
        addChild(preDecrementExpression);
    }

    public PostIncrementExpression getPostIncrementExpression() {
        return postIncrementExpression;
    }

    public void setPostIncrementExpression(PostIncrementExpression postIncrementExpression) {
        this.postIncrementExpression = postIncrementExpression;
        addChild(postIncrementExpression);
    }

    public PostDecrementExpression getPostDecrementExpression() {
        return postDecrementExpression;
    }

    public void setPostDecrementExpression(PostDecrementExpression postDecrementExpression) {
        this.postDecrementExpression = postDecrementExpression;
        addChild(postDecrementExpression);
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
        addChild(methodInvocation);
    }

    public ClassInstanceCreationExpression getClassInstanceCreationExpression() {
        return classInstanceCreationExpression;
    }

    public void setClassInstanceCreationExpression(ClassInstanceCreationExpression classInstanceCreationExpression) {
        this.classInstanceCreationExpression = classInstanceCreationExpression;
        addChild(classInstanceCreationExpression);
    }
}
