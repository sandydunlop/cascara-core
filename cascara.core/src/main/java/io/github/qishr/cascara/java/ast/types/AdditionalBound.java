package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AdditionalBound:
/// & InterfaceType
public class AdditionalBound extends ASTNode {
    private InterfaceType interfaceType = null;

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType interfaceType) {
        this.interfaceType = interfaceType;
        addChild(interfaceType);
    }


}
