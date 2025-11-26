package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.expressions.ArgumentList;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// EnumConstant:
/// {EnumConstantModifier} Identifier [( [ArgumentList] )] [ClassBody]
public class EnumConstant extends ASTNode implements Identifiable {
    private List<EnumConstantModifier> enumConstantModifierList = new ArrayList<>();
    private Identifier identifier = null;
    private ArgumentList argumentList = null;
    private ClassBody classBody = null;

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

    public List<EnumConstantModifier> getEnumConstantModifierList() {
        return enumConstantModifierList;
    }

    public void addEnumConstantModifier(EnumConstantModifier item) {
        enumConstantModifierList.add(item);
        addChild(item);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public ArgumentList getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(ArgumentList argumentList) {
        this.argumentList = argumentList;
        addChild(argumentList);
    }

    public ClassBody getClassBody() {
        return classBody;
    }

    public void setClassBody(ClassBody classBody) {
        this.classBody = classBody;
        addChild(classBody);
    }

}
