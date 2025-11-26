package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;

/// VariableModifier:
/// Annotation
/// final
public class VariableModifier extends ASTNode {
    private Annotation annotation = null;
    private boolean isFinal = false;

    public VariableModifier() {
    }

    public VariableModifier(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public boolean isFinal() {
        return isFinal;
    }

    public void setFinal(boolean isFinal) {
        this.isFinal = isFinal;
    }


}
