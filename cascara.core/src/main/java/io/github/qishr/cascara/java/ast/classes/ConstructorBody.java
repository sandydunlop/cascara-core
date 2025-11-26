package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.statements.BlockStatements;

/// ConstructorBody:
/// { [BlockStatements] ConstructorInvocation [BlockStatements] }
/// { [BlockStatements] }
public class ConstructorBody extends ASTNode {
    private BlockStatements blockStatements = null;
    private ConstructorInvocation constructorInvocation = null;
    private BlockStatements moreBlockStatements = null;

    public BlockStatements getBlockStatements() {
        return blockStatements;
    }

    public void setBlockStatements(BlockStatements blockStatements) {
        this.blockStatements = blockStatements;
        addChild(blockStatements);
    }

    public ConstructorInvocation getConstructorInvocation() {
        return constructorInvocation;
    }

    public void setConstructorInvocation(ConstructorInvocation constructorInvocation) {
        this.constructorInvocation = constructorInvocation;
        addChild(constructorInvocation);
    }

    public BlockStatements getMoreBlockStatements() {
        return moreBlockStatements;
    }

    public void setMoreBlockStatements(BlockStatements moreBlockStatements) {
        this.moreBlockStatements = moreBlockStatements;
        addChild(moreBlockStatements);
    }
}
