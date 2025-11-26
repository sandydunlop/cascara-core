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

/// RecordDeclaration:
/// {ClassModifier} record TypeIdentifier [TypeParameters] RecordHeader [ClassImplements] RecordBody
public class RecordDeclaration extends ASTNode implements Identifiable, Declaration, TypeDeclaration, ScopingConstruct {
    private List<ClassModifier> classModifierList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private TypeParameters typeParameters = null;
    private RecordHeader recordHeader = null;
    private ClassImplements classImplements = null;
    private RecordBody recordBody = null;

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

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParameters typeParameters) {
        this.typeParameters = typeParameters;
        addChild(typeParameters);
    }

    public RecordHeader getRecordHeader() {
        return recordHeader;
    }

    public void setRecordHeader(RecordHeader recordHeader) {
        this.recordHeader = recordHeader;
        addChild(recordHeader);
    }

    public ClassImplements getClassImplements() {
        return classImplements;
    }

    public void setClassImplements(ClassImplements classImplements) {
        this.classImplements = classImplements;
        addChild(classImplements);
    }

    public RecordBody getRecordBody() {
        return recordBody;
    }

    public void setRecordBody(RecordBody recordBody) {
        this.recordBody = recordBody;
        addChild(recordBody);
    }


}
