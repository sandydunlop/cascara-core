package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// PackageOrTypeName:
/// Identifier
/// PackageOrTypeName . Identifier
public class PackageOrTypeName extends ASTNode implements Identifiable, Name {
    private Identifier identifier = null;
    private Name packageOrTypeName = null;

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
        StringBuilder sb = new StringBuilder();
        if (packageOrTypeName != null) {
            sb.append(packageOrTypeName.toString());
        }
        if (identifier != null) {
            if (!sb.isEmpty()) {
                sb.append(".");
            }
            sb.append(identifier.toString());
        }
        if (sb.isEmpty()) {
            return StringConstant.UNNAMED;
        } else {
            return sb.toString();
        }
    }

    // @Override
    public String getQualifiedName() {
        if (packageOrTypeName != null) {
            return packageOrTypeName.getQualifiedName() + "." + identifier.getNameToken().lexeme();
        } else {
            return identifier.getNameToken().lexeme();
        }
    }

    @Override
    public Name getQualifier() {
        return packageOrTypeName;
    }

    @Override
    public boolean isQualified() {
        return packageOrTypeName != null;
    }

    public Name getName() {
        return packageOrTypeName;
    }

    public void setName(Name name) {
        this.packageOrTypeName = name;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public Name getPackageOrTypeName() {
        return packageOrTypeName;
    }

    public void setPackageOrTypeName(Name packageOrTypeName) {
        this.packageOrTypeName = packageOrTypeName;
        if (packageOrTypeName instanceof PackageOrTypeName potn) {
            addChild(potn);
        }
    }
}
