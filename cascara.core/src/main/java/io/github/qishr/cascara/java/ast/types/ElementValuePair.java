package io.github.qishr.cascara.java.ast.types;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ElementValuePair:
/// Identifier = ElementValue
public class ElementValuePair extends ASTNode implements Identifiable {
    private Identifier identifier = null;
    private ElementValue elementValue = null;

    @Override
    public Token getNameToken() {
        return identifier == null ? null : identifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return toString();
    }

    @Override
    public String toString() {
        if (identifier != null && elementValue != null) {
            return identifier.toString() + "=" + elementValue.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public ElementValue getElementValue() {
        return elementValue;
    }

    public void setElementValue(ElementValue elementValue) {
        this.elementValue = elementValue;
        addChild(elementValue);
    }
}
