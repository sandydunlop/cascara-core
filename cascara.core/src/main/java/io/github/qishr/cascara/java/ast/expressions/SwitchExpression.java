package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.statements.SwitchBlock;

/// SwitchExpression:
/// switch ( Expression ) SwitchBlock
public class SwitchExpression extends ASTNode {
    private Expression expression = null;
    private SwitchBlock switchBlock = null;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public SwitchBlock getSwitchBlock() {
        return switchBlock;
    }

    public void setSwitchBlock(SwitchBlock switchBlock) {
        this.switchBlock = switchBlock;
        addChild(switchBlock);
    }

}
