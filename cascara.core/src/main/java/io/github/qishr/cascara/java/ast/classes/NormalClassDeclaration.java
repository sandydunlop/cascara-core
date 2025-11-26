package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.ModifiableAccess;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.semantic.TypeDeclaration;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// NormalClassDeclaration:
/// {ClassModifier} class TypeIdentifier [TypeParameters] [ClassExtends] [ClassImplements] [ClassPermits] ClassBody
public class NormalClassDeclaration extends ASTNode implements Identifiable, Declaration, TypeDeclaration, ScopingConstruct, ModifiableAccess {
    private TypeIdentifier typeIdentifier = null;
    private List<ClassModifier> classModifierList = new ArrayList<>();
    private TypeParameters typeParameters = null;
    private ClassExtends classExtends = null;
    private ClassImplements classImplements = null;
    private ClassPermits classPermits = null;
    private ClassBody classBody;

    @Override
    public List<Declaration> getDeclarations() {
        List<Declaration> declarations = new ArrayList<>();
        if (classBody != null) {
            for (ClassBodyDeclaration cbd : classBody.getClassBodyDeclarations()) {
                ClassMemberDeclaration cmd = cbd.getClassMemberDeclaration();
                // TODO: Constructors etc?

                if (cmd != null) {
                    declarations.addAll(cmd.getFieldDeclarations());
                    Declaration declaration = cmd.getDeclaration();
                    if (declaration != null) {
                        declarations.add(declaration);
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

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParameters typeParameters) {
        this.typeParameters = typeParameters;
        addChild(typeParameters);
    }

    public ClassExtends getClassExtends() {
        return classExtends;
    }

    public void setClassExtends(ClassExtends classExtends) {
        this.classExtends = classExtends;
        addChild(classExtends);
    }

    public ClassImplements getClassImplements() {
        return classImplements;
    }

    public void setClassImplements(ClassImplements classImplements) {
        this.classImplements = classImplements;
        addChild(classImplements);
    }

    public ClassPermits getClassPermits() {
        return classPermits;
    }

    public void setClassPermits(ClassPermits classPermits) {
        this.classPermits = classPermits;
        addChild(classPermits);
    }

    public ClassBody getClassBody() {
        return classBody;
    }

    public void setClassBody(ClassBody classBody) {
        this.classBody = classBody;
        addChild(classBody);
    }

    public void addClassModifier(ClassModifier item) {
        classModifierList.add(item);
        addChild(item);
    }

    public List<ClassModifier> getClassModifierList() {
        return classModifierList;
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

}
