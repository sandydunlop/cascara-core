package io.github.qishr.cascara.java.ast.expressions;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// LambdaExpression:
/// LambdaParameters -> LambdaBody
public class LambdaExpression extends ASTNode implements ScopingConstruct {
    private LambdaParameters lambdaParameters = null;
    private LambdaBody lambdaBody = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    public LambdaParameters getLambdaParameters() {
        return lambdaParameters;
    }

    public void setLambdaParameters(LambdaParameters lambdaParameters) {
        this.lambdaParameters = lambdaParameters;
        addChild(lambdaParameters);
    }

    public LambdaBody getLambdaBody() {
        return lambdaBody;
    }

    public void setLambdaBody(LambdaBody lambdaBody) {
        this.lambdaBody = lambdaBody;
        addChild(lambdaBody);
    }

}
