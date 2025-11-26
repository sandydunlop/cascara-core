package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Primary:
/// PrimaryNoNewArray
/// ArrayCreationExpression
public class Primary extends ASTNode {
    private PrimaryNoNewArray primaryNoNewArray = null;
    private ArrayCreationExpression arrayCreationExpression = null;

    public PrimaryNoNewArray getPrimaryNoNewArray() {
        return primaryNoNewArray;
    }

    public void setPrimaryNoNewArray(PrimaryNoNewArray primaryNoNewArray) {
        this.primaryNoNewArray = primaryNoNewArray;
        addChild(primaryNoNewArray);
    }

    public ArrayCreationExpression getArrayCreationExpression() {
        return arrayCreationExpression;
    }

    public void setArrayCreationExpression(ArrayCreationExpression arrayCreationExpression) {
        this.arrayCreationExpression = arrayCreationExpression;
        addChild(arrayCreationExpression);
    }
}
