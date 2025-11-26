package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// RecordHeader:
/// ( [RecordComponentList] )
public class RecordHeader extends ASTNode {
    private RecordComponentList recordComponentList = null;

    public RecordComponentList getRecordComponentList() {
        return recordComponentList;
    }

    public void setRecordComponentList(RecordComponentList recordComponentList) {
        this.recordComponentList = recordComponentList;
        addChild(recordComponentList);
    }
}
