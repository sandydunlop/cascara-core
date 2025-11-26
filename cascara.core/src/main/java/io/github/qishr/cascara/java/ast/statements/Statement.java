package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Statement:
/// StatementWithoutTrailingSubstatement
/// LabeledStatement
/// IfThenStatement
/// IfThenElseStatement
/// WhileStatement
/// ForStatement
public class Statement extends BlockStatement {
    private StatementWithoutTrailingSubstatement statementWithoutTrailingSubstatement = null;
    private LabeledStatement labeledStatement = null;
    private IfThenStatement ifThenStatement = null;
    private IfThenElseStatement ifThenElseStatement = null;
    private WhileStatement whileStatement = null;
    private ForStatement forStatement = null;

    public StatementWithoutTrailingSubstatement getStatementWithoutTrailingSubstatement() {
        return statementWithoutTrailingSubstatement;
    }

    public void setStatementWithoutTrailingSubstatement(
            StatementWithoutTrailingSubstatement statementWithoutTrailingSubstatement) {
        this.statementWithoutTrailingSubstatement = statementWithoutTrailingSubstatement;
        addChild(statementWithoutTrailingSubstatement);
    }

    public LabeledStatement getLabeledStatement() {
        return labeledStatement;
    }

    public void setLabeledStatement(LabeledStatement labeledStatement) {
        this.labeledStatement = labeledStatement;
        addChild(labeledStatement);
    }

    public IfThenStatement getIfThenStatement() {
        return ifThenStatement;
    }

    public void setIfThenStatement(IfThenStatement ifThenStatement) {
        this.ifThenStatement = ifThenStatement;
        addChild(ifThenStatement);
    }

    public IfThenElseStatement getIfThenElseStatement() {
        return ifThenElseStatement;
    }

    public void setIfThenElseStatement(IfThenElseStatement ifThenElseStatement) {
        this.ifThenElseStatement = ifThenElseStatement;
        addChild(ifThenElseStatement);
    }

    public WhileStatement getWhileStatement() {
        return whileStatement;
    }

    public void setWhileStatement(WhileStatement whileStatement) {
        this.whileStatement = whileStatement;
        addChild(whileStatement);
    }

    public ForStatement getForStatement() {
        return forStatement;
    }

    public void setForStatement(ForStatement forStatement) {
        this.forStatement = forStatement;
        addChild(forStatement);
    }

}
