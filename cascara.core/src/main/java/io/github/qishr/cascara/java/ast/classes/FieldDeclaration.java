package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.ConstantFieldOrVariableType;
import io.github.qishr.cascara.java.ast.semantic.Declaration;

/// FieldDeclaration:
/// {FieldModifier} UnannType VariableDeclaratorList ;
public class FieldDeclaration extends ASTNode implements ConstantFieldOrVariableType {
    private List<FieldModifier> fieldModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private VariableDeclaratorList variableDeclaratorList = null;

    public List<FieldModifier> getFieldModifierList() {
        return fieldModifierList;
    }

    public List<Declaration> getDeclarations() {
        List<Declaration> declarations = new ArrayList<>();
        if (variableDeclaratorList != null) {
            for (VariableDeclarator declarator : variableDeclaratorList.getVariableDeclaratorList()) {
                declarations.add(declarator);
            }
        }
        return declarations;
    }

    public void addFieldModifier(FieldModifier item) {
        fieldModifierList.add(item);
        addChild(item);
    }

    @Override
    public UnannType getUnannType() {
        return unannType;
    }

    @Override
    public boolean isVar() {
        return false;
    }

    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }

    public VariableDeclaratorList getVariableDeclaratorList() {
        return variableDeclaratorList;
    }

    public void setVariableDeclaratorList(VariableDeclaratorList variableDeclaratorList) {
        this.variableDeclaratorList = variableDeclaratorList;
        addChild(variableDeclaratorList);
    }
}
