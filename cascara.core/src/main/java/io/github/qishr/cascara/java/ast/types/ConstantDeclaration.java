package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.UnannType;
import io.github.qishr.cascara.java.ast.classes.VariableDeclaratorList;
import io.github.qishr.cascara.java.ast.semantic.ConstantFieldOrVariableType;

/// ConstantDeclaration:
/// {ConstantModifier} UnannType VariableDeclaratorList ;
public class ConstantDeclaration extends ASTNode implements ConstantFieldOrVariableType {
    private List<ConstantModifier> constantModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private VariableDeclaratorList variableDeclaratorList = null;

    public List<ConstantModifier> getConstantModifierList() {
        return constantModifierList;
    }

    public void addConstantModifier(ConstantModifier item) {
        constantModifierList.add(item);
        addChild(item);
    }

    @Override
    public UnannType getUnannType() {
        return unannType;
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

    @Override
    public boolean isVar() {
        return false;
    }
}
