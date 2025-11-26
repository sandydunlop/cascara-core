package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// CompilationUnit:
/// OrdinaryCompilationUnit
/// ModularCompilationUnit
public class CompilationUnit extends ASTNode implements ScopingConstruct {
    private OrdinaryCompilationUnit ordinaryCompilationUnit = null;
    private ModularCompilationUnit modularCompilationUnit = null;

    public OrdinaryCompilationUnit getOrdinaryCompilationUnit() {
        return ordinaryCompilationUnit;
    }

    public void setOrdinaryCompilationUnit(OrdinaryCompilationUnit ordinaryCompilationUnit) {
        this.ordinaryCompilationUnit = ordinaryCompilationUnit;
        addChild(ordinaryCompilationUnit);
    }

    public ModularCompilationUnit getModularCompilationUnit() {
        return modularCompilationUnit;
    }

    public void setModularCompilationUnit(ModularCompilationUnit modularCompilationUnit) {
        this.modularCompilationUnit = modularCompilationUnit;
        addChild(modularCompilationUnit);
    }

    @Override
    public List<Declaration> getDeclarations() {
        if (ordinaryCompilationUnit != null) {
            return null; //TODO: Implement this
        } else if (modularCompilationUnit != null) {
            return null; //TODO: Implement this
        } else {
            return new ArrayList<>();
        }
    }
}
