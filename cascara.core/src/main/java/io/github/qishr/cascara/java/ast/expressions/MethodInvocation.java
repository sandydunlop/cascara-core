package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.names.ExpressionName;
import io.github.qishr.cascara.java.ast.names.MethodName;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.TypeArguments;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// MethodInvocation:
/// MethodName ( [ArgumentList] )
/// TypeName . [TypeArguments] Identifier ( [ArgumentList] )
/// ExpressionName . [TypeArguments] Identifier ( [ArgumentList] )
/// Primary . [TypeArguments] Identifier ( [ArgumentList] )
/// super . [TypeArguments] Identifier ( [ArgumentList] )
/// TypeName . super . [TypeArguments] Identifier ( [ArgumentList] )
public class MethodInvocation extends ASTNode implements Identifiable {
    private MethodName methodName = null;
    private ArgumentList argumentList = null;
    private TypeName typeName = null;
    private TypeArguments typeArguments = null;
    private Identifier identifier = null;
    private ExpressionName expressionName = null;
    private Primary primary = null;
    private boolean isSuper = false;

    @Override
    public Token getNameToken() {
        if (methodName != null) {
            return methodName.getNameToken();
        } else if (identifier != null) {
            return identifier.getNameToken();
        }
        return null;
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public ASTNode getTarget() {
        if (typeName != null) {
            return typeName;
        }
        if (expressionName != null) {
            return expressionName;
        }
        if (primary != null) {
            return primary;
        }
        return null;
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public MethodName getMethodName() {
        return methodName;
    }

    public void setMethodName(MethodName methodName) {
        this.methodName = methodName;
        addChild(methodName);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public ArgumentList getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(ArgumentList argumentList) {
        this.argumentList = argumentList;
        addChild(argumentList);
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
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

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean isSuper) {
        this.isSuper = isSuper;
        addChild(argumentList);
    }
}
