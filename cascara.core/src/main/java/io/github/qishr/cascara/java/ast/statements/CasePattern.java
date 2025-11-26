package io.github.qishr.cascara.java.ast.statements;

import io.github.qishr.cascara.java.ast.ASTNode;

/// CasePattern:
/// Pattern
public class CasePattern extends ASTNode {
    private Pattern pattern = null;

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
        addChild(pattern);
    }


}
