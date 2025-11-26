package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// WildcardBounds:
/// extends ReferenceType
/// super ReferenceType
public class WildcardBounds extends ASTNode {
    private boolean isExtends = false;
    private boolean isSuper = false;
    private ReferenceType referenceType = null;

    public boolean isExtends() {
        return isExtends;
    }

    public void setExtends(boolean isExtends) {
        this.isExtends = isExtends;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean isSuper) {
        this.isSuper = isSuper;
        addChild(referenceType);
    }
    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        addChild(referenceType);
    }

    @Override
    public String toString() {
        if (isExtends && referenceType != null) {
            return StringConstant.EXTENDS + " " + referenceType.toString();
        } else if (isSuper && referenceType != null) {
            return StringConstant.SUPER + " " + referenceType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
