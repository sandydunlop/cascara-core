package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.TypeParameter;

/// TypeParameterList:
/// TypeParameter {, TypeParameter}
public class TypeParameterList extends ASTNode {
    private List<TypeParameter> typeParameterList = new ArrayList<>();

    public List<TypeParameter> getTypeParameterList() {
        return typeParameterList;
    }

    public void addTypeParameter(TypeParameter item) {
        typeParameterList.add(item);
        addChild(item);
    }
}
