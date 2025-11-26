package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AdditiveExpression:
/// MultiplicativeExpression
/// AdditiveExpression + MultiplicativeExpression
/// AdditiveExpression - MultiplicativeExpression
public class AdditiveExpression extends ASTNode {
    private MultiplicativeExpression multiplicativeExpression = null;
    private boolean add = false;
    private boolean subtract = false;
    private AdditiveExpression additiveExpression = null;

    public MultiplicativeExpression getMultiplicativeExpression() {
        return multiplicativeExpression;
    }

    public void setMultiplicativeExpression(MultiplicativeExpression multiplicativeExpression) {
        this.multiplicativeExpression = multiplicativeExpression;
        addChild(multiplicativeExpression);
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isSubtract() {
        return subtract;
    }

    public void setSubtract(boolean subtract) {
        this.subtract = subtract;
    }

    public AdditiveExpression getAdditiveExpression() {
        return additiveExpression;
    }

    public void setAdditiveExpression(AdditiveExpression additiveExpression) {
        this.additiveExpression = additiveExpression;
        addChild(additiveExpression);
    }


}
