package io.github.qishr.cascara.java.analyzer;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.Table;
import io.github.qishr.cascara.java.analyzer.SymbolTable.Scope;
import io.github.qishr.cascara.java.analyzer.SymbolTable.ScopeKind;
import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.*;
import io.github.qishr.cascara.java.ast.expressions.*;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceMethodDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.NormalInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.names.*;
import io.github.qishr.cascara.java.ast.semantic.ConstantFieldOrVariableType;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.TypeDeclaration;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.PackageDeclaration;
import io.github.qishr.cascara.java.ast.structures.SingleModuleImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.SingleStaticImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.SingleTypeImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.StaticImportOnDemandDeclaration;
import io.github.qishr.cascara.java.ast.structures.TypeImportOnDemandDeclaration;
import io.github.qishr.cascara.java.model.AnnotationNode;
import io.github.qishr.cascara.java.model.ClassNode;
import io.github.qishr.cascara.java.model.Dependency;
import io.github.qishr.cascara.java.model.EnumNode;
import io.github.qishr.cascara.java.model.FieldNode;
import io.github.qishr.cascara.java.model.InterfaceNode;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.ModelUtil;
import io.github.qishr.cascara.java.model.NameUtil;
import io.github.qishr.cascara.java.model.ParamNode;
import io.github.qishr.cascara.java.model.RecordNode;
import io.github.qishr.cascara.java.model.SemanticModel;
import io.github.qishr.cascara.java.model.TypeNode;
import io.github.qishr.cascara.java.model.VariableNode;
import io.github.qishr.cascara.java.model.VariableTypeNode;
import io.github.qishr.cascara.java.modeling.StandardModeler;
import io.github.qishr.cascara.java.parser.Tokenizer;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;
import io.github.qishr.cascara.jreutil.JreUtil;

public class SemanticAnalyzer {
    private final Reporter reporter;
    private SemanticModel semanticModel;
    private final List<Tokenizer.Token> rawTokens;
    private List<Tokenizer.Token> newTokens;

    // Package related things
    private String packageName = null;
    private List<TypeName> importedTypes = new ArrayList<>();
    private List<PackageOrTypeName> onDemand = new ArrayList<>(); // TODO on-demand type importing

    /// Map to store the final resolution results (key: "line:column")
    private final Map<String, SemanticType> resolvedIdentifiers = new HashMap<>();

    /// The symbol table for handling scopes
    private final SymbolTable symbolTable = new SymbolTable();

    private static final Set<TokenType> JAVA_MODIFIERS = Set.of(
        TokenType.KEYWORD_PUBLIC, TokenType.KEYWORD_PROTECTED, TokenType.KEYWORD_PRIVATE,
        TokenType.KEYWORD_STATIC, TokenType.KEYWORD_ABSTRACT, TokenType.KEYWORD_FINAL,
        TokenType.KEYWORD_TRANSIENT, TokenType.KEYWORD_VOLATILE, TokenType.KEYWORD_SYNCHRONIZED,
        TokenType.KEYWORD_NATIVE, TokenType.KEYWORD_STRICTFP, TokenType.CONTEXTUAL_SEALED,
        TokenType.CONTEXTUAL_NON_SEALED
    );

    private static final Set<TokenType> JAVA_OPERATORS = Set.of(
        // Single character operators
        TokenType.EQUALS, TokenType.PLUS, TokenType.MINUS, TokenType.STAR, TokenType.SLASH,
        TokenType.PERCENT, TokenType.LESS_THAN, TokenType.GREATER_THAN, TokenType.AMPERSAND,
        TokenType.PIPE, TokenType.CARET, TokenType.TILDE, TokenType.BANG,

        // Multi-character operators
        TokenType.ARROW, TokenType.COLON_COLON, TokenType.DOT_DOT_DOT, TokenType.EQUALS_EQUALS,
        TokenType.BANG_EQUALS, TokenType.LESS_EQUALS, TokenType.GREATER_EQUALS,
        TokenType.AMPERSAND_AMPERSAND, TokenType.PIPE_PIPE, TokenType.LEFT_SHIFT,
        TokenType.RIGHT_SHIFT, TokenType.UNSIGNED_RIGHT_SHIFT, TokenType.PLUS_EQUALS,
        TokenType.MINUS_EQUALS, TokenType.STAR_EQUALS, TokenType.SLASH_EQUALS,
        TokenType.PERCENT_EQUALS, TokenType.AMPERSAND_EQUALS, TokenType.PIPE_EQUALS,
        TokenType.CARET_EQUALS, TokenType.LEFT_SHIFT_EQUALS, TokenType.RIGHT_SHIFT_EQUALS,
        TokenType.UNSIGNED_RIGHT_SHIFT_EQUALS, TokenType.PLUS_PLUS, TokenType.MINUS_MINUS
    );

    public SemanticAnalyzer(Reporter reporter, SemanticModel semanticModel, List<Tokenizer.Token> tokens) {
        this.reporter = reporter;
        this.semanticModel = semanticModel;
        this.rawTokens = tokens;
        this.newTokens = new ArrayList<>();
        this.newTokens.addAll(tokens);

        if (semanticModel.getDependency("java.lang") == null) {
            Dependency defaultPackage = loadDefaultPackage();
            semanticModel.addDependency(defaultPackage);
        }

    }

    /// Main entry point after parsing the AST
    public List<Tokenizer.Token> analyze(ASTNode root) {
        // Beware:
        // AnnotationTypeDeclaration isn't defined in JLS v25,
        // although it is mentioned in §9.6

        defineSymbols(root);
        resolveSymbols(root);
        showSymbols(root);

        List<Token> enrichedTokens = generateEnrichedTokens(this.newTokens, this.resolvedIdentifiers);
        for (Token token : enrichedTokens) {
            if (token.semtype() == SemanticType.NONE && token.type() == TokenType.IDENTIFIER) {
                reporter.reportError("Unknown semantic type on line %d: %s",
                        token.line(), token.lexeme());
            }
        }
        return enrichedTokens;
    }

    /// First pass
    private void defineSymbols(ASTNode node) {
        if (node == null) return;
        // reporter.reportDebug("NODE: %s [%s]", node, node.getClass().getSimpleName());
        SemanticType type = getSemanticType(node);

        // 1. If this is a declaration, add it to the symbol table
        if (node instanceof Declaration declaration) {
            if (declaration.getNameToken() != null) {
                defineSymbol(declaration, type);
            } else {
                reporter.reportWarning("Missing name token for %s", declaration.toString());
            }
        }

        if (node instanceof PackageDeclaration packageDeclaration) {
            packageName = packageDeclaration.getPackageName();
            setPackageSemanticType(packageDeclaration);
        }

        if (node instanceof ImportDeclaration importDeclaration) {
            handleImportDeclaration(importDeclaration);
            setPackageSemanticType(importDeclaration);
        }

        // 2. If it's supposed to create a new scope, create the new scope
        if (node instanceof ScopingConstruct scopingConstruct) {
            ScopeKind scopeKind = getScopeType(node);
            if (scopeKind == ScopeKind.CLASS && scopingConstruct instanceof Identifiable identifiable) {
                String fqn;
                String simpleName = identifiable.getNameToken().lexeme();
                TypeNode parentClass = symbolTable.getParentClass();
                if (parentClass == null) {
                    // It's a top-level class
                    fqn = packageName + "." + simpleName;
                } else {
                    // It's a nested class
                    fqn = parentClass.getName().fullyQualifiedName() + "." + simpleName;
                }
                TypeNode thisClass = semanticModel.getTypeNode(fqn);
                symbolTable.beginScopeCreation(scopeKind, scopingConstruct, thisClass);
            } else {
                symbolTable.beginScopeCreation(scopeKind, scopingConstruct, null);
                if (node instanceof CompilationUnit) {
                    addDefaultPackageToScope();
                }
            }
        }

        // 3. Deal with all child nodes
        for (ASTNode child : node.getChildren()) {
            defineSymbols(child);
        }

        // 4. If it created a new scope, go back to the previous one
        if (node instanceof ScopingConstruct) {
            symbolTable.endScopeCreation();
        }
    }

    /// Second pass
    private void resolveSymbols(ASTNode node) {
        // 1. If this node defines a scope, retrieve it from the
        // cache and push it to the current scope stack.
        if (node instanceof ScopingConstruct scopingConstruct) {
            symbolTable.pushScope(scopingConstruct);
        }

        handleSymbolUsage(node);
        if (node instanceof Name name) {
            node = resolveName(name);
        }

        List<ASTNode> children = node.getChildren();
        for (int i = 0; i < children.size(); i++) {
            resolveSymbols(children.get(i));
        }

        // 3. If this node defined a scope, pop it from the scope stack.
        if (node instanceof ScopingConstruct) {
            symbolTable.popScope();
        }
    }

    private void showSymbols(ASTNode node) {
        // 1. If this node defines a scope, retrieve it from the
        // cache and push it to the current scope stack.
        if (node instanceof ScopingConstruct scopingConstruct) {
            symbolTable.pushScope(scopingConstruct);
        }
        for (ASTNode child : node.getChildren()) {
            showSymbols(child);
        }
        if (node instanceof ScopingConstruct) {
            showSymbolTable();
            symbolTable.popScope();
        }
    }

    //
    // Symbol Table Operations
    //

    private void handleSymbolUsage(ASTNode node) {
        if (node instanceof Identifiable identifiableNode) {
            if (identifiableNode.getNameToken().lexeme().equals("draw")) {
                // reporter.reportDebug("Line %d ID: %s",
                //         identifiableNode.getNameToken().line(),
                //         identifiableNode.getNameToken().lexeme());
                // System.out.println();
            }
            Token nameToken = identifiableNode.getNameToken();
            if (nameToken != null) {
                SemanticType semanticType = getSemanticType(node);
                if (semanticType != SemanticType.NONE) {
                    resolveSymbol(nameToken, semanticType);
                } else {
                    resolveSymbol(nameToken);
                }
                recordUsage(nameToken.lexeme());
            } else {
                // This should never happen
                reporter.reportDebug("No name token for %s", node.getClass().getSimpleName());
            }
        }
    }

    private void recordUsage(String identifier) {
        Binding binding = symbolTable.getBinding(identifier);
        if (binding != null) {
            TypeNode typeNode = binding.getTypeNode();
            if (typeNode != null) {
                String typeName = typeNode.getName().simpleName();
                symbolTable.recordUsage(typeName);
            }
        }
    }

    private void resolveSymbol(Tokenizer.Token token) {
        if (token.type() != Tokenizer.TokenType.IDENTIFIER) return;

        String identifier = token.lexeme();
        SemanticType resolvedType;

        // Search from current scope backwards (LIFO)
        resolvedType = symbolTable.getSemanticType(identifier);

        // Record the semantic type for later token enrichment
        if (resolvedType != SemanticType.NONE) {
            defineTokenSemanticType(token, resolvedType);
            recordUsage(token.lexeme());
        }
    }

    /// Called when processing a declaration in the FIRST PASS
    private void defineSymbol(Declaration declaration, SemanticType semanticType) {
        Tokenizer.Token token = declaration.getNameToken();
        if (semanticType == SemanticType.NONE) {
            reporter.reportWarning("Ignoring 1: %s", token.lexeme());
            return;
        }
        if (token.type() != Tokenizer.TokenType.IDENTIFIER) {
            reporter.reportWarning("Ignoring 2: %s", token.lexeme());
            return;
        }

        if (token.lexeme().equals("draw") && declaration instanceof Identifiable identifiable) {
            // reporter.reportDebug("Line %d ID: %s",
            //         identifiable.getNameToken().line(),
            //         identifiable.getNameToken().lexeme());
            // System.out.println();
        }

        // Add the symbol to the current scope
        if (declaration instanceof VariableDeclarator variableDeclarator) {
            // reporter.reportDebug("Variable: %s", token.lexeme());
            TypeNode parentClass = symbolTable.getParentClass();
            VariableNode variableNode = null;
            if (parentClass != null) {
                variableNode = parentClass.getField(token.lexeme());
                // TODO:  Does this need done in second pass?
                if (variableNode != null) {
                    VariableTypeNode vt = variableNode.getType();
                    String variableType = vt.getRawTypeName();
                    if (!variableType.contains(".")) { //TODO: This isn't a suitable check for qualified name
                        Binding binding = symbolTable.getBinding(variableType);
                        if (binding != null) {
                            TypeNode tn = binding.getTypeNode();
                            vt.setFullTypeName(tn.getName().fullyQualifiedName());
                            // TODO: Did that need re-parsed?
                            // System.out.println();
                        } else {
                            reporter.reportError("Type lookup failed for %s", variableType);
                        }
                    }
                    reporter.reportTrace("Field: %s", token.lexeme());
                }
            }
            if (variableNode == null) {
                reporter.reportTrace("Variable: %s", token.lexeme());
                variableNode = createFieldNode(variableDeclarator);
            }
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, variableNode, declaration);
        } else if (declaration instanceof RecordComponent recordComponent) {
            TypeNode parentClass = symbolTable.getParentClass();
            FieldNode componentNode = parentClass.getField(recordComponent.getNameString());
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, componentNode, declaration);
        } else if (declaration instanceof FormalParameter formalParameter) {
            FormalParameterList fpl = (FormalParameterList)formalParameter.getParent();
            TypeNode parentClass = symbolTable.getParentClass();
            MethodNode methodOrConstructorNode;
            ASTNode methodOrConstructor = fpl.getParent();
            if (methodOrConstructor instanceof MethodDeclarator methodDeclarator) {
                String methodName = methodDeclarator.getNameString();
                methodOrConstructorNode = getClassMethod(parentClass, methodName);
            } else {
                methodOrConstructorNode = getClassConstructor(parentClass);
            }

            // TODO: The type anme of this is unqualified. Fix it.
            ParamNode paramNode = methodOrConstructorNode.getParam(formalParameter.getNameString());
            VariableTypeNode vt = paramNode.getType();
            String paramType = vt.getFullTypeName();
            Binding typeBinding = symbolTable.getBinding(paramType);
            if (typeBinding != null) {
                TypeNode typeNode = typeBinding.getTypeNode();
                vt.setFullTypeName(typeNode.getName().fullyQualifiedName());
            }
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, paramNode, declaration);
        } else if (declaration instanceof MethodDeclaration) {
            reporter.reportTrace("Method: %s", token.lexeme());
            TypeNode parentClass = symbolTable.getParentClass();
            MethodNode methodNode = getClassMethod(parentClass, token.lexeme());
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, methodNode, declaration);
        }  else if (declaration instanceof InterfaceMethodDeclaration) {
            reporter.reportTrace("Interface Method: %s", token.lexeme());
            TypeNode parentClass = symbolTable.getParentClass();
            MethodNode methodNode = getClassMethod(parentClass, token.lexeme());
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, methodNode, declaration);
        } else if (declaration instanceof TypeDeclaration) {
            reporter.reportTrace("Type: %s", token.lexeme());
            String fqn = packageName + "." + token.lexeme();
            TypeNode typeNode = semanticModel.getTypeNode(fqn);
            if (token.semtype() == SemanticType.NONE) {
                if (typeNode.isInterface()) {
                    semanticType = SemanticType.INTERFACE_NAME;
                    // replaceToken(token, SemanticType.INTERFACE_NAME); // TODO is this  needed here?
                } else if (typeNode.isEnum()) {
                    semanticType = SemanticType.CLASS_NAME;
                    // replaceToken(token, SemanticType.CLASS_NAME); //TODO: Enum name?
                } else if (typeNode.isRecord()) {
                    semanticType = SemanticType.CLASS_NAME; //TODO: RECORD_NAME?
                    // replaceToken(token, SemanticType.CLASS_NAME);
                } else if (typeNode.isAnnotation()) {
                    semanticType = SemanticType.ANNOTATION_NAME;
                    // replaceToken(token, SemanticType.ANNOTATION_NAME);
                } else if (typeNode.isClass()) {
                    semanticType = SemanticType.CLASS_NAME;
                    // replaceToken(token, SemanticType.CLASS_NAME);
                } else {
                    // WTF is it then?
                    reporter.reportError("Can't determine what this is: %s", fqn);
                }
            }
            symbolTable.addBinding(token.lexeme(), token.line(), semanticType, typeNode, declaration);
        } else {
            reporter.reportError("Unhandled: %s", declaration.getClass().getSimpleName());
        }

        // Record the semantic type for later token enrichment
        defineTokenSemanticType(token, semanticType);

        // reporter.reportDebug("SET    : " + semanticType + " " + token.lexeme());
    }

    // TODO: This needs to use full signature
    private MethodNode getClassConstructor(TypeNode typeNode) {
        return typeNode.getConstructors().getFirst();
    }

    // TODO: This needs to use full signature
    private MethodNode getClassMethod(TypeNode typeNode, String methodName) {
        for (MethodNode method : typeNode.getMethods()) {
            if (method.getName().simpleName().equals(methodName)) {
                return method;
            }
        }
        return null;
    }

    private FieldNode createFieldNode(VariableDeclarator declarator) {
        // TODO: Generics and arrays
        ConstantFieldOrVariableType type = declarator.getDeclaredType();
        String identifier = declarator.getVariableDeclaratorId().toString();
        VariableTypeNode variableTypeNode;
        if (type.getUnannType() != null) {

            // TODO: If type is defined in this CU, prepend this CU's package name.
            //       If it's imported, get the package name from the import.
            String variableType = type.getUnannType().toString();
            Binding binding = symbolTable.getBinding(variableType);
            TypeNode typeNode = binding.getTypeNode();

            // TODO: Here the TypeNode might have a simpleName only.
            // We need to qualify it here based on the current scope
            if (typeNode == null) {
                reporter.reportError("TypeNode lookup failed for %s", variableType);
                // TODO: Exception
            }

            String fullTypeName = typeNode.getName().fullyQualifiedName();
            variableTypeNode = ModelUtil.parseVariableType(fullTypeName);
        } else {
            variableTypeNode = new VariableTypeNode();
        }
        variableTypeNode.setVar(type.isVar());
        variableTypeNode.setIdentifier(identifier);
        return new FieldNode(variableTypeNode, NameUtil.createMemberName(identifier));
    }

    //
    // Packages and imports
    //

    private Dependency loadDefaultPackage() {
        final String packageName = "java.lang";
        Dependency dependency = new Dependency(packageName);
        StandardModeler standardModeler = new StandardModeler();
        List<Class<?>> jreClasses = JreUtil.loadClassesFromPackage(packageName);
        for (Class<?> jreClass : jreClasses) {
            dependency.addType(standardModeler.modelClass(jreClass));
        }
        return dependency;
    }

    private void addDefaultPackageToScope() {
        Dependency defaultPackage = semanticModel.getDependency("java.lang");
        for (TypeNode typeNode : defaultPackage.getTypes()) {
            String simpleName = typeNode.getName().simpleName();
            symbolTable.addBinding(simpleName, -1, getSemanticType(typeNode), typeNode, null);
        }
    }

    private void setPackageSemanticType(PackageDeclaration packageDeclaration) {
        SemanticType semanticType = SemanticType.PACKAGE_NAME;
        for (Identifier identifier : packageDeclaration.getIdentifiers()) {
            Token token = identifier.getNameToken();
            replaceToken(token, semanticType);
        }
    }

    private void setPackageSemanticType(ImportDeclaration importDeclaration) {
        setPackageSemanticType(importDeclaration.getSingleModuleImportDeclaration());
        setPackageSemanticType(importDeclaration.getSingleStaticImportDeclaration());
        setPackageSemanticType(importDeclaration.getSingleTypeImportDeclaration());
        setPackageSemanticType(importDeclaration.getStaticImportOnDemandDeclaration());
        setPackageSemanticType(importDeclaration.getTypeImportOnDemandDeclaration());
    }

    private void setPackageSemanticType(SingleModuleImportDeclaration declaration) {
        if (declaration != null) {

        }
    }

    private void setPackageSemanticType(SingleStaticImportDeclaration declaration) {
        if (declaration != null) {

        }
    }

    private void setPackageSemanticType(SingleTypeImportDeclaration declaration) {
        if (declaration != null) {
            TypeName typeName = declaration.getTypeName();
            TypeNode typeNode = semanticModel.getTypeNode(typeName.getQualifiedName());
            if (typeNode != null) {
                int lastPackageindex = typeNode.getName().packageComponentCount();
                int index = typeNode.getName().componentCount() - 1;
                // PackageOrTypeName component = typeName.getPackageOrTypeName();
                Name component = typeName.getPackageOrTypeName();
                while (component != null) {
                    if (index <= lastPackageindex) {
                        Token nameToken = component.getNameToken();
                        nameToken = replaceToken(nameToken, SemanticType.PACKAGE_NAME);
                        nameToken = nameToken;
                        // TODO: Replace component's NameToke
                    }
                    // component = component.getPackageOrTypeName();
                    component = component.getName();
                    index--;
                }
            } else {
                reporter.reportError("TypeNode lookup failed for %s", typeName);
            }
        }
    }

    private void setPackageSemanticType(StaticImportOnDemandDeclaration declaration) {
        if (declaration != null) {

        }
    }

    private void setPackageSemanticType(TypeImportOnDemandDeclaration declaration) {
        if (declaration != null) {

        }
    }

    private void handleImportDeclaration(ImportDeclaration importDeclaration) {
        SingleTypeImportDeclaration singleType = importDeclaration.getSingleTypeImportDeclaration();
        if (singleType != null) {
            TypeName typeName = singleType.getTypeName();
            importedTypes.add(typeName);
            String typeString = typeName.getQualifiedName();
            TypeNode typeNode = semanticModel.getTypeNode(typeString);
            if (typeNode == null) {
                // It's an external package
                typeNode = loadType(typeString);
            }
        }
        // TODO: on-demand, etc
        // System.out.println(importDeclaration);
    }

    private TypeNode loadType(String typeString) {
        Class<?> jreType = JreUtil.loadClass(typeString);
        StandardModeler standardModeler = new StandardModeler();
        TypeNode typeNode = standardModeler.modelType(jreType);
        String pkgName = jreType.getPackageName();
        Dependency dependency = semanticModel.getDependency(pkgName);
        if (dependency == null) {
            dependency = new Dependency(pkgName);
            semanticModel.addDependency(dependency);
        }
        if (!dependency.contains(typeNode)) {
            dependency.addType(typeNode);
        }
        SemanticType semanticType = getSemanticType(typeNode);
        symbolTable.addBinding(typeNode.getName().simpleName(), -1, semanticType, typeNode, null);
        return typeNode;
    }

    //
    // Basic Token Enrichment
    //

    private void resolveSymbol(Tokenizer.Token token, SemanticType predeterminedType) {
        if (token.type() != Tokenizer.TokenType.IDENTIFIER) return;
        defineTokenSemanticType(token, predeterminedType);
    }

    public List<Tokenizer.Token> generateEnrichedTokens(List<Tokenizer.Token> rawTokens, Map<String, SemanticType> resolvedIdentifiers) {
        List<Tokenizer.Token> enrichedTokens = new ArrayList<>(rawTokens.size());
        for (Tokenizer.Token original : rawTokens) {
            String key = original.line() + ":" + original.column();
            if (original.type() == TokenType.IDENTIFIER) {
                SemanticType semtype = resolvedIdentifiers.get(key);
                if (semtype != null) {
                    enrichedTokens.add(enrichToken(original, semtype));
                } else {
                    enrichedTokens.add(original);
                }
            } else {
                SemanticType semanticType = categorizeToken(original.type());
                Tokenizer.Token enrichedToken = enrichToken(original, semanticType);
                enrichedTokens.add(enrichedToken);
            }
        }
        return enrichedTokens;
    }

    private SemanticType categorizeToken(TokenType tokenType) {
        if (JAVA_MODIFIERS.contains(tokenType)) {
            return SemanticType.MODIFIER;
        } else if (JAVA_OPERATORS.contains(tokenType)) {
            return SemanticType.OPERATOR;
        } else {
            return SemanticType.NONE;
        }
    }

    public Tokenizer.Token enrichToken(Tokenizer.Token original, SemanticType semtype) {
        return new Tokenizer.Token(
            original.type(),
            original.lexeme(),
            original.startIndex(),
            original.line(),
            original.column(),
            semtype
        );
    }

    private Token replaceToken(Token oldToken, SemanticType semtype) {
        Token newToken = new Token(oldToken.type(), oldToken.lexeme(),
                oldToken.startIndex(), oldToken.line(), oldToken.column(), semtype);
        int index = newTokens.indexOf(oldToken);
        if (index > -1) {
            newTokens.remove(index);
            newTokens.add(index, newToken);
        } else {
            reporter.reportError("token lookup failed for %s", oldToken.lexeme());
        }
        return newToken;
    }

    //
    // Name resolution
    //

    private ASTNode resolveName(Name name) {
        if (name instanceof AmbiguousName ambiguousName) {
            return resolveAmbiguousName(ambiguousName);
        } else if (name instanceof TypeName typeName) {
            return resolveTypeName(typeName);
        }
        return (ASTNode)name;
    }

    private ASTNode resolveAmbiguousName(AmbiguousName ambiguousName) {
        ASTNode returnNode = ambiguousName;
        Binding binding = symbolTable.getBinding(ambiguousName.getNameString());
        if (binding != null) {
            if (binding.getTypeNode() != null) {
                ExpressionName expressionName = convertName(ExpressionName.class, ambiguousName);
                replaceAmbiguousName(ambiguousName, expressionName);
                returnNode = expressionName;
                reporter.reportDebug("Changed AmbiguousName of type %s to ExpressionName",
                        ambiguousName.getNameString());

                ASTNode parent = expressionName.getParent();
                if (parent instanceof ExpressionName parentExpression) {
                    // Set SemanticType of right hand side of dot in parent expression...
                    Identifier identifier = parentExpression.getIdentifier();
                    resolveSymbol(identifier.getNameToken(), SemanticType.FIELD_NAME);
                }
            } else if (binding.getVariableNode() != null) {
                ExpressionName expressionName = convertName(ExpressionName.class, ambiguousName);
                replaceAmbiguousName(ambiguousName, expressionName);
                returnNode = expressionName;
                reporter.reportDebug("Changed AmbiguousName of variable %s to ExpressionName",
                        ambiguousName.getNameString());
            } else {
                reporter.reportDebug("Unresolved AmbiguousName %s at line %d",
                        ambiguousName.getNameString(),
                        ambiguousName.getNameToken().line());
            }
        }
        return returnNode;
    }

    private ASTNode resolveTypeName(TypeName typeName) {
        if (!typeName.isQualified()) return typeName;

        ASTNode returnNode = typeName;
        Name prefix = typeName.getQualifier();
        Identifier identifier = typeName.getIdentifier();
        String prefixNameStr = prefix.getQualifiedName();
        SemanticType prefixType = symbolTable.getSemanticType(prefixNameStr);

        if (prefixType == SemanticType.CLASS_NAME) {
            // Static field access
            ASTNode parent = typeName.getParent();
            if (parent instanceof PrimaryNoNewArray parentPnna) {
                // TypeName newTypeName = convertName(TypeName.class, prefix);
                TypeIdentifier ti = new TypeIdentifier();
                ti.setIdentifier(prefix.getIdentifier());
                TypeName newTypeName = new TypeName();
                newTypeName.setTypeIdentifier(ti);
                FieldAccess fieldAccess = new FieldAccess();
                fieldAccess.setTypeName(newTypeName);
                fieldAccess.setIdentifier(identifier);
                parentPnna.getChildren().clear();
                parentPnna.setTypeName(null);
                parentPnna.setFieldAccess(fieldAccess);
                reporter.reportDebug("Changed TypeName %s to FieldAccess", typeName.getNameString());
                returnNode = fieldAccess;
            }
            defineTokenSemanticType(identifier.getNameToken(), SemanticType.ENUM_CONSTANT);
        }
        return returnNode;
    }

    private void replaceAmbiguousName(AmbiguousName ambiguousName, Name replacement) {
        ASTNode parent = ambiguousName.getParent();
        if (parent instanceof Name parentName) {
            parentName.getChildren().remove(ambiguousName);
            parentName.setName(replacement);
        }
    }

    public <T extends Name> T convertName(Class<T> type, Name name) {
        try {
            T newName = type.getConstructor().newInstance();
            newName.setIdentifier(name.getIdentifier());
            if (name.getName() != null) {
                newName.setName(convertName(type, name.getName()));
            }
            return newName;
        } catch (Exception _) {
            // The upper bound of the generic type means this shouldn't fail
            return null;
        }
    }

    // Helper to update the token map (you already have this logic)
    private void defineTokenSemanticType(Tokenizer.Token token, SemanticType type) {
        reporter.reportTrace("DEFINE: %s %s", type, token.lexeme());
        String key = token.line() + ":" + token.column();
        this.resolvedIdentifiers.put(key, type);
    }

    //
    // Look-up methods
    //

    private ScopeKind getScopeType(ASTNode node) {
        if (node instanceof CompilationUnit) {
            return ScopeKind.GLOBAL;
        } else if (node instanceof NormalClassDeclaration) {
            return ScopeKind.CLASS;
        } else if (node instanceof NormalInterfaceDeclaration) {
            return ScopeKind.CLASS;
        } else if (node instanceof AnnotationInterfaceDeclaration) {
            return ScopeKind.CLASS;
        } else if (node instanceof EnumDeclaration) {
            return ScopeKind.CLASS;
        } else if (node instanceof RecordDeclaration) {
            return ScopeKind.CLASS;
        } else if (node instanceof MethodDeclaration) {
            return ScopeKind.METHOD;
        } else if (node instanceof ConstructorDeclaration) {
            return ScopeKind.METHOD;
        } else {
            return ScopeKind.METHOD;
        }
    }

    private SemanticType getSemanticType(TypeNode typeNode) {
        if (typeNode instanceof EnumNode) {
            return SemanticType.CLASS_NAME;
        } else if (typeNode instanceof AnnotationNode) {
            return SemanticType.ANNOTATION_NAME;
        } else if (typeNode instanceof RecordNode) {
            return SemanticType.CLASS_NAME;
        } else if (typeNode instanceof InterfaceNode) {
            return SemanticType.INTERFACE_NAME;
        } else if (typeNode instanceof ClassNode) {
            return SemanticType.CLASS_NAME;
        } else {
            return SemanticType.NONE;
        }
    }

    private SemanticType getSemanticType(ASTNode node) {
        // if (node instanceof Identifiable id && id.getNameString().startsWith("Color.GREEN")) {
        //     System.out.println();
        //     // TODO: This needs changed to use FieldAccess for static field access.
        // }
        // These are used for scope creation...
        if (node instanceof NormalClassDeclaration) {
            return SemanticType.CLASS_NAME;
        } else if (node instanceof NormalInterfaceDeclaration) {
            return SemanticType.INTERFACE_NAME;
        } else if (node instanceof AnnotationInterfaceDeclaration) {
            return SemanticType.ANNOTATION_NAME;
        } else if (node instanceof EnumDeclaration) {
            return SemanticType.CLASS_NAME;
        } else if (node instanceof RecordDeclaration) {
            return SemanticType.CLASS_NAME;
        } else if (node instanceof MethodDeclaration) {
            return SemanticType.METHOD_NAME;
        } else if (node instanceof InterfaceMethodDeclaration) {
            return SemanticType.METHOD_NAME;
        } else if (node instanceof ConstructorDeclaration) {
            return SemanticType.CONSTRUCTOR_NAME;

        // These aren't used for scope creation...
        // TODO: This needs expanded to handle more node types
        } else if (node instanceof FormalParameter) {
            return SemanticType.PARAMETER_NAME;
        } else if (node instanceof EnumConstant) {
            return SemanticType.ENUM_CONSTANT;
        } else if (node instanceof RecordComponent) {
            return SemanticType.FIELD_NAME;
        } else if (node instanceof MethodInvocation) {
            return SemanticType.METHOD_NAME;
        } else if (node instanceof TypeName) {
            return SemanticType.TYPE_REFERENCE;
        } else if (node instanceof FieldAccess) {
            return SemanticType.FIELD_NAME;
        } else if (node instanceof PackageName) {
            return SemanticType.PACKAGE_NAME;
        } else if (node instanceof VariableDeclarator) {
            ScopeKind scopeType = symbolTable.currentScope().getScopeKind();
            if (scopeType == ScopeKind.CLASS) {
                return SemanticType.FIELD_NAME;
            } else if (scopeType == ScopeKind.METHOD) {
                return SemanticType.LOCAL_VARIABLE_NAME;
            }
        }
        // Should not happen, but safe exit
        // TODO: But it happens all the time!
        // reporter.reportWarning("Unable to determine SemanticType: %s", node);
        return SemanticType.NONE;
    }

    //
    //
    //

    private void showSymbolTable() {
        Scope scope = symbolTable.currentScope();
        if (scope.getBindings().isEmpty()) {
            return;
        }
        int skipped = 0;
        Table table = new Table();
        table.addColumn("ID");
        table.addColumn("Line");
        table.addColumn("Semantic Type");
        table.addColumn("Java Type");
        Map<String, Binding>  bindings = scope.getBindings();
        for (Binding binding : bindings.values()) {
            String typeName = "";
            int usageCount = 0;
            if (binding.getVariableNode() != null) {
                VariableTypeNode variableType = binding.getVariableNode().getType();
                typeName = variableType.getFullTypeName();
                String rawTypeName = variableType.getRawTypeName();
                Binding typeBinding = symbolTable.getBinding(rawTypeName);
                if (typeBinding != null) {
                    usageCount = typeBinding.getUsageCount();
                }
            } else if (binding.getTypeNode() != null) {
                typeName = binding.getTypeNode().getName().fullyQualifiedName();
                usageCount = binding.getUsageCount();
            } else if (binding.getMethodNode() != null) {
                VariableTypeNode returnType = binding.getMethodNode().getReturnType();
                typeName = returnType.getFullTypeName();
            }
            if (!typeName.startsWith("java.") || usageCount > 0){
                table.addRow(binding.getIdentifier(),
                            binding.getLine() > -1 ? Integer.toString(binding.getLine()) : "",
                            binding.getSemanticType().toString(),
                            typeName);
            } else {
                skipped++;
            }
        }
        ScopingConstruct scopingConstruct = scope.getScopingConstruct();
        String creator = scopingConstruct.getClass().getSimpleName();
        ASTNode astNode = (ASTNode)scopingConstruct;
        MethodDeclaration method = astNode.getFirstAncestor(MethodDeclaration.class);
        if (method != null) {
            creator += " in method \"" + method.getNameString() + "\"";
        } else if (scopingConstruct instanceof Identifiable identifiable) {
            creator += " \"" + identifiable.toString() + "\"";
        }
        reporter.reportDebug("Identifiers in scope created by %s", creator);
        StringWriter writer = new StringWriter();
        try {
            table.render(writer);
            String[] lines = writer.toString().split("\\n");
            for (String line : lines) {
                reporter.reportDebug(line);
            }
            if (skipped > 0) {
                reporter.reportDebug("(Hidden %d unused java.lang %s)",
                        skipped, skipped > 1 ? "classes" : "class");
            }
            reporter.reportDebug("");
        } catch (IOException e) {
            reporter.reportError("Error rending table: %s", e.getMessage());
        }
    }
}