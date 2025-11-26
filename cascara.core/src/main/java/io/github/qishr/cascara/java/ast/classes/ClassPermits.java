package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;

/// ClassPermits:
/// permits TypeName {, TypeName}
public class ClassPermits extends ASTNode {
    private List<TypeName> typeNameList = new ArrayList<>();

    public List<TypeName> getTypeNameList() {
        return typeNameList;
    }

    public void addTypeName(TypeName item) {
        typeNameList.add(item);
        addChild(item);
    }
}
