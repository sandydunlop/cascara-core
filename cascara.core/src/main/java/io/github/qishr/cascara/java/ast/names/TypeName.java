package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// TypeName:
/// TypeIdentifier
/// PackageOrTypeName . TypeIdentifier
public class TypeName extends ASTNode implements Identifiable, Name {
    private TypeIdentifier typeIdentifier = null;
    private Name packageOrTypeName = null;

    @Override
    public Token getNameToken() {
        // TODO: This needs to be able to work out the entire package name?
        // TODO: Check other identifier type things
        return typeIdentifier == null ? null : typeIdentifier.getNameToken();
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
        if (typeIdentifier != null) {
            if (!sb.isEmpty()) {
                sb.append(".");
            }
            sb.append(typeIdentifier.toString());
        }
        if (sb.isEmpty()) {
            return StringConstant.UNNAMED;
        } else {
            return sb.toString();
        }
    }

    @Override
    public boolean isQualified() {
        return packageOrTypeName != null;
    }

    @Override
    public Identifier getIdentifier() {
        return typeIdentifier == null ? null : typeIdentifier.getIdentifier();
    }

    @Override
    public Name getQualifier() {
        return packageOrTypeName;
    }

    // @Override
    public String getQualifiedName() {
        if (packageOrTypeName != null) {
            return packageOrTypeName.getQualifiedName() + "." + typeIdentifier.getNameToken().lexeme();
        } else {
            return typeIdentifier.getNameToken().lexeme();
        }
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }
    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }
    public Name getPackageOrTypeName() {
        return packageOrTypeName;
    }
    public void setPackageOrTypeName(PackageOrTypeName packageOrTypeName) {
        this.packageOrTypeName = packageOrTypeName;
        addChild(packageOrTypeName);
    }

    @Override
    public void setIdentifier(Identifier identifier) {
        throw new IllegalAccessError();
        // if (typeIdentifier != null) {
        //     typeIdentifier.setIdentifier(identifier);
        // }
        // if (getChildren().contains(identifier)) {
        //     getChildren().remove(identifier);
        // }
        // addChild(identifier);
    }

    @Override
    public Name getName() {
        return packageOrTypeName;
    }

    @Override
    public void setName(Name name) {
        packageOrTypeName = name;
        addChild(typeIdentifier);
    }


}
