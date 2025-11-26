package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// FormalParameter:
/// {VariableModifier} UnannType VariableDeclaratorId
/// VariableArityParameter
public class FormalParameter extends ASTNode implements Identifiable, Declaration {
    private List<VariableModifier> variableModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private VariableDeclaratorId variableDeclaratorId = null;
    private VariableArityParameter variableArityParameter = null;

    @Override
    public Token getNameToken() {
        return variableDeclaratorId == null ? null : variableDeclaratorId.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public void addVariableModifier(VariableModifier item) {
        variableModifierList.add(item);
        addChild(item);
    }

    public List<VariableModifier> getVariableModifierList() {
        return variableModifierList;
    }

    public UnannType getUnannType() {
        return unannType;
    }

    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }

    public VariableDeclaratorId getVariableDeclaratorId() {
        return variableDeclaratorId;
    }

    public void setVariableDeclaratorId(VariableDeclaratorId variableDeclaratorId) {
        this.variableDeclaratorId = variableDeclaratorId;
        addChild(variableDeclaratorId);
    }

    public VariableArityParameter getVariableArityParameter() {
        return variableArityParameter;
    }

    public void setVariableArityParameter(VariableArityParameter variableArityParameter) {
        this.variableArityParameter = variableArityParameter;
        addChild(variableArityParameter);
    }


}
