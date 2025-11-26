package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ModuleName;

/// SingleModuleImportDeclaration:
/// import module ModuleName ;
public class SingleModuleImportDeclaration extends ASTNode {
    private ModuleName moduleName = null;

    public ModuleName getModuleName() {
        return moduleName;
    }

    public void setModuleName(ModuleName moduleName) {
        this.moduleName = moduleName;
    }

}
