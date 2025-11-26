package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.UnannType;

/// LambdaParameterType:
/// UnannType
/// var
public class LambdaParameterType extends ASTNode {
    private UnannType unannType = null;
    private boolean isVar = false;

    public UnannType getUnannType() {
        return unannType;
    }

    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }

    public boolean isVar() {
        return isVar;
    }

    public void setVar(boolean isVar) {
        this.isVar = isVar;
    }


}
