package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ExpressionName:
/// Identifier
/// AmbiguousName . Identifier
public class ExpressionName extends ASTNode implements Identifiable, Name {
    private Identifier identifier = null;
    private Name ambiguousName = null;

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
        return getQualifiedName();
        // StringBuilder sb = new StringBuilder();
        // if (ambiguousName != null) {
        //     sb.append(ambiguousName.toString());
        // }
        // if (identifier != null) {
        //     if (!sb.isEmpty()) {
        //         sb.append(".");
        //     }
        //     sb.append(identifier.toString());
        // }
        // if (sb.isEmpty()) {
        //     return StringConstant.UNNAMED;
        // } else {
        //     return sb.toString();
        // }
    }

    @Override
    public String getQualifiedName() {
        if (ambiguousName != null) {
            return ambiguousName.getQualifiedName() + "." + identifier.getNameToken().lexeme();
        } else {
            return identifier.getNameToken().lexeme();
        }
    }

    @Override
    public Name getQualifier() {
        return ambiguousName;
    }

    @Override
    public boolean isQualified() {
        return ambiguousName != null;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public Name getName() {
        return ambiguousName;
    }

    public void setName(Name name) {
        this.ambiguousName = name;
        if (name instanceof AmbiguousName ambiguousName) {
            addChild(ambiguousName);
        } else if (name instanceof ExpressionName expressionName) {
            addChild(expressionName);
        }
    }

}
