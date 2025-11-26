package io.github.qishr.cascara.java.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.VariableArityParameter;
import io.github.qishr.cascara.java.ast.classes.VariableDeclaratorId;
import io.github.qishr.cascara.java.ast.classes.VariableModifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// NormalLambdaParameter:
/// {VariableModifier} LambdaParameterType VariableDeclaratorId
/// VariableArityParameter
public class NormalLambdaParameter extends ASTNode implements Identifiable {
    private List<VariableModifier> variableModifierList = new ArrayList<>();
    private LambdaParameterType lambdaParameterType = null;
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
        return getNameToken().lexeme();
    }

    public List<VariableModifier> getVariableModifierList() {
        return variableModifierList;
    }

    public void addVariableModifier(VariableModifier item) {
        variableModifierList.add(item);
        addChild(item);
    }

    public LambdaParameterType getLambdaParameterType() {
        return lambdaParameterType;
    }

    public void setLambdaParameterType(LambdaParameterType lambdaParameterType) {
        this.lambdaParameterType = lambdaParameterType;
    }

    public VariableDeclaratorId getVariableDeclaratorId() {
        return variableDeclaratorId;
    }

    public void setVariableDeclaratorId(VariableDeclaratorId variableDeclaratorId) {
        this.variableDeclaratorId = variableDeclaratorId;
    }

    public VariableArityParameter getVariableArityParameter() {
        return variableArityParameter;
    }

    public void setVariableArityParameter(VariableArityParameter variableArityParameter) {
        this.variableArityParameter = variableArityParameter;
    }


}
