package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// MethodDeclarator:
/// Identifier ( [ReceiverParameter ,] [FormalParameterList] ) [Dims]
public class MethodDeclarator extends ASTNode implements Identifiable {
    Identifier identifier = null;
    ReceiverParameter receiverParameter = null;
    FormalParameterList formalParameterList = null;
    Dims dims = null;

    @Override
    public Token getNameToken() {
        return identifier == null ? null : identifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public Identifier getIdentifier() {
        return identifier;
    }
    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
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
    public Dims getDims() {
        return dims;
    }
    public void setDims(Dims dims) {
        this.dims = dims;
        addChild(dims);
    }


}
