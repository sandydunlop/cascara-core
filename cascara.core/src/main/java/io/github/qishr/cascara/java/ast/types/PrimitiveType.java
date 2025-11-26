package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;

/// PrimitiveType:
/// {Annotation} NumericType
/// {Annotation} boolean
public class PrimitiveType extends ASTNode {
    private Annotation annotation = null;
    private NumericType numericType = null;
    private boolean isBooleanKeyword = false;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public NumericType getNumericType() {
        return numericType;
    }

    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
        addChild(numericType);
    }

    public boolean isBooleanKeyword() {
        return isBooleanKeyword;
    }

    public void setBooleanKeyword(boolean booleanValue) {
        this.isBooleanKeyword = booleanValue;
    }

}
