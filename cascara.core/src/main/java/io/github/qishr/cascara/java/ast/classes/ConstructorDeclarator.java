package io.github.qishr.cascara.java.ast.classes;

import java.lang.invoke.StringConcatException;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ConstructorDeclarator:
/// [TypeParameters] SimpleTypeName ( [ReceiverParameter ,] [FormalParameterList] )
public class ConstructorDeclarator extends ASTNode implements Identifiable {
    private TypeParameters typeParameters = null;
    private SimpleTypeName simpleTypeName = null;
    private ReceiverParameter receiverParameter = null;
    private FormalParameterList formalParameterList = null;

    public Token getNameToken() {
        return simpleTypeName == null ? null : simpleTypeName.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        // TODO: TypeParameters etc?
        if (simpleTypeName != null) {
            return simpleTypeName.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParameters typeParameters) {
        this.typeParameters = typeParameters;
        addChild(typeParameters);
    }

    public SimpleTypeName getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(SimpleTypeName simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
        addChild(simpleTypeName);
    }

    public ReceiverParameter getReceiverParameter() {
        return receiverParameter;
    }

    public void setReceiverParameter(ReceiverParameter receiverParameter) {
        this.receiverParameter = receiverParameter;
        addChild(receiverParameter);
    }

    public FormalParameterList getFormalParameterList() {
        return formalParameterList;
    }

    public void setFormalParameterList(FormalParameterList formalParameterList) {
        this.formalParameterList = formalParameterList;
        addChild(formalParameterList);
    }
}
