package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ForStatement:
/// BasicForStatement
/// EnhancedForStatement
public class ForStatement extends ASTNode {
    private BasicForStatement basicForStatement = null;
    private EnhancedForStatement enhancedForStatement = null;

    public BasicForStatement getBasicForStatement() {
        return basicForStatement;
    }

    public void setBasicForStatement(BasicForStatement basicForStatement) {
        this.basicForStatement = basicForStatement;
        addChild(basicForStatement);
    }

    public EnhancedForStatement getEnhancedForStatement() {
        return enhancedForStatement;
    }

    public void setEnhancedForStatement(EnhancedForStatement enhancedForStatement) {
        this.enhancedForStatement = enhancedForStatement;
        addChild(enhancedForStatement);
    }
}
