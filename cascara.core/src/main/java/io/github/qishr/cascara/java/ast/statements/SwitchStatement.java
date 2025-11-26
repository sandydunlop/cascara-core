package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// SwitchStatement:
/// switch ( Expression ) SwitchBlock
public class SwitchStatement extends ASTNode implements ScopingConstruct {
    private Expression expression = null;
    private SwitchBlock switchBlock = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

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
