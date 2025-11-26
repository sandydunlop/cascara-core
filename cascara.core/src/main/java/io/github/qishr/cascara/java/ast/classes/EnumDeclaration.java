package io.github.qishr.cascara.java.ast.classes;

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

/// EnumDeclaration:
/// {ClassModifier} enum TypeIdentifier [ClassImplements] EnumBody
public class EnumDeclaration extends ASTNode implements Identifiable, Declaration, TypeDeclaration, ScopingConstruct {
    private List<ClassModifier> classModifierList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private ClassImplements classImplements = null;
    private EnumBody enumBody = null;

    @Override
    public List<Declaration> getDeclarations() {
        List<Declaration> declarations = new ArrayList<>();
        if (enumBody != null) {
            EnumBodyDeclarations ebd = enumBody.getEnumBodyDeclarations();
            if (ebd != null) {
                for (ClassBodyDeclaration cbd : ebd.getClassBodyDeclarationList()) {
                    ClassMemberDeclaration cmd = cbd.getClassMemberDeclaration();
                    if (cmd != null) {
                        declarations.addAll(cmd.getFieldDeclarations());
                        Declaration declaration = cmd.getDeclaration();
                        if (declaration != null) {
                            declarations.add(declaration);
                        }
                    }
                }
            }
        }
        return declarations;
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

    public List<ClassModifier> getClassModifierList() {
        return classModifierList;
    }

    public void addClassModifier(ClassModifier item) {
        classModifierList.add(item);
        addChild(item);
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

    public ClassImplements getClassImplements() {
        return classImplements;
    }

    public void setClassImplements(ClassImplements classImplements) {
        this.classImplements = classImplements;
        addChild(classImplements);
    }

    public EnumBody getEnumBody() {
        return enumBody;
    }

    public void setEnumBody(EnumBody enumBody) {
        this.enumBody = enumBody;
        addChild(enumBody);
    }

}
