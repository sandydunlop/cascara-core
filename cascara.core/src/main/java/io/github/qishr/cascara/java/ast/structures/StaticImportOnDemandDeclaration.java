package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;

/// StaticImportOnDemandDeclaration:
/// import static TypeName . * ;
public class StaticImportOnDemandDeclaration extends ASTNode {
    private TypeName typeName = null;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
    }

}
