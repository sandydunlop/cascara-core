package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// PostIncrementExpression:
/// PostfixExpression ++
public class PostIncrementExpression extends ASTNode {
    private PostfixExpression postfixExpression = null;

    public PostfixExpression getPostfixExpression() {
        return postfixExpression;
    }

    public void setPostfixExpression(PostfixExpression postfixExpression) {
        this.postfixExpression = postfixExpression;
        addChild(postfixExpression);
    }

}
