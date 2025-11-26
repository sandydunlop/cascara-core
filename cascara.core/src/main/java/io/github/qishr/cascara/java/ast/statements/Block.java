package io.github.qishr.cascara.java.ast.statements;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;

/// Block:
/// { [BlockStatements] }
public class Block extends ASTNode implements ScopingConstruct {
    BlockStatements blockStatements = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }

    public void setBlockStatements(BlockStatements blockStatements) {
        this.blockStatements = blockStatements;
        addChild(blockStatements);
    }
}
