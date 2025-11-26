package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// TryWithResourcesStatement:
/// try ResourceSpecification Block [Catches] [Finally]
public class TryWithResourcesStatement extends ASTNode implements ScopingConstruct {
    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

}
