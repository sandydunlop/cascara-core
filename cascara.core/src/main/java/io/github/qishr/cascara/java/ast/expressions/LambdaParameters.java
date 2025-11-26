package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

//TODO: This is missing ConciseLambdaParameter
/// LambdaParameters:
/// ( [LambdaParameterList] )
/// Identifier
public class LambdaParameters extends ASTNode implements Identifiable {
    private LambdaParameterList lambdaParameterList = null;
    private ConciseLambdaParameter conciseLambdaParameter = null;

    @Override
    public Token getNameToken() {
        return conciseLambdaParameter == null ? null : conciseLambdaParameter.getNameToken();
        // return identifier == null ? null : identifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public LambdaParameterList getLambdaParameterList() {
        return lambdaParameterList;
    }

    public void setLambdaParameterList(LambdaParameterList lambdaParameterList) {
        this.lambdaParameterList = lambdaParameterList;
        addChild(lambdaParameterList);
    }

    public ConciseLambdaParameter getConciseLambdaParameter() {
        return conciseLambdaParameter;
    }

    public void setConciseLambdaParameter(ConciseLambdaParameter conciseLambdaParameter) {
        this.conciseLambdaParameter = conciseLambdaParameter;
    }

    // public Identifier getIdentifier() {
    //     return identifier;
    // }

    // public void setIdentifier(Identifier identifier) {
    //     this.identifier = identifier;
    //     addChild(identifier);
    // }

}
