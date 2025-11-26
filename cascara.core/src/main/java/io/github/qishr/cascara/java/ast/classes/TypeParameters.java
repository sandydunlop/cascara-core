package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// TypeParameters:
/// < TypeParameterList >
public class TypeParameters extends ASTNode {
    private TypeParameterList typeParameterList = null;

    public TypeParameterList getTypeParameterList() {
        return typeParameterList;
    }

    public void setTypeParameterList(TypeParameterList typeParameterList) {
        this.typeParameterList = typeParameterList;
        addChild(typeParameterList);
    }

}
