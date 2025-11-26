package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.Pair;
import io.github.qishr.cascara.java.ast.ASTNode;

/// SwitchBlock:
/// { SwitchRule {SwitchRule} }
/// { {SwitchBlockStatementGroup} {SwitchLabel :} }
public class SwitchBlock extends ASTNode {
    private List<SwitchRule> switchRuleList = new ArrayList<>();
    private List<Pair<List<SwitchBlockStatementGroup>,List<SwitchLabel>>> switchBlockStatementGroupList = new ArrayList<>();

    public List<SwitchRule> getSwitchRuleList() {
        return switchRuleList;
    }

    public void addSwitchRule(SwitchRule item) {
        switchRuleList.add(item);
        addChild(item);
    }

    public List<Pair<List<SwitchBlockStatementGroup>, List<SwitchLabel>>> getSwitchBlockStatementGroupList() {
        return switchBlockStatementGroupList;
    }

    // TODO: Caller needs to call addChild
    public void addStatementGroupPair(Pair<List<SwitchBlockStatementGroup>,List<SwitchLabel>> item) {
        switchBlockStatementGroupList.add(item);
    }
}
