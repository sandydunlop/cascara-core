package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// OrdinaryCompilationUnit:
/// [PackageDeclaration] {ImportDeclaration} {TopLevelClassOrInterfaceDeclaration}
public class OrdinaryCompilationUnit extends ASTNode {
    PackageDeclaration packageDeclaration = null;
    private List<ImportDeclaration> importDeclarationList = new ArrayList<>();
    List<TopLevelClassOrInterfaceDeclaration> topLevelClassOrInterfaceDeclarations = new ArrayList<>();

    public void addImportDeclaration(ImportDeclaration item) {
        importDeclarationList.add(item);
        addChild(item);
    }

    public List<ImportDeclaration> getImportDeclarationList() {
        return importDeclarationList;
    }

    public void addTopLevelClassOrInterfaceDeclaration(TopLevelClassOrInterfaceDeclaration item) {
        topLevelClassOrInterfaceDeclarations.add(item);
        addChild(item);
    }

    public List<TopLevelClassOrInterfaceDeclaration> getTopLevelClassOrInterfaceDeclarations() {
        return topLevelClassOrInterfaceDeclarations;
    }

    public PackageDeclaration getPackageDeclaration() {
        return packageDeclaration;
    }

    public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
        this.packageDeclaration = packageDeclaration;
        addChild(packageDeclaration);
    }

}
