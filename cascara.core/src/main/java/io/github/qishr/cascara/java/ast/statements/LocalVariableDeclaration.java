package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.UnannType;
import io.github.qishr.cascara.java.ast.classes.VariableDeclaratorList;
import io.github.qishr.cascara.java.ast.classes.VariableModifier;
import io.github.qishr.cascara.java.ast.semantic.ConstantFieldOrVariableType;

/// LocalVariableDeclaration:
/// {VariableModifier} LocalVariableType VariableDeclaratorList
public class LocalVariableDeclaration extends ASTNode implements ConstantFieldOrVariableType {
    private List<VariableModifier> variableModifierList = new ArrayList<>();
    private LocalVariableType localVariableType = null;
    private VariableDeclaratorList variableDeclaratorList = null;

    public void addVariableModifier(VariableModifier item) {
        variableModifierList.add(item);
        addChild(item);
    }

    public List<VariableModifier> getVariableModifierList() {
        return variableModifierList;
    }

    public LocalVariableType getLocalVariableType() {
        return localVariableType;
    }

    public void setLocalVariableType(LocalVariableType localVariableType) {
        this.localVariableType = localVariableType;
        addChild(localVariableType);
    }

    public VariableDeclaratorList getVariableDeclaratorList() {
        return variableDeclaratorList;
    }

    public void setVariableDeclaratorList(VariableDeclaratorList variableDeclaratorList) {
        this.variableDeclaratorList = variableDeclaratorList;
        addChild(variableDeclaratorList);
    }

    @Override
    public UnannType getUnannType() {
        return localVariableType == null ? null : localVariableType.getUnannType();
    }

    @Override
    public boolean isVar() {
        return localVariableType != null && localVariableType.isVar();
    }
}
