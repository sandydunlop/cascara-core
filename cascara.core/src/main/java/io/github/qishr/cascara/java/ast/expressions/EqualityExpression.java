package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// EqualityExpression:
/// RelationalExpression
/// EqualityExpression == RelationalExpression
/// EqualityExpression != RelationalExpression
public class EqualityExpression extends ASTNode {
    private RelationalExpression relationalExpression = null;
    private EqualityExpression equalityExpression = null;
    private boolean equal = false;
    private boolean notEqual = false;

    public RelationalExpression getRelationalExpression() {
        return relationalExpression;
    }

    public void setRelationalExpression(RelationalExpression relationalExpression) {
        this.relationalExpression = relationalExpression;
        addChild(relationalExpression);
    }

    public EqualityExpression getEqualityExpression() {
        return equalityExpression;
    }

    public void setEqualityExpression(EqualityExpression equalityExpression) {
        this.equalityExpression = equalityExpression;
        addChild(equalityExpression);
    }

    public boolean isEqual() {
        return equal;
    }

    public void setEqual(boolean equal) {
        this.equal = equal;
    }

    public boolean isNotEqual() {
        return notEqual;
    }

    public void setNotEqual(boolean notEqual) {
        this.notEqual = notEqual;
    }


}
