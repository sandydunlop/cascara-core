package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// UnannInterfaceType:
/// UnannClassType
public class UnannInterfaceType extends ASTNode {
    private UnannClassType unannClassType = null;

    public UnannClassType getUnannClassType() {
        return unannClassType;
    }

    public void setUnannClassType(UnannClassType unannClassType) {
        this.unannClassType = unannClassType;
        addChild(unannClassType);
    }

    @Override
    public String toString() {
        if (unannClassType != null) {
            return unannClassType.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
