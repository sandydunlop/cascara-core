package io.github.qishr.cascara.java.model;

import java.util.ArrayList;
import java.util.List;

public class Dependency {
    private String packageName = null;
    private List<TypeNode> types = new ArrayList<>();

    public Dependency(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public List<TypeNode> getTypes() {
        return types;
    }

    public void addType(TypeNode type) {
        types.add(type);
    }

    public boolean contains(TypeNode type) {
        return types.contains(type);
    }

    public TypeNode getType(String typeName) {
        for (TypeNode typeNode : types) {
            if (typeNode.getName().fullyQualifiedName().equals(typeName)) {
                return typeNode;
            }
        }
        return null;
    }
}
