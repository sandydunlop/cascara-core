package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// VariableDeclaratorList:
/// VariableDeclarator {, VariableDeclarator}
public class VariableDeclaratorList extends ASTNode {
    private List<VariableDeclarator> variableDeclaratorList = new ArrayList<>();

    public List<VariableDeclarator> getVariableDeclaratorList() {
        return variableDeclaratorList;
    }

    public void addVariableDeclarator(VariableDeclarator item) {
        variableDeclaratorList.add(item);
        addChild(item);
    }
}
