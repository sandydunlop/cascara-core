package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ClassBody:
/// { {ClassBodyDeclaration} }
public class ClassBody extends ASTNode {
    List<ClassBodyDeclaration> classBodyDeclarations = new ArrayList<>();

    public List<ClassBodyDeclaration> getClassBodyDeclarations() {
        return classBodyDeclarations;
    }

    public void setClassBodyDeclarations(List<ClassBodyDeclaration> classBodyDeclarations) {
        this.classBodyDeclarations = classBodyDeclarations;
    }

}
