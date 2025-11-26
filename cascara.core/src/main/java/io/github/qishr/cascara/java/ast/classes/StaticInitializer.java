package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// StaticInitializer:
/// static Block
public class StaticInitializer extends ASTNode {
    private StaticInitializer staticInitializer = null;

    public StaticInitializer getStaticInitializer() {
        return staticInitializer;
    }

    public void setStaticInitializer(StaticInitializer staticInitializer) {
        this.staticInitializer = staticInitializer;
        addChild(staticInitializer);
    }
}
