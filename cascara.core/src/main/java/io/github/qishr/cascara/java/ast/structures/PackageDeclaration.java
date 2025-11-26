package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;


/// PackageDeclaration:
/// {PackageModifier} package Identifier {. Identifier} ;
public class PackageDeclaration extends ASTNode {
    List<PackageModifier> packageModifiers = new ArrayList<>();
    List<Identifier> identifiers = new ArrayList<>();

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public List<PackageModifier> getPackageModifiers() {
        return packageModifiers;
    }

    public PackageDeclaration() {
        // Nothing to see here
    }

    public String getPackageName() {
        StringBuilder sb = new StringBuilder();
        for (Identifier id : identifiers) {
            if (!sb.isEmpty()) {
                sb.append(".");
            }
            sb.append(id.toString());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getPackageName();
    }
}
