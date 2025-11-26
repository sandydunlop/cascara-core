package io.github.qishr.cascara.java.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.MethodDeclaration;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.TypeNode;
import io.github.qishr.cascara.java.model.VariableNode;

public class SymbolTable {
    private ArrayList<Scope> scopeStack = new ArrayList<>();
    private Map<ScopingConstruct,Scope> scopeCache = new HashMap<>();
    private Scope globalScope = null;
    private Scope currentScope = null;

    //
    // Defining
    //

    public Scope beginScopeCreation(ScopeKind scopeType, ScopingConstruct scopingConstruct, TypeNode scopingClass) {
        currentScope = new Scope(scopeType, scopingConstruct, scopingClass);
        scopeStack.add(currentScope);
        scopeCache.put(scopingConstruct, currentScope);
        if (globalScope == null) {
            globalScope = currentScope;
        }
        return currentScope;
    }

    String getScopeName(ScopingConstruct scopingConstruct) {
        ASTNode astNode = (ASTNode)scopingConstruct;
        MethodDeclaration method = astNode.getFirstAncestor(MethodDeclaration.class);
        if (method != null) {
            return "in method " + method.getNameString();
        } else {
            return "";
        }
    }

    public void endScopeCreation() {
        popScope();
    }

    public void addBinding(String identifier, int line, SemanticType semanticType, TypeNode typeNode, Declaration declaration) {
        currentScope.put(new Binding(identifier, line, semanticType, typeNode, declaration));
    }

    public void addBinding(String identifier, int line, SemanticType semanticType, MethodNode methodNode, Declaration declaration) {
        currentScope.put(new Binding(identifier, line, semanticType, methodNode, declaration));
    }

    public void addBinding(String identifier, int line, SemanticType semanticType, VariableNode variableNode, Declaration declaration) {
        currentScope.put(new Binding(identifier, line, semanticType, variableNode, declaration));
    }

    //
    // Retieving
    //

    public Scope currentScope() {
        return currentScope;
    }

    public int scopeDepth() {
        return scopeStack.size();
    }

    public Map<ScopingConstruct,Scope> getScopeCache() {
        return scopeCache;
    }

    /// Get the TypeNode of the class that this scope was created in
    public TypeNode getParentClass() {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            Scope scope = scopeStack.get(i);
            TypeNode container = scope.getScopingType();
            if (container != null) {
                return container;
            }
        }
        return null;
    }

    public void pushScope(ScopingConstruct scopingConstruct) {
        currentScope = scopeCache.get(scopingConstruct);
        scopeStack.add(currentScope);
    }

    public void pushGlobalScope() {
        pushScope(globalScope.getScopingConstruct());
    }

    public void popScope() {
        if (scopeStack.isEmpty()) throw new IllegalStateException("Stack is empty");
        scopeStack.remove(scopeStack.size() - 1);
        if (scopeStack.isEmpty()) {
            currentScope = null;
        } else {
            currentScope = scopeStack.getLast();
        }
    }

    public SemanticType getSemanticType(String identifier) {
        Binding binding = getBinding(identifier);
        return binding == null ? SemanticType.NONE : binding.getSemanticType();
    }

    public Binding getBinding(String identifier) {
        // Search from current scope backwards (LIFO)
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            if (scopeStack.get(i).contains(identifier)) {
                return scopeStack.get(i).get(identifier);
            }
        }
        return null;
    }

    public void recordUsage(String identifier) {
        Binding binding = getBinding(identifier);
        binding.recordUsage();
    }

    //
    //
    //

    public enum ScopeKind {
        NONE,
        GLOBAL,
        CLASS,
        METHOD
    }

    public static class Scope {
        private ScopeKind scopeKind = ScopeKind.NONE;
        private ScopingConstruct scopingConstruct = null;
        private TypeNode scopingType = null;
        private Map<String, Binding> bindings = new HashMap<>();

        public Scope(ScopeKind scopeKind, ScopingConstruct scopingConstruct, TypeNode scopingType) {
            this.scopeKind = scopeKind;
            this.scopingConstruct = scopingConstruct;
            this.scopingType = scopingType;
        }

        public ScopeKind getScopeKind() {
            return scopeKind;
        }

        public ScopingConstruct getScopingConstruct() {
            return scopingConstruct;
        }

        public TypeNode getScopingType() {
            return scopingType;
        }

        public Map<String, Binding> getBindings() {
            return bindings;
        }

        public boolean contains(String identifier) {
            return bindings.containsKey(identifier);
        }

        public Binding get(String identifier) {
            return bindings.get(identifier);
        }

        public void put(Binding binding) {
            bindings.put(binding.getIdentifier(), binding);
        }

        public int size() {
            return bindings.size();
        }
    }
}
