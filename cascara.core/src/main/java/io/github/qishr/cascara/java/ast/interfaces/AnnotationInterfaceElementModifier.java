package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;

/// AnnotationInterfaceElementModifier:
/// (one of)
/// Annotation public
/// abstract
public class AnnotationInterfaceElementModifier extends ASTNode {
    private Annotation annotation = null;
    private boolean isPublic = false;
    private boolean isPrivate = false;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

}
