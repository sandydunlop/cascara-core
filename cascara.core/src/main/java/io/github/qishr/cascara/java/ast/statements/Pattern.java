package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Pattern:
/// TypePattern
/// RecordPattern
public class Pattern extends ASTNode {
    private TypePattern typePattern = null;
    private RecordPattern recordPattern = null;

    public TypePattern getTypePattern() {
        return typePattern;
    }

    public void setTypePattern(TypePattern typePattern) {
        this.typePattern = typePattern;
        addChild(typePattern);
    }

    public RecordPattern getRecordPattern() {
        return recordPattern;
    }

    public void setRecordPattern(RecordPattern recordPattern) {
        this.recordPattern = recordPattern;
        addChild(recordPattern);
    }

}
