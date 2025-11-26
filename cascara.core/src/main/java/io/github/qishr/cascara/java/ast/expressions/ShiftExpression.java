package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ShiftExpression:
/// AdditiveExpression
/// ShiftExpression << AdditiveExpression
/// ShiftExpression >> AdditiveExpression
/// ShiftExpression >>> AdditiveExpression
public class ShiftExpression extends ASTNode {
    private AdditiveExpression additiveExpression = null;
    private ShiftExpression shiftExpression = null;
    private boolean shiftLeft = false;
    private boolean shiftRight = false;
    private boolean unsignedShiftRight = false;

    public AdditiveExpression getAdditiveExpression() {
        return additiveExpression;
    }

    public void setAdditiveExpression(AdditiveExpression additiveExpression) {
        this.additiveExpression = additiveExpression;
        addChild(additiveExpression);
    }

    public ShiftExpression getShiftExpression() {
        return shiftExpression;
    }

    public void setShiftExpression(ShiftExpression shiftExpression) {
        this.shiftExpression = shiftExpression;
        addChild(shiftExpression);
    }

    public boolean isShiftLeft() {
        return shiftLeft;
    }

    public void setShiftLeft(boolean shiftLeft) {
        this.shiftLeft = shiftLeft;
    }

    public boolean isShiftRight() {
        return shiftRight;
    }

    public void setShiftRight(boolean shiftRight) {
        this.shiftRight = shiftRight;
    }

    public boolean isUnsignedShiftRight() {
        return unsignedShiftRight;
    }

    // TODO: What's this called?
    public void setUnsignedShiftRight(boolean unsignedShiftRight) {
        this.unsignedShiftRight = unsignedShiftRight;
    }


}
