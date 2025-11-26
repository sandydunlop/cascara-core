package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// MarkerAnnotation:
/// \@ TypeName
public class MarkerAnnotation extends Annotation {
    private TypeName typeName = null;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    @Override
    public String toString() {
        if (typeName != null) {
            return typeName.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
