package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.InterfaceType;

/// InterfaceTypeList:
/// InterfaceType {, InterfaceType}
public class InterfaceTypeList extends ASTNode {
    private List<InterfaceType> interfaceTypeList = new ArrayList<>();

    public void addInterfaceType(InterfaceType item) {
        interfaceTypeList.add(item);
        addChild(item);
    }

    public List<InterfaceType> getInterfaceTypeList() {
        return interfaceTypeList;
    }
}
