package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ExpressionName;

/// ClassInstanceCreationExpression:
/// UnqualifiedClassInstanceCreationExpression
/// ExpressionName . UnqualifiedClassInstanceCreationExpression
/// Primary . UnqualifiedClassInstanceCreationExpression
public class ClassInstanceCreationExpression extends ASTNode {
    private UnqualifiedClassInstanceCreationExpression unqualifiedClassInstanceCreationExpression = null;
    private ExpressionName expressionName = null;
    private Primary primary = null;

    public UnqualifiedClassInstanceCreationExpression getUnqualifiedClassInstanceCreationExpression() {
        return unqualifiedClassInstanceCreationExpression;
    }

    public void setUnqualifiedClassInstanceCreationExpression(
            UnqualifiedClassInstanceCreationExpression unqualifiedClassInstanceCreationExpression) {
        this.unqualifiedClassInstanceCreationExpression = unqualifiedClassInstanceCreationExpression;
        addChild(unqualifiedClassInstanceCreationExpression);
    }

    public ExpressionName getExpressionName() {
        return expressionName;
    }

    public void setExpressionName(ExpressionName expressionName) {
        this.expressionName = expressionName;
        addChild(expressionName);
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
        addChild(primary);
    }

}
