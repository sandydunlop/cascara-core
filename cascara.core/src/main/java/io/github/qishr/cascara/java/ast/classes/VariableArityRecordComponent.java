package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// VariableArityRecordComponent:
/// {RecordComponentModifier} UnannType {Annotation} ... Identifier
public class VariableArityRecordComponent extends ASTNode implements Identifiable {
    private List<RecordComponentModifier> recordComponentModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private List<Annotation> annotationList = new ArrayList<>();
    private Identifier identifier = null;

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

    public List<RecordComponentModifier> getRecordComponentModifierList() {
        return recordComponentModifierList;
    }

    public void addRecordComponentModifier(RecordComponentModifier item) {
        recordComponentModifierList.add(item);
        addChild(item);
    }

    public UnannType getUnannType() {
        return unannType;
    }

    public void setUnannType(UnannType unannType) {
        this.unannType = unannType;
        addChild(unannType);
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }
}
