package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.UnannType;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.DefaultValue;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// AnnotationInterfaceElementDeclaration:
/// {AnnotationInterfaceElementModifier} UnannType Identifier ( ) [Dims] [DefaultValue] ;
public class AnnotationInterfaceElementDeclaration extends ASTNode implements Identifiable {
    private List<AnnotationInterfaceElementModifier> annotationInterfaceElementModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private Identifier identifier = null;
    private Dims dims = null;
    private DefaultValue defaultValue = null;

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
        return getNameToken().lexeme();
    }

    public List<AnnotationInterfaceElementModifier> getAnnotationInterfaceElementModifierList() {
        return annotationInterfaceElementModifierList;
    }

    public void addAnnotationInterfaceElementModifier(AnnotationInterfaceElementModifier item) {
        annotationInterfaceElementModifierList.add(item);
        addChild(item);
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

    public Dims getDims() {
        return dims;
    }

    public void setDims(Dims dims) {
        this.dims = dims;
        addChild(dims);
    }

    public DefaultValue getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(DefaultValue defaultValue) {
        this.defaultValue = defaultValue;
        addChild(defaultValue);
    }

}