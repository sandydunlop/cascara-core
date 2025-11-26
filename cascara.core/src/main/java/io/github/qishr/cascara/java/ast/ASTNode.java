package io.github.qishr.cascara.java.ast;

import java.util.ArrayList;
import java.util.List;

public class ASTNode {
    ASTNode parent = null;
    List<ASTNode> children = new ArrayList<>();

    public void addChild(ASTNode child) {
        if (child != null) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    public void setParent(ASTNode parent) {
        this.parent = parent;
    }

    public ASTNode getParent() {
        return parent;
    }

    public List<ASTNode> getChildren() {
        return children;
    }

    public <T extends ASTNode> List<T> getDescendants(Class<T> type) {
        List<T> constructList = new ArrayList<>();
        traverseConstructs(this, type, constructList);
        return constructList;
    }

    private <T extends ASTNode> void traverseConstructs(ASTNode node, Class<T> type, List<T> constructList) {
        if (type.isInstance(node)) {
            constructList.add(type.cast(node));
        }
        for (ASTNode child : node.getChildren()) {
            traverseConstructs(child, type, constructList);
        }
    }

    public <T extends ASTNode> T getFirstDescendant(Class<T> type) {
        for (ASTNode child : this.getChildren()) {
            if (type.isInstance(child)) {
                return type.cast(child);
            }
            T instance = child.getFirstDescendant(type);
            if (instance != null) {
                return instance;
            }
        }
        return null;
    }

    public <T extends ASTNode> T getFirstAncestor(Class<T> type) {
        ASTNode node = this.getParent();
        while (node != null) {
            if (type.isInstance(node)) {
                return type.cast(node);
            }
            node = node.getParent();
        }
        return null;
    }
}
