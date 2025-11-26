package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ElementValuePairList:
/// ElementValuePair {, ElementValuePair}
public class ElementValuePairList extends ASTNode {
    private List<ElementValuePair> elementValuePairList = new ArrayList<>();

    public List<ElementValuePair> getElementValuePairList() {
        return elementValuePairList;
    }

    public void addElementValuePair(ElementValuePair item) {
        elementValuePairList.add(item);
        addChild(item);
    }

    @Override
    public String toString() {
        if (elementValuePairList.isEmpty()) {
            return StringConstant.UNDEFINED;
        } else {
            StringBuilder sb = new StringBuilder();
            for (ElementValuePair evp : elementValuePairList) {
                if (!sb.isEmpty()) {
                    sb.append(",");
                }
                sb.append(evp.toString());
            }
            return sb.toString();
        }
    }
}
