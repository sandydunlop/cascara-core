package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;

/// Wildcard:
/// {Annotation} ? [WildcardBounds]
public class Wildcard extends ASTNode {
    private List<Annotation> annotationList = new ArrayList<>();
    private WildcardBounds wildcardBounds = null;

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public WildcardBounds getWildcardBounds() {
        return wildcardBounds;
    }

    public void setWildcardBounds(WildcardBounds wildcardBounds) {
        this.wildcardBounds = wildcardBounds;
        addChild(wildcardBounds);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Annotation annotation : annotationList) {
            sb.append(annotation.toString());
            sb.append(" ");
        }
        sb.append("?");
        if (wildcardBounds != null) {
            sb.append(" ");
            sb.append(wildcardBounds.toString());
        }
        return sb.toString();
    }
}
