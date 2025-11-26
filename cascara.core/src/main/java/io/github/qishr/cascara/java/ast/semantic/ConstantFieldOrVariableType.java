package io.github.qishr.cascara.java.ast.semantic;

import io.github.qishr.cascara.java.ast.classes.UnannType;

public interface ConstantFieldOrVariableType {
    public boolean isVar();
    public UnannType getUnannType();
    public String toString();
}
