package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.TypeArguments;

/// TypeArgumentsOrDiamond:
/// TypeArguments
/// <>
public class TypeArgumentsOrDiamond extends ASTNode {
    private TypeArguments typeArguments = null;
    private boolean diamond = false;

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
    }

    public boolean isDiamond() {
        return diamond;
    }

    public void setDiamond(boolean diamond) {
        this.diamond = diamond;
    }


}
