package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.UnqualifiedMethodIdentifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// MethodName:
/// UnqualifiedMethodIdentifier
public class MethodName extends ASTNode implements Identifiable {
    // TODO implements Name?
    private UnqualifiedMethodIdentifier unqualifiedMethodIdentifier = null;

    @Override
    public Token getNameToken() {
        return unqualifiedMethodIdentifier == null ? null : unqualifiedMethodIdentifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return toString();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public UnqualifiedMethodIdentifier getUnqualifiedMethodIdentifier() {
        return unqualifiedMethodIdentifier;
    }

    public void setUnqualifiedMethodIdentifier(UnqualifiedMethodIdentifier unqualifiedMethodIdentifier) {
        this.unqualifiedMethodIdentifier = unqualifiedMethodIdentifier;
        addChild(unqualifiedMethodIdentifier);
    }

    // @Override
    // public boolean isQualified() {
    //     return false;
    // }

    // @Override
    // public Identifier getIdentifier() {
    //     return unqualifiedMethodIdentifier == null ? null : unqualifiedMethodIdentifier.getIdentifier();
    // }

    // @Override
    // public Name getQualifier() {
    //     return null;
    // }

    // @Override
    // public String getQualifiedName() {
    //     if (getNameToken() != null) {
    //         return getNameToken().lexeme();
    //     } else {
    //         return StringConstant.UNDEFINED;
    //     }
    // }
}
