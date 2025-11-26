package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ReceiverParameter:
/// {Annotation} UnannType [Identifier .] this
public class ReceiverParameter extends ASTNode implements Identifiable {
    private List<Annotation> annotationList = new ArrayList<>();
    private UnannType unannType = null;
    private Identifier identifier = null;
    // TODO: This?

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

    public List<Annotation> getAnnotationList() {
        return annotationList;
        // TODO: For all lists, addCHild needs called for each element
    }

    public UnannType getUnannType() {
        return unannType;
    }

    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }


}
