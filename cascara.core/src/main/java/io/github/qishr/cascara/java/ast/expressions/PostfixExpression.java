package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ExpressionName;

/// PostfixExpression:
/// Primary
/// ExpressionName
/// PostIncrementExpression
/// PostDecrementExpression
public class PostfixExpression extends ASTNode {
    private Primary primary = null;
    private ExpressionName expressionName = null;
    private PostIncrementExpression postIncrementExpression = null;
    private PostDecrementExpression postDecrementExpression = null;

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
        addChild(primary);
    }

    public ExpressionName getExpressionName() {
        return expressionName;
    }

    public void setExpressionName(ExpressionName expressionName) {
        this.expressionName = expressionName;
        addChild(expressionName);
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


}
