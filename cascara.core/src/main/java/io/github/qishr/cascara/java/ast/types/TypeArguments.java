package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// TypeArguments:
/// < TypeArgumentList >
public class TypeArguments extends ASTNode {
    private TypeArgumentList typeArgumentList = null;

    public TypeArgumentList getTypeArgumentList() {
        return typeArgumentList;
    }

    public void setTypeArgumentList(TypeArgumentList typeArgumentList) {
        this.typeArgumentList = typeArgumentList;
        addChild(typeArgumentList);
    }

    @Override
    public String toString() {
        if (typeArgumentList != null) {
            return "<" + typeArgumentList.toString() + ">";
        } else {
            return "<" + StringConstant.UNDEFINED + ">";
        }
    }
}
