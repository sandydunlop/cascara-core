package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// StatementWithoutTrailingSubstatement:
/// Block
/// EmptyStatement
/// ExpressionStatement
/// AssertStatement
/// SwitchStatement
/// DoStatement
/// BreakStatement
/// ContinueStatement
/// ReturnStatement
/// SynchronizedStatement
/// ThrowStatement
/// TryStatement
/// YieldStatement
public class StatementWithoutTrailingSubstatement extends ASTNode {
    private Block block = null;
    private EmptyStatement emptyStatement = null;
    private ExpressionStatement expressionStatement = null;
    private AssertStatement assertStatement = null;
    private SwitchStatement switchStatement = null;
    private DoStatement doStatement = null;
    private BreakStatement breakStatement = null;
    private ContinueStatement continueStatement = null;
    private ReturnStatement returnStatement = null;
    private SynchronizedStatement synchronizedStatement = null;
    private ThrowStatement throwStatement = null;
    private TryStatement tryStatement = null;
    private YieldStatement yieldStatement = null;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
        addChild(block);
    }

    public EmptyStatement getEmptyStatement() {
        return emptyStatement;
    }

    public void setEmptyStatement(EmptyStatement emptyStatement) {
        this.emptyStatement = emptyStatement;
        addChild(emptyStatement);
    }

    public ExpressionStatement getExpressionStatement() {
        return expressionStatement;
    }

    public void setExpressionStatement(ExpressionStatement expressionStatement) {
        this.expressionStatement = expressionStatement;
        addChild(expressionStatement);
    }

    public AssertStatement getAssertStatement() {
        return assertStatement;
    }

    public void setAssertStatement(AssertStatement assertStatement) {
        this.assertStatement = assertStatement;
        addChild(assertStatement);
    }

    public SwitchStatement getSwitchStatement() {
        return switchStatement;
    }

    public void setSwitchStatement(SwitchStatement switchStatement) {
        this.switchStatement = switchStatement;
        addChild(switchStatement);
    }

    public DoStatement getDoStatement() {
        return doStatement;
    }

    public void setDoStatement(DoStatement doStatement) {
        this.doStatement = doStatement;
        addChild(doStatement);
    }

    public BreakStatement getBreakStatement() {
        return breakStatement;
    }

    public void setBreakStatement(BreakStatement breakStatement) {
        this.breakStatement = breakStatement;
        addChild(breakStatement);
    }

    public ContinueStatement getContinueStatement() {
        return continueStatement;
    }

    public void setContinueStatement(ContinueStatement continueStatement) {
        this.continueStatement = continueStatement;
        addChild(continueStatement);
    }

    public ReturnStatement getReturnStatement() {
        return returnStatement;
    }

    public void setReturnStatement(ReturnStatement returnStatement) {
        this.returnStatement = returnStatement;
        addChild(returnStatement);
    }

    public SynchronizedStatement getSynchronizedStatement() {
        return synchronizedStatement;
    }

    public void setSynchronizedStatement(SynchronizedStatement synchronizedStatement) {
        this.synchronizedStatement = synchronizedStatement;
        addChild(synchronizedStatement);
    }

    public ThrowStatement getThrowStatement() {
        return throwStatement;
    }

    public void setThrowStatement(ThrowStatement throwStatement) {
        this.throwStatement = throwStatement;
        addChild(throwStatement);
    }

    public TryStatement getTryStatement() {
        return tryStatement;
    }

    public void setTryStatement(TryStatement tryStatement) {
        this.tryStatement = tryStatement;
        addChild(tryStatement);
    }

    public YieldStatement getYieldStatement() {
        return yieldStatement;
    }

    public void setYieldStatement(YieldStatement yieldStatement) {
        this.yieldStatement = yieldStatement;
        addChild(yieldStatement);
    }
}
