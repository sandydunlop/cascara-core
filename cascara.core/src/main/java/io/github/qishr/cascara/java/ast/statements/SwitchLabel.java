package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// SwitchLabel:
/// case CaseConstant {, CaseConstant}
/// case null [, default]
/// case CasePattern {, CasePattern} [Guard]
/// default
public class SwitchLabel extends ASTNode {
    private List<CaseConstant> caseConstantList = new ArrayList<>();
    private boolean isNull = false;
    private boolean isDefault = false;
    private List<CasePattern> casePatternList = new ArrayList<>();

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean isNull) {
        this.isNull = isNull;
    }

    public List<CasePattern> getCasePatternList() {
        return casePatternList;
    }

    public void addCasePattern(CasePattern item) {
        casePatternList.add(item);
        addChild(item);
    }

    public Guard getGuard() {
        return guard;
    }

    public void setGuard(Guard guard) {
        this.guard = guard;
        addChild(guard);
    }

    private Guard guard = null;

    public List<CaseConstant> getCaseConstantList() {
        return caseConstantList;
    }

    public void addCaseConstant(CaseConstant item) {
        caseConstantList.add(item);
        addChild(item);
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

}
