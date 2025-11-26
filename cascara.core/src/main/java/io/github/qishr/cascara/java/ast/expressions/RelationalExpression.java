package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RelationalExpression:
/// ShiftExpression
/// RelationalExpression < ShiftExpression
/// RelationalExpression > ShiftExpression
/// RelationalExpression <= ShiftExpression
/// RelationalExpression >= ShiftExpression
/// InstanceofExpression
public class RelationalExpression extends ASTNode {
    private ShiftExpression shiftExpression = null;
    private RelationalExpression relationalExpression = null;
    private boolean lessThan = false;
    private boolean greterThan = false;
    private boolean lessThanOrEqualTo = false;
    private boolean greterThanOrEqualTo = false;
    private InstanceofExpression instanceofExpression = null;

    public ShiftExpression getShiftExpression() {
        return shiftExpression;
    }

    public void setShiftExpression(ShiftExpression shiftExpression) {
        this.shiftExpression = shiftExpression;
        addChild(shiftExpression);
    }

    public RelationalExpression getRelationalExpression() {
        return relationalExpression;
    }

    public void setRelationalExpression(RelationalExpression relationalExpression) {
        this.relationalExpression = relationalExpression;
        addChild(relationalExpression);
    }

    public boolean isLessThan() {
        return lessThan;
    }

    public void setLessThan(boolean lessThan) {
        this.lessThan = lessThan;
    }

    public boolean isGreterThan() {
        return greterThan;
    }

    public void setGreterThan(boolean greterThan) {
        this.greterThan = greterThan;
    }

    public boolean isLessThanOrEqualTo() {
        return lessThanOrEqualTo;
    }

    public void setLessThanOrEqualTo(boolean lessThanOrEqualTo) {
        this.lessThanOrEqualTo = lessThanOrEqualTo;
    }

    public boolean isGreterThanOrEqualTo() {
        return greterThanOrEqualTo;
    }

    public void setGreterThanOrEqualTo(boolean greterThanOrEqualTo) {
        this.greterThanOrEqualTo = greterThanOrEqualTo;
        addChild(instanceofExpression);
    }

    public InstanceofExpression getInstanceofExpression() {
        return instanceofExpression;
    }

    public void setInstanceofExpression(InstanceofExpression instanceofExpression) {
        this.instanceofExpression = instanceofExpression;
        addChild(instanceofExpression);
    }


}
