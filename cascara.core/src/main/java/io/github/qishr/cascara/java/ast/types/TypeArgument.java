package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// TypeArgument:
/// ReferenceType
/// Wildcard
public class TypeArgument extends ASTNode {
    private ReferenceType referenceType = null;
    private Wildcard wildcard = null;

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        addChild(referenceType);
    }

    public Wildcard getWildcard() {
        return wildcard;
    }

    public void setWildcard(Wildcard wildcard) {
        this.wildcard = wildcard;
        addChild(wildcard);
    }

    @Override
    public String toString() {
        if (referenceType != null) {
            return referenceType.toString();
        } else if (wildcard != null) {
            return wildcard.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
