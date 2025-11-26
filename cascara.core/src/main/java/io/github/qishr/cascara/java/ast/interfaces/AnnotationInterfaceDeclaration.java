package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.semantic.TypeDeclaration;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// AnnotationInterfaceDeclaration:
/// {InterfaceModifier} @ interface TypeIdentifier AnnotationInterfaceBody
public class AnnotationInterfaceDeclaration extends ASTNode implements Identifiable, Declaration, TypeDeclaration, ScopingConstruct {
    private List<InterfaceModifier> interfaceModifierList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private AnnotationInterfaceBody annotationInterfaceBody = null;

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

}
