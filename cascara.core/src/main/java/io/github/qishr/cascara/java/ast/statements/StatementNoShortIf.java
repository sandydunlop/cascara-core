package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// StatementNoShortIf:
/// StatementWithoutTrailingSubstatement
/// LabeledStatementNoShortIf
/// IfThenElseStatementNoShortIf
/// WhileStatementNoShortIf
/// ForStatementNoShortIf
public class StatementNoShortIf extends ASTNode {
    private StatementWithoutTrailingSubstatement statementWithoutTrailingSubstatement = null;
    private LabeledStatementNoShortIf labeledStatementNoShortIf = null;
    private IfThenElseStatementNoShortIf ifThenElseStatementNoShortIf = null;
    private WhileStatementNoShortIf whileStatementNoShortIf = null;
    private ForStatementNoShortIf forStatementNoShortIf = null;

    public StatementWithoutTrailingSubstatement getStatementWithoutTrailingSubstatement() {
        return statementWithoutTrailingSubstatement;
    }

    public void setStatementWithoutTrailingSubstatement(
            StatementWithoutTrailingSubstatement statementWithoutTrailingSubstatement) {
        this.statementWithoutTrailingSubstatement = statementWithoutTrailingSubstatement;
        addChild(statementWithoutTrailingSubstatement);
    }

    public LabeledStatementNoShortIf getLabeledStatementNoShortIf() {
        return labeledStatementNoShortIf;
    }

    public void setLabeledStatementNoShortIf(LabeledStatementNoShortIf labeledStatementNoShortIf) {
        this.labeledStatementNoShortIf = labeledStatementNoShortIf;
        addChild(labeledStatementNoShortIf);
    }

    public IfThenElseStatementNoShortIf getIfThenElseStatementNoShortIf() {
        return ifThenElseStatementNoShortIf;
    }

    public void setIfThenElseStatementNoShortIf(IfThenElseStatementNoShortIf ifThenElseStatementNoShortIf) {
        this.ifThenElseStatementNoShortIf = ifThenElseStatementNoShortIf;
        addChild(ifThenElseStatementNoShortIf);
    }

    public WhileStatementNoShortIf getWhileStatementNoShortIf() {
        return whileStatementNoShortIf;
    }

    public void setWhileStatementNoShortIf(WhileStatementNoShortIf whileStatementNoShortIf) {
        this.whileStatementNoShortIf = whileStatementNoShortIf;
        addChild(whileStatementNoShortIf);
    }

    public ForStatementNoShortIf getForStatementNoShortIf() {
        return forStatementNoShortIf;
    }

    public void setForStatementNoShortIf(ForStatementNoShortIf forStatementNoShortIf) {
        this.forStatementNoShortIf = forStatementNoShortIf;
        addChild(forStatementNoShortIf);
    }

}
