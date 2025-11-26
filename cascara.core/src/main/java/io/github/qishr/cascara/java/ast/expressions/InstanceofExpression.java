package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.statements.Pattern;
import io.github.qishr.cascara.java.ast.types.ReferenceType;

/// InstanceofExpression:
/// RelationalExpression instanceof ReferenceType
/// RelationalExpression instanceof Pattern
public class InstanceofExpression extends ASTNode {
    private RelationalExpression relationalExpression = null;
    private ReferenceType referenceType = null;
    private Pattern pattern = null;

    public RelationalExpression getRelationalExpression() {
        return relationalExpression;
    }

    public void setRelationalExpression(RelationalExpression relationalExpression) {
        this.relationalExpression = relationalExpression;
        addChild(relationalExpression);
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        addChild(referenceType);
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
        addChild(pattern);
    }


}
