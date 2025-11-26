package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// FormalParameterList:
/// FormalParameter {, FormalParameter}
public class FormalParameterList extends ASTNode {
    private List<FormalParameter> formalParameterList = new ArrayList<>();

    public void addFormalParameter(FormalParameter item) {
        formalParameterList.add(item);
        addChild(item);
    }

    public List<FormalParameter> getFormalParameterList() {
        return formalParameterList;
    }
}


