package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.ArgumentList;
import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.names.ExpressionName;
import io.github.qishr.cascara.java.ast.types.TypeArguments;

/// ConstructorInvocation:
/// [TypeArguments] this ( [ArgumentList] ) ;
/// [TypeArguments] super ( [ArgumentList] ) ;
/// ExpressionName . [TypeArguments] super ( [ArgumentList] ) ;
/// Primary . [TypeArguments] super ( [ArgumentList] ) ;
public class ConstructorInvocation extends ASTNode {
    private TypeArguments typeArguments = null;
    private ArgumentList argumentList = null;
    private ExpressionName expressionName = null;
    private Primary primary = null;

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
    }

    public ArgumentList getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(ArgumentList argumentList) {
        this.argumentList = argumentList;
        addChild(argumentList);
    }

    public ExpressionName getExpressionName() {
        return expressionName;
    }

    public void setExpressionName(ExpressionName expressionName) {
        this.expressionName = expressionName;
        addChild(expressionName);
    }

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
        addChild(primary);
    }
}
