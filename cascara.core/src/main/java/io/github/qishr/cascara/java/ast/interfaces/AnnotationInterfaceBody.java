package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AnnotationInterfaceBody:
/// { {AnnotationInterfaceMemberDeclaration} }
public class AnnotationInterfaceBody extends ASTNode {
    private List<AnnotationInterfaceMemberDeclaration> annotationInterfaceMemberDeclarationList = new ArrayList<>();

    public void addAnnotationInterfaceMemberDeclaration(AnnotationInterfaceMemberDeclaration item) {
        annotationInterfaceMemberDeclarationList.add(item);
        addChild(item);
    }

    public List<AnnotationInterfaceMemberDeclaration> getAnnotationInterfaceMemberDeclarationList() {
        return annotationInterfaceMemberDeclarationList;
    }

}
