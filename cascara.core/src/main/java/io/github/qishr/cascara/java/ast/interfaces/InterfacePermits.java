package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;

/// InterfacePermits:
/// permits TypeName {, TypeName}
public class InterfacePermits extends ASTNode {
    private List<TypeName> typeNameList = new ArrayList<>();

    public List<TypeName> getTypeNameList() {
        return typeNameList;
    }

    public void addTypeName(TypeName item) {
        typeNameList.add(item);
        addChild(item);
    }
}
