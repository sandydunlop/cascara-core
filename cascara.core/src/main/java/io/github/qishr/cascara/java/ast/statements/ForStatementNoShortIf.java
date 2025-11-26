package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ForStatementNoShortIf:
/// BasicForStatementNoShortIf
/// EnhancedForStatementNoShortIf
public class ForStatementNoShortIf extends ASTNode {
    private BasicForStatementNoShortIf basicForStatementNoShortIf = null;
    private EnhancedForStatementNoShortIf enhancedForStatementNoShortIf = null;

    public BasicForStatementNoShortIf getBasicForStatementNoShortIf() {
        return basicForStatementNoShortIf;
    }

    public void setBasicForStatementNoShortIf(BasicForStatementNoShortIf basicForStatementNoShortIf) {
        this.basicForStatementNoShortIf = basicForStatementNoShortIf;
        addChild(basicForStatementNoShortIf);
    }

    public EnhancedForStatementNoShortIf getEnhancedForStatementNoShortIf() {
        return enhancedForStatementNoShortIf;
    }

    public void setEnhancedForStatementNoShortIf(EnhancedForStatementNoShortIf enhancedForStatementNoShortIf) {
        this.enhancedForStatementNoShortIf = enhancedForStatementNoShortIf;
        addChild(enhancedForStatementNoShortIf);
    }
}
