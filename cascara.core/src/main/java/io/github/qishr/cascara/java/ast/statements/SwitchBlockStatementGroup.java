package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// SwitchBlockStatementGroup:
/// SwitchLabel : {SwitchLabel :} BlockStatements
public class SwitchBlockStatementGroup extends ASTNode {
    private List<SwitchLabel> switchLabelList = new ArrayList<>();
    private BlockStatements blockStatements = null;

    public List<SwitchLabel> getSwitchLabelList() {
        return switchLabelList;
    }

    public void addSwitchLabel(SwitchLabel item) {
        switchLabelList.add(item);
        addChild(item);
    }

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }

    public void setBlockStatements(BlockStatements blockStatements) {
        this.blockStatements = blockStatements;
        addChild(blockStatements);
    }
}
