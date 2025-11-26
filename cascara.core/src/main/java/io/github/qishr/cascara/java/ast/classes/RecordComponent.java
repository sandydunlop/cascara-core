package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// RecordComponent:
/// {RecordComponentModifier} UnannType Identifier
/// VariableArityRecordComponent
public class RecordComponent extends ASTNode implements Identifiable, Declaration {
    private List<RecordComponentModifier> recordComponentModifierList = new ArrayList<>();
    private UnannType unannType = null;
    private Identifier identifier = null;
    private VariableArityRecordComponent variableArityRecordComponent = null;

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

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public VariableArityRecordComponent getVariableArityRecordComponent() {
        return variableArityRecordComponent;
    }

    public void setVariableArityRecordComponent(VariableArityRecordComponent variableArityRecordComponent) {
        this.variableArityRecordComponent = variableArityRecordComponent;
        addChild(variableArityRecordComponent);
    }


}
