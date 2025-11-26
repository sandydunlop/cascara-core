package io.github.qishr.cascara.java.ast.statements;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// BlockStatements:
/// BlockStatement {BlockStatement}
public class BlockStatements extends ASTNode {
    List<BlockStatement> blockStatementList = new ArrayList<>();

    public void addBlockStatement(BlockStatement item) {
        blockStatementList.add(item);
        addChild(item);
    }

    public List<BlockStatement> getBlockStatementList() {
        return blockStatementList;
    }
}
