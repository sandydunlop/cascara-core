package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// TypeBound:
/// extends TypeVariable
/// extends ClassOrInterfaceType {AdditionalBound}
public class TypeBound extends ASTNode {
    private TypeVariable typeVariable = null;
    private ClassOrInterfaceType classOrInterfaceType = null;
    private List<AdditionalBound> additionalBoundList = new ArrayList<>();

    public TypeVariable getTypeVariable() {
        return typeVariable;
    }

    public void setTypeVariable(TypeVariable typeVariable) {
        this.typeVariable = typeVariable;
        addChild(typeVariable);
    }

    public ClassOrInterfaceType getClassOrInterfaceType() {
        return classOrInterfaceType;
    }

    public void setClassOrInterfaceType(ClassOrInterfaceType classOrInterfaceType) {
        this.classOrInterfaceType = classOrInterfaceType;
        addChild(classOrInterfaceType);
    }

    public List<AdditionalBound> getAdditionalBoundList() {
        return additionalBoundList;
    }

    public void addAdditionalBound(AdditionalBound item) {
        additionalBoundList.add(item);
        addChild(item);
    }
}
