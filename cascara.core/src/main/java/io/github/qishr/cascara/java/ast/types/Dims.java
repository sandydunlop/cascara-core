package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;

/// Dims:
/// {Annotation} [ ] {{Annotation} [ ]}
public class Dims extends ASTNode {
    private List<List<Annotation>> annotationList = new ArrayList<>();

    public void addAnnotationList(List<Annotation> item) {
        annotationList.add(item);
        for (Annotation a : item) {
            addChild(a);
        }
    }

    public List<List<Annotation>> getAnnotationList() {
        return annotationList;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (List<Annotation> dimension : annotationList) {
            for (Annotation annotation : dimension) {
                sb.append(annotation.toString());
            }
            sb.append("[]");
        }
        return sb.toString();
    }
}
