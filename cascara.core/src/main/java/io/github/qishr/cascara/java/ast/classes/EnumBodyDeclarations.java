package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;

/// EnumBodyDeclarations:
/// ; {ClassBodyDeclaration}
public class EnumBodyDeclarations extends ASTNode {
    private List<ClassBodyDeclaration> classBodyDeclarationList = new ArrayList<>();

    public List<ClassBodyDeclaration> getClassBodyDeclarationList() {
        return classBodyDeclarationList;
    }

    public void addClassBodyDeclaration(ClassBodyDeclaration item) {
        classBodyDeclarationList.add(item);
        addChild(item);
    }
}
