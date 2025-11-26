package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.ConditionalExpression;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ElementValue:
/// ConditionalExpression
/// ElementValueArrayInitializer
/// Annotation
public class ElementValue extends ASTNode {
    private ConditionalExpression conditionalExpression = null;
    private ElementValueArrayInitializer elementValueArrayInitializer = null;
    private Annotation annotation = null;

    public ConditionalExpression getConditionalExpression() {
        return conditionalExpression;
    }

    public void setConditionalExpression(ConditionalExpression conditionalExpression) {
        this.conditionalExpression = conditionalExpression;
        addChild(conditionalExpression);
    }

    public ElementValueArrayInitializer getElementValueArrayInitializer() {
        return elementValueArrayInitializer;
    }

    public void setElementValueArrayInitializer(ElementValueArrayInitializer elementValueArrayInitializer) {
        this.elementValueArrayInitializer = elementValueArrayInitializer;
        addChild(elementValueArrayInitializer);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    @Override
    public String toString() {
        if (conditionalExpression != null) {
            return conditionalExpression.toString();
        } else if (elementValueArrayInitializer != null) {
            return elementValueArrayInitializer.toString();
        } else if (annotation != null) {
            return annotation.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
