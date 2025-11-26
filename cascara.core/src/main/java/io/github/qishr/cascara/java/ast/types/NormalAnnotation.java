package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// NormalAnnotation:
/// @ TypeName ( [ElementValuePairList] )
public class NormalAnnotation extends Annotation {
    private TypeName typeName = null;
    private ElementValuePairList elementValuePairList = null;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    public ElementValuePairList getElementValuePairList() {
        return elementValuePairList;
    }

    public void setElementValuePairList(ElementValuePairList elementValuePairList) {
        this.elementValuePairList = elementValuePairList;
        addChild(elementValuePairList);
    }

    @Override
    public String toString() {
        if (typeName != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("@");
            sb.append(typeName.toString());
            sb.append("(");
            if (elementValuePairList != null) {
                sb.append(elementValuePairList.toString());
            }
            sb.append(")");
            return sb.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
