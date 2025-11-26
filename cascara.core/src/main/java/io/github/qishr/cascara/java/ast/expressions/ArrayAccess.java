package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ExpressionName;

/// ArrayAccess:
/// ExpressionName [ Expression ]
/// PrimaryNoNewArray [ Expression ]
/// ArrayCreationExpressionWithInitializer [ Expression ]
public class ArrayAccess extends ASTNode {
    private ExpressionName expressionName = null;
    private Expression expression = null;
    private PrimaryNoNewArray primaryNoNewArray = null;
    private ArrayCreationExpressionWithInitializer arrayCreationExpressionWithInitializer = null;

    public ExpressionName getExpressionName() {
        return expressionName;
    }

    public void setExpressionName(ExpressionName expressionName) {
        this.expressionName = expressionName;
        addChild(expressionName);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public PrimaryNoNewArray getPrimaryNoNewArray() {
        return primaryNoNewArray;
    }

    public void setPrimaryNoNewArray(PrimaryNoNewArray primaryNoNewArray) {
        this.primaryNoNewArray = primaryNoNewArray;
        addChild(primaryNoNewArray);
    }

    public ArrayCreationExpressionWithInitializer getArrayCreationExpressionWithInitializer() {
        return arrayCreationExpressionWithInitializer;
    }

    public void setArrayCreationExpressionWithInitializer(
            ArrayCreationExpressionWithInitializer arrayCreationExpressionWithInitializer) {
        this.arrayCreationExpressionWithInitializer = arrayCreationExpressionWithInitializer;
        addChild(arrayCreationExpressionWithInitializer);
    }

}
