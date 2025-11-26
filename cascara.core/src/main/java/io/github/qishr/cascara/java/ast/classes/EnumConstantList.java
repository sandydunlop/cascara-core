package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// EnumConstantList:
/// EnumConstant {, EnumConstant}
public class EnumConstantList extends ASTNode {
    private List<EnumConstant> enumConstantList = new ArrayList<>();

    public List<EnumConstant> getEnumConstantList() {
        return enumConstantList;
    }

    public void addEnumConstant(EnumConstant item) {
        enumConstantList.add(item);
        addChild(item);
    }
}
