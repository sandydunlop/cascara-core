package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;

/// ConstructorModifier:
/// (one of)
/// Annotation public protected private
public class ConstructorModifier extends ASTNode {
    private Annotation annotation = null;
    private boolean isPublic = false;
    private boolean isProtected = false;
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

    public boolean isProtected() {
        return isProtected;
    }

    public void setProtected(boolean isProtected) {
        this.isProtected = isProtected;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean isPrivate) {
        this.isPrivate = isPrivate;
    }
}
