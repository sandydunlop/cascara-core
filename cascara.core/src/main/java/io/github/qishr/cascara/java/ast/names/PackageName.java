package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// PackageName:
/// Identifier
/// PackageName . Identifier
public class PackageName extends ASTNode implements Identifiable {
    // TODO implements Name?
    private PackageName packageName = null;
    private Identifier identifier = null;

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
        if (identifier != null) {
            if (packageName != null) {
                return packageName.toString() + "." + identifier.toString();
            } else {
                return identifier.toString();
            }
        } else {
            return StringConstant.UNDEFINED;
        }
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public void setPackageName(PackageName packageName) {
        this.packageName = packageName;
        addChild(packageName);
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }
}
