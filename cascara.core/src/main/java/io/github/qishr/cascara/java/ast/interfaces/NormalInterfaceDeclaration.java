package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.TypeParameters;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.semantic.TypeDeclaration;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// NormalInterfaceDeclaration:
/// {InterfaceModifier} interface TypeIdentifier [TypeParameters] [InterfaceExtends] [InterfacePermits] InterfaceBody
public class NormalInterfaceDeclaration extends ASTNode implements Identifiable, Declaration, TypeDeclaration, ScopingConstruct {
    private List<InterfaceModifier> interfaceModifierList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private AnnotationInterfaceBody annotationInterfaceBody = null;
    private TypeParameters typeParameters = null;
    private InterfaceExtends interfaceExtends = null;
    private InterfacePermits interfacePermits = null;
    private InterfaceBody interfaceBody = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    @Override
    public Token getNameToken() {
        return typeIdentifier == null ? null : typeIdentifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public List<InterfaceModifier> getInterfaceModifierList() {
        return interfaceModifierList;
    }

    public void addInterfaceModifier(InterfaceModifier item) {
        interfaceModifierList.add(item);
        addChild(item);
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

    public AnnotationInterfaceBody getAnnotationInterfaceBody() {
        return annotationInterfaceBody;
    }

    public void setAnnotationInterfaceBody(AnnotationInterfaceBody annotationInterfaceBody) {
        this.annotationInterfaceBody = annotationInterfaceBody;
        addChild(annotationInterfaceBody);
    }

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParameters typeParameters) {
        this.typeParameters = typeParameters;
        addChild(typeParameters);
    }

    public InterfaceExtends getInterfaceExtends() {
        return interfaceExtends;
    }

    public void setInterfaceExtends(InterfaceExtends interfaceExtends) {
        this.interfaceExtends = interfaceExtends;
        addChild(interfaceExtends);
    }

    public InterfacePermits getInterfacePermits() {
        return interfacePermits;
    }

    public void setInterfacePermits(InterfacePermits interfacePermits) {
        this.interfacePermits = interfacePermits;
        addChild(interfacePermits);
    }

    public InterfaceBody getInterfaceBody() {
        return interfaceBody;
    }

    public void setInterfaceBody(InterfaceBody interfaceBody) {
        this.interfaceBody = interfaceBody;
        addChild(interfaceBody);
    }
}
