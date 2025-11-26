package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// InterfaceBody:
/// { {InterfaceMemberDeclaration} }
public class InterfaceBody extends ASTNode {
    private List<InterfaceMemberDeclaration> interfaceMemberDeclarationList = new ArrayList<>();

    public List<InterfaceMemberDeclaration> getInterfaceMemberDeclarationList() {
        return interfaceMemberDeclarationList;
    }

    public void addInterfaceMemberDeclaration(InterfaceMemberDeclaration item) {
        interfaceMemberDeclarationList.add(item);
        addChild(item);
    }
}
