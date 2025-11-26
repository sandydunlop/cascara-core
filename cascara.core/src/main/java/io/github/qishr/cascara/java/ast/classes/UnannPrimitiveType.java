package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.NumericType;

/// UnannPrimitiveType:
/// NumericType
/// boolean
public class UnannPrimitiveType extends ASTNode {
    private NumericType numericType = null;
    private boolean isBoolean = false;

    public NumericType getNumericType() {
        return numericType;
    }

    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
        addChild(numericType);
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean isBoolean) {
        this.isBoolean = isBoolean;
        addChild(numericType);
    }

    public String toString() {
        if (isBoolean) {
            return StringConstant.BOOLEAN;
        } else {
            if (numericType != null) {
                return numericType.toString();
            } else {
                return StringConstant.UNDEFINED;
            }
        }
    }
}
