package io.github.qishr.cascara.java.analyzer;

import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.TypeNode;
import io.github.qishr.cascara.java.model.VariableNode;

public class Binding {
    private String identifier;
    private int line;
    private SemanticType semanticType;
    private TypeNode typeNode;
    private MethodNode methodNode;
    private VariableNode variableNode;
    private Declaration declaration;
    private int usageCount = 0;

    public String getIdentifier() {
        return identifier;
    }

    public int getLine() {
        return line;
    }

    public SemanticType getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(SemanticType semanticType) {
        this.semanticType = semanticType;
    }

    public TypeNode getTypeNode() {
        return typeNode;
    }

    public MethodNode getMethodNode() {
        return methodNode;
    }

    public VariableNode getVariableNode() {
        return variableNode;
    }

    public Declaration getDeclaration() {
        return declaration;
    }

    public void recordUsage() {
        usageCount++;
    }

    public int getUsageCount() {
        return usageCount;
    }

    public Binding(String identifier, int line, SemanticType semanticType, TypeNode typeNode, Declaration declaration) {
        this(identifier, line, semanticType, declaration);
        this.typeNode = typeNode;
    }

    public Binding(String identifier, int line, SemanticType semanticType, MethodNode methodNode, Declaration declaration) {
        this(identifier, line, semanticType, declaration);
        this.methodNode = methodNode;
    }

    public Binding(String identifier, int line, SemanticType semanticType, VariableNode variableNode, Declaration declaration) {
        this(identifier, line, semanticType, declaration);
        this.variableNode = variableNode;
    }

    private Binding(String identifier, int line, SemanticType semanticType, Declaration declaration) {
        this.identifier = identifier;
        this.line = line;
        this.semanticType = semanticType;
        this.declaration = declaration;
    }
}
