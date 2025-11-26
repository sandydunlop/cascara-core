package io.github.qishr.cascara.java.ast.classes;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;

/// EnumBody:
/// { [EnumConstantList] [,] [EnumBodyDeclarations] }
public class EnumBody extends ASTNode {
    private EnumConstantList enumConstantList = null;
    private boolean hasComma = false;
    private EnumBodyDeclarations enumBodyDeclarations = null;

    public EnumConstantList getEnumConstantList() {
        return enumConstantList;
    }

    public void setEnumConstantList(EnumConstantList enumConstantList) {
        this.enumConstantList = enumConstantList;
        addChild(enumConstantList);
    }

    public boolean isHasComma() {
        return hasComma;
    }

    public void setHasComma(boolean hasComma) {
        this.hasComma = hasComma;
        addChild(enumBodyDeclarations);
    }

    public EnumBodyDeclarations getEnumBodyDeclarations() {
        return enumBodyDeclarations;
    }

    public void setEnumBodyDeclarations(EnumBodyDeclarations enumBodyDeclarations) {
        this.enumBodyDeclarations = enumBodyDeclarations;
        addChild(enumBodyDeclarations);
    }


}
