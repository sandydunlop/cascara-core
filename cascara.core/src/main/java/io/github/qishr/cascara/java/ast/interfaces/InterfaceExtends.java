package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.InterfaceTypeList;

/// InterfaceExtends:
/// extends InterfaceTypeList
public class InterfaceExtends extends ASTNode {
    private InterfaceTypeList interfaceTypeList = null;

    public InterfaceTypeList getInterfaceTypeList() {
        return interfaceTypeList;
    }

    public void setInterfaceTypeList(InterfaceTypeList interfaceTypeList) {
        this.interfaceTypeList = interfaceTypeList;
        addChild(interfaceTypeList);
    }
}
