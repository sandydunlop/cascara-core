package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RecordBody:
/// { {RecordBodyDeclaration} }
public class RecordBody extends ASTNode {
    private List<RecordBodyDeclaration> recordBodyDeclarationList = new ArrayList<>();

    public List<RecordBodyDeclaration> getRecordBodyDeclarationList() {
        return recordBodyDeclarationList;
    }

    public void addRecordBodyDeclaration(RecordBodyDeclaration item) {
        recordBodyDeclarationList.add(item);
        addChild(item);
    }
}
