package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RecordComponentList:
/// RecordComponent {, RecordComponent}
public class RecordComponentList extends ASTNode {
    private List<RecordComponent> recordComponentList = new ArrayList<>();

    public List<RecordComponent> getRecordComponentList() {
        return recordComponentList;
    }

    public void addRecordComponent(RecordComponent item) {
        recordComponentList.add(item);
        addChild(item);
    }
}
