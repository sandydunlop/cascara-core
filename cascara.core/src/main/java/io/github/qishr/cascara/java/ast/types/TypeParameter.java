package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;

/// TypeParameter:
/// {TypeParameterModifier} TypeIdentifier [TypeBound]
public class TypeParameter extends ASTNode {
    private List<TypeParameterModifier> typeParameterModifierList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private TypeBound typeBound = null;

    public List<TypeParameterModifier> getTypeParameterModifierList() {
        return typeParameterModifierList;
    }

    public void addTypeParameterModifier(TypeParameterModifier item) {
        typeParameterModifierList.add(item);
        addChild(item);
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

    public TypeBound getTypeBound() {
        return typeBound;
    }

    public void setTypeBound(TypeBound typeBound) {
        this.typeBound = typeBound;
        addChild(typeBound);
    }
}
