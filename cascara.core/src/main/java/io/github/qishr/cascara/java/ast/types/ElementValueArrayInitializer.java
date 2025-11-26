package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.Pair;
import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ElementValueArrayInitializer:
/// { [ElementValueList] [,] }
public class ElementValueArrayInitializer extends ASTNode {
    private List<Pair<ElementValueList,Boolean>> elementValuePairList = new ArrayList<>();

    public List<Pair<ElementValueList, Boolean>> getElementValuePairList() {
        return elementValuePairList;
    }

    public void addElementValueListPair(Pair<ElementValueList,Boolean> item) {
        elementValuePairList.add(item);
        addChild(item.getL());
    }

    @Override
    public String toString() {
        // TODO: Implement this
        return StringConstant.UNDEFINED;
    }
}
