package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;

/// SwitchRule:
/// SwitchLabel -> Expression ;
/// SwitchLabel -> Block
/// SwitchLabel -> ThrowStatement
public class SwitchRule extends ASTNode {
    private SwitchLabel switchLabel = null;
    private Expression expression = null;
    private Block block = null;
    private ThrowStatement throwStatement = null;

    public SwitchLabel getSwitchLabel() {
        return switchLabel;
    }

    public void setSwitchLabel(SwitchLabel switchLabel) {
        this.switchLabel = switchLabel;
        addChild(switchLabel);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
        addChild(block);
    }

    public ThrowStatement getThrowStatement() {
        return throwStatement;
    }

    public void setThrowStatement(ThrowStatement throwStatement) {
        this.throwStatement = throwStatement;
        addChild(throwStatement);
    }

}
