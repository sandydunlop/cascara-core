package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ElementValueList:
/// ElementValue {, ElementValue}
public class ElementValueList extends ASTNode {
    private List<ElementValue> elementValueList = new ArrayList<>();

    public List<ElementValue> getElementValueList() {
        return elementValueList;
    }

    public void addElementValue(ElementValue item) {
        elementValueList.add(item);
        addChild(item);
    }
}
