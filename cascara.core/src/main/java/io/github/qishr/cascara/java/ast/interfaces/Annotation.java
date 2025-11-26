package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.MarkerAnnotation;
import io.github.qishr.cascara.java.ast.types.NormalAnnotation;
import io.github.qishr.cascara.java.ast.types.SingleElementAnnotation;

/// Annotation:
/// NormalAnnotation
/// MarkerAnnotation
/// SingleElementAnnotation
public class Annotation extends ASTNode {
    private NormalAnnotation normalAnnotation = null;
    private MarkerAnnotation markerAnnotation = null;
    private SingleElementAnnotation singleElementAnnotation = null;

    public NormalAnnotation getNormalAnnotation() {
        return normalAnnotation;
    }

    public void setNormalAnnotation(NormalAnnotation normalAnnotation) {
        this.normalAnnotation = normalAnnotation;
        addChild(normalAnnotation);
    }

    public MarkerAnnotation getMarkerAnnotation() {
        return markerAnnotation;
    }

    public void setMarkerAnnotation(MarkerAnnotation markerAnnotation) {
        this.markerAnnotation = markerAnnotation;
        addChild(markerAnnotation);
    }

    public SingleElementAnnotation getSingleElementAnnotation() {
        return singleElementAnnotation;
    }

    public void setSingleElementAnnotation(SingleElementAnnotation singleElementAnnotation) {
        this.singleElementAnnotation = singleElementAnnotation;
        addChild(singleElementAnnotation);
    }

    @Override
    public String toString() {
        if (normalAnnotation != null) {
            return normalAnnotation.toString();
        } else if (markerAnnotation != null) {
            return markerAnnotation.toString();
        } else if (singleElementAnnotation != null) {
            return singleElementAnnotation.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }

    public TypeName getTypeName() {
        if (normalAnnotation != null) {
            return normalAnnotation.getTypeName();
        } else if (markerAnnotation != null) {
            return markerAnnotation.getTypeName();
        } else if (singleElementAnnotation != null) {
            return singleElementAnnotation.getTypeName();
        } else {
            return null;
        }
    }
}
