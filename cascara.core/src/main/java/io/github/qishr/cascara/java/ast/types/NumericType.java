package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// NumericType:
/// IntegralType
/// FloatingPointType
public class NumericType extends ASTNode {
    private IntegralType integralType = null;
    private FloatingPointType floatingPointType = null;

    public IntegralType getIntegralType() {
        return integralType;
    }

    public void setIntegralType(IntegralType integralType) {
        this.integralType = integralType;
        addChild(integralType);
    }

    public FloatingPointType getFloatingPointType() {
        return floatingPointType;
    }

    public void setFloatingPointType(FloatingPointType floatingPointType) {
        this.floatingPointType = floatingPointType;
        addChild(floatingPointType);
    }

    public String toString() {
        if (integralType != null) {
            return integralType.toString();
        } else if (floatingPointType != null) {
            return floatingPointType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
