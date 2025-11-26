package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// TypeArgumentList:
/// TypeArgument {, TypeArgument}
public class TypeArgumentList extends ASTNode {
    private List<TypeArgument> typeArgumentList = new ArrayList<>();

    public void addTypeArgument(TypeArgument item) {
        typeArgumentList.add(item);
        addChild(item);
    }

    public List<TypeArgument> getTypeArgumentList() {
        return typeArgumentList;
    }

    @Override
    public String toString() {
        if (typeArgumentList.isEmpty()) {
            return StringConstant.UNDEFINED;
        } else {
            StringBuilder sb = new StringBuilder();
            for (TypeArgument typeArgument : typeArgumentList) {
                if (!sb.isEmpty()) {
                    sb.append(",");
                }
                sb.append(typeArgument.toString());
            }
            return sb.toString();
        }
    }
}
