package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.statements.Block;

/// MethodBody:
/// Block
/// ;
public class MethodBody extends ASTNode {
    Block block = null;

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
        addChild(block);
    }
}
