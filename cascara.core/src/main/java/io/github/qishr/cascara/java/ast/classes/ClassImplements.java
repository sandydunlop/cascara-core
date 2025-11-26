package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ClassImplements:
/// implements InterfaceTypeList
public class ClassImplements extends ASTNode {
    private InterfaceTypeList interfaceTypeList = null;

    public InterfaceTypeList getInterfaceTypeList() {
        return interfaceTypeList;
    }

    public void setInterfaceTypeList(InterfaceTypeList interfaceTypeList) {
        this.interfaceTypeList = interfaceTypeList;
        addChild(interfaceTypeList);
    }
}
