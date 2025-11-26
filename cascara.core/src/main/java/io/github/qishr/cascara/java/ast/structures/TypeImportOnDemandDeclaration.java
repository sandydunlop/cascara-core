package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.PackageOrTypeName;

/// TypeImportOnDemandDeclaration:
/// import PackageOrTypeName . * ;
public class TypeImportOnDemandDeclaration extends ASTNode {
    private PackageOrTypeName packageOrTypeName = null;

    public PackageOrTypeName getPackageOrTypeName() {
        return packageOrTypeName;
    }

    public void setPackageOrTypeName(PackageOrTypeName packageOrTypeName) {
        this.packageOrTypeName = packageOrTypeName;
    }

}
