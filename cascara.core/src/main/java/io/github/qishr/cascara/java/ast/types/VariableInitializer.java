package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.arrays.ArrayInitializer;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// VariableInitializer:
/// Expression
/// ArrayInitializer
public class VariableInitializer extends ASTNode {
    private Expression expression = null;
    private ArrayInitializer arrayInitializer = null;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public ArrayInitializer getArrayInitializer() {
        return arrayInitializer;
    }

    public void setArrayInitializer(ArrayInitializer arrayInitializer) {
        this.arrayInitializer = arrayInitializer;
        addChild(arrayInitializer);
    }

}
