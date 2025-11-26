package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ModularCompilationUnit:
/// {ImportDeclaration} ModuleDeclaration
public class ModularCompilationUnit extends ASTNode {
    private List<ImportDeclaration> importDeclarationList = new ArrayList<>();
    private ModuleDeclaration moduleDeclaration = null;

    public List<ImportDeclaration> getImportDeclarationList() {
        return importDeclarationList;
    }

    public void addImportDeclaration(ImportDeclaration item) {
        importDeclarationList.add(item);
        addChild(item);
    }

    public ModuleDeclaration getModuleDeclaration() {
        return moduleDeclaration;
    }

    public void setModuleDeclaration(ModuleDeclaration moduleDeclaration) {
        this.moduleDeclaration = moduleDeclaration;
        addChild(moduleDeclaration);
    }

}
