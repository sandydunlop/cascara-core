package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// UnannType:
/// UnannPrimitiveType
/// UnannReferenceType
public class UnannType extends ASTNode {
    private UnannPrimitiveType unannPrimitiveType = null;
    private UnannReferenceType unannReferenceType = null;

    public UnannPrimitiveType getUnannPrimitiveType() {
        return unannPrimitiveType;
    }

    public void setUnannPrimitiveType(UnannPrimitiveType unannPrimitiveType) {
        this.unannPrimitiveType = unannPrimitiveType;
        addChild(unannPrimitiveType);
    }

    public UnannReferenceType getUnannReferenceType() {
        return unannReferenceType;
    }

    public void setUnannReferenceType(UnannReferenceType unannReferenceType) {
        this.unannReferenceType = unannReferenceType;
        addChild(unannReferenceType);
    }

    @Override
    public String toString() {
        if (unannPrimitiveType != null) {
            return unannPrimitiveType.toString();
        } else if (unannReferenceType != null) {
            return unannReferenceType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
