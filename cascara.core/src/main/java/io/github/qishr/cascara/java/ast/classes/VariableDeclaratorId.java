package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// VariableDeclaratorId:
/// Identifier [Dims]
public class VariableDeclaratorId extends ASTNode implements Identifiable {
    private Identifier identifier = null;
    private Dims dims = null;

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

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
        addChild(dims);
    }


}
