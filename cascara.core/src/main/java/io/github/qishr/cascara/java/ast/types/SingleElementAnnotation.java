package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// SingleElementAnnotation:
/// \@ TypeName ( ElementValue )
public class SingleElementAnnotation extends ASTNode {
    private TypeName typeName = null;
    private ElementValue elementValue = null;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    public ElementValue getElementValue() {
        return elementValue;
    }

    public void setElementValue(ElementValue elementValue) {
        this.elementValue = elementValue;
        addChild(elementValue);
    }

    @Override
    public String toString() {
        if (typeName != null && elementValue != null) {
            return "@" + typeName.toString() + "(" + elementValue.toString() + ")";
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
