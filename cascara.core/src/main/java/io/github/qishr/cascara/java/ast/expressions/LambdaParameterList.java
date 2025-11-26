package io.github.qishr.cascara.java.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;

/// LambdaParameterList:
/// NormalLambdaParameter {, NormalLambdaParameter}
/// ConciseLambdaParameter {, ConciseLambdaParameter}
public class LambdaParameterList extends ASTNode {
    private List<NormalLambdaParameter> normalLambdaParameterList = new ArrayList<>();
    private List<ConciseLambdaParameter> conciseLambdaParameterList = new ArrayList<>();

    public List<NormalLambdaParameter> getLambdaParameterList() {
        return normalLambdaParameterList;
    }

    public List<ConciseLambdaParameter> getIdentifierList() {
        return conciseLambdaParameterList;
    }

    public void addNormalLambdaParameter(NormalLambdaParameter item) {
        normalLambdaParameterList.add(0, item);
        addChild(item);
    }

    public void addConciseLambdaParameter(ConciseLambdaParameter item) {
        conciseLambdaParameterList.add(item);
        addChild(item);
    }
}
