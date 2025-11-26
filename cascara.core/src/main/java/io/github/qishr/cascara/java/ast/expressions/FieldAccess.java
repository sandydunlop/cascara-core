package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// FieldAccess:
/// Primary . Identifier
/// super . Identifier
/// TypeName . super . Identifier
public class FieldAccess extends ASTNode implements Identifiable {
    private Primary primary = null;
    private Identifier identifier = null;
    private Name typeName = null;
    private boolean isSuper = false;

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

    public Primary getPrimary() {
        return primary;
    }

    public void setPrimary(Primary primary) {
        this.primary = primary;
        addChild(primary);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public Name getTypeName() {
        return typeName;
    }

    public void setTypeName(Name name) {
        if (name == null) {
            if (typeName != null) {
                getChildren().remove(typeName);
            }
            typeName = null;
        } else {
            this.typeName = name;
            if (name instanceof TypeName typeName) {
                addChild(typeName);
            } else {
                this.typeName = null;
            }
        }
    }


    public boolean isSuper() {
        return isSuper;
    }


    public void setSuper(boolean isSuper) {
        this.isSuper = isSuper;
    }

}
