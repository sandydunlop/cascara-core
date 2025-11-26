package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ExpressionName;

/// LeftHandSide:
/// ExpressionName
/// FieldAccess
/// ArrayAccess
public class LeftHandSide extends ASTNode {
    private ExpressionName expressionName = null;
    private FieldAccess fieldAccess = null;
    private ArrayAccess arrayAccess = null;

    public ExpressionName getExpressionName() {
        return expressionName;
    }

    public void setExpressionName(ExpressionName expressionName) {
        this.expressionName = expressionName;
        addChild(expressionName);
    }

    public FieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public void setFieldAccess(FieldAccess fieldAccess) {
        this.fieldAccess = fieldAccess;
        addChild(fieldAccess);
    }

    public ArrayAccess getArrayAccess() {
        return arrayAccess;
    }

    public void setArrayAccess(ArrayAccess arrayAccess) {
        this.arrayAccess = arrayAccess;
        addChild(arrayAccess);
    }

}
