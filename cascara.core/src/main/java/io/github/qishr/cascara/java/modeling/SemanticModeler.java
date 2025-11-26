package io.github.qishr.cascara.java.modeling;

import java.io.File;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassBody;
import io.github.qishr.cascara.java.ast.classes.ClassBodyDeclaration;
import io.github.qishr.cascara.java.ast.classes.ClassMemberDeclaration;
import io.github.qishr.cascara.java.ast.classes.ConstructorDeclaration;
import io.github.qishr.cascara.java.ast.classes.ConstructorModifier;
import io.github.qishr.cascara.java.ast.classes.EnumBody;
import io.github.qishr.cascara.java.ast.classes.EnumConstant;
import io.github.qishr.cascara.java.ast.classes.EnumConstantList;
import io.github.qishr.cascara.java.ast.classes.EnumDeclaration;
import io.github.qishr.cascara.java.ast.classes.FieldDeclaration;
import io.github.qishr.cascara.java.ast.classes.FormalParameter;
import io.github.qishr.cascara.java.ast.classes.FormalParameterList;
import io.github.qishr.cascara.java.ast.classes.MethodDeclaration;
import io.github.qishr.cascara.java.ast.classes.MethodModifier;
import io.github.qishr.cascara.java.ast.classes.NormalClassDeclaration;
import io.github.qishr.cascara.java.ast.classes.RecordComponent;
import io.github.qishr.cascara.java.ast.classes.RecordComponentList;
import io.github.qishr.cascara.java.ast.classes.RecordDeclaration;
import io.github.qishr.cascara.java.ast.classes.RecordHeader;
import io.github.qishr.cascara.java.ast.classes.VariableDeclarator;
import io.github.qishr.cascara.java.ast.expressions.MethodInvocation;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.NormalInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.lexical.Literal;
import io.github.qishr.cascara.java.ast.lexical.StringLiteral;
import io.github.qishr.cascara.java.ast.names.ModuleName;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.TypeDeclaration;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ModularCompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ModuleDeclaration;
import io.github.qishr.cascara.java.ast.structures.ModuleDirective;
import io.github.qishr.cascara.java.ast.structures.OrdinaryCompilationUnit;
import io.github.qishr.cascara.java.ast.structures.PackageDeclaration;
import io.github.qishr.cascara.java.ast.structures.TopLevelClassOrInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.types.ElementValue;
import io.github.qishr.cascara.java.ast.types.SingleElementAnnotation;
import io.github.qishr.cascara.java.model.AnnotationElement;
import io.github.qishr.cascara.java.model.AnnotationNode;
import io.github.qishr.cascara.java.model.AppliedAnnotationNode;
import io.github.qishr.cascara.java.model.ClassNode;
import io.github.qishr.cascara.java.model.Dependency;
import io.github.qishr.cascara.java.model.DirectiveNode;
import io.github.qishr.cascara.java.model.EnumNode;
import io.github.qishr.cascara.java.model.FieldNode;
import io.github.qishr.cascara.java.model.InterfaceNode;
import io.github.qishr.cascara.java.model.JlsName;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.ModelUtil;
import io.github.qishr.cascara.java.model.Modifier;
import io.github.qishr.cascara.java.model.ModuleNode;
import io.github.qishr.cascara.java.model.NameUtil;
import io.github.qishr.cascara.java.model.PackageNode;
import io.github.qishr.cascara.java.model.PackageReference;
import io.github.qishr.cascara.java.model.ParamNode;
import io.github.qishr.cascara.java.model.RecordNode;
import io.github.qishr.cascara.java.model.Reference;
import io.github.qishr.cascara.java.model.SemanticModel;
import io.github.qishr.cascara.java.model.TypeNode;
import io.github.qishr.cascara.java.model.VariableTypeNode;
import io.github.qishr.cascara.jreutil.JreUtil;

public class SemanticModeler implements Modeler<ModuleDeclaration, PackageDeclaration, NormalClassDeclaration, VariableDeclarator, MethodDeclaration, FormalParameter> {
    private String currentModuleName = null;
    private JlsName currentPackageName = null;
    private JlsName currentTypeName = null;
    private PackageNode packageNode;
    SemanticModel semanticModel;
    private String sourceFileName;

    public void setModuleName(String moduleName) {
        currentModuleName = moduleName;
    }

    public void model(CompilationUnit cu, String sourcePath, SemanticModel semanticModel) {
        if (cu == null) {
            return;
        }
        this.sourceFileName = sourcePath;
        this.semanticModel = semanticModel;
        ModularCompilationUnit modularUnit = cu.getModularCompilationUnit();
        OrdinaryCompilationUnit ordinaryUnit = cu.getOrdinaryCompilationUnit();
        if (modularUnit != null) {
            ModuleDeclaration moduleDeclaration = modularUnit.getModuleDeclaration();
            ModuleNode moduleNode = modelModule(moduleDeclaration);
            semanticModel.addModule(moduleNode);
        } else if (ordinaryUnit != null) {
            PackageDeclaration packageDeclaration = ordinaryUnit.getPackageDeclaration();
            addPackageToModel(packageDeclaration);
            for (TopLevelClassOrInterfaceDeclaration topLevel : ordinaryUnit.getTopLevelClassOrInterfaceDeclarations()) {
                addToModel(topLevel.getDeclaration());
            }
            // TODO: Classes not in a package
        }
        // arrangePackagesInModules();
    }

    private void addPackageToModel(PackageDeclaration packageDeclaration) {
        String packageName = packageDeclaration.getPackageName();
        packageNode = semanticModel.getPackageNode(packageName);
        if (packageNode == null) {
            packageNode = modelPackage(packageDeclaration);
            semanticModel.addPackage(packageNode);
            // TODO: Package needs added to module too
        }
    }

    private void addToModel(Declaration declaration) {
        // SemanticModeler modeler = new SemanticModeler();
        if (declaration instanceof NormalClassDeclaration classDeclaration) {
            ClassNode node = modelClass(classDeclaration);
            semanticModel.addType(node);
            packageNode.addType(node);
        } else if (declaration instanceof EnumDeclaration enumDeclaration) {
            EnumNode node = modelEnum(enumDeclaration);
            semanticModel.addType(node);
            packageNode.addType(node);
        } else if (declaration instanceof RecordDeclaration recordDeclaration) {
            RecordNode node = modelRecord(recordDeclaration);
            semanticModel.addType(node);
            packageNode.addType(node);
        } else if (declaration instanceof AnnotationInterfaceDeclaration annotationDeclaration) {
            AnnotationNode node = modelAnnotation(annotationDeclaration);
            semanticModel.addType(node);
            packageNode.addType(node);
        } else if (declaration instanceof NormalInterfaceDeclaration interfaceDeclaration) {
            InterfaceNode node = modelInterface(interfaceDeclaration);
            semanticModel.addType(node);
            packageNode.addType(node);
        }
    }


    @Override
    public ModuleNode modelModule(ModuleDeclaration m) {
        ModuleNode moduleNode = new ModuleNode(m.getNameString());
        moduleNode.setHasModuleInfo(true);

        if (sourceFileName != null && sourceFileName.endsWith("module-info.java")) {
            moduleNode.setSourcePath(sourceFileName);
        }

        for (ModuleDirective directive : m.getModuleDirectiveList()) {
            DirectiveNode directiveNode = new DirectiveNode();
            if (directive.getRequiresModuleName() != null) {
                directiveNode.setKind(DirectiveNode.Kind.REQUIRES);
                directiveNode.setName(directive.getRequiresModuleName().getNameString());
            } else if (directive.getExportsPackageName() != null) {
                directiveNode.setKind(DirectiveNode.Kind.EXPORTS);
                directiveNode.setName(directive.getExportsPackageName().getNameString());
                for (ModuleName moduleName : directive.getToModuleList()) {
                    directiveNode.addModule(moduleName.getNameString());
                }
            } else if (directive.getOpensModuleName() != null) {
                directiveNode.setKind(DirectiveNode.Kind.OPENS);
                PackageReference packageReference = new PackageReference(directive.getOpensPackageName().getNameString());
                directiveNode.setPackageReference(packageReference);
                for (ModuleName moduleName : directive.getToModuleList()) {
                    directiveNode.addModule(moduleName.getNameString());
                }
            } else if (directive.getUsesTypeName() != null) {
                directiveNode.setKind(DirectiveNode.Kind.USES);
                JlsName typeName = NameUtil.createTypeName(directive.getUsesTypeName().getNameString());
                Reference typeReference = new Reference();
                typeReference.setName(typeName);
            } else if (directive.getProvidesTypeName() != null) {
                directiveNode.setKind(DirectiveNode.Kind.PROVIDES);
                JlsName typeName = NameUtil.createTypeName(directive.getProvidesTypeName().getNameString());
                Reference typeReference = new Reference();
                typeReference.setName(typeName);
                for (TypeName withTypeName : directive.getWithTypeNameList()) {
                    typeName = NameUtil.createTypeName(withTypeName.getNameString());
                    typeReference = new Reference();
                    typeReference.setName(typeName);
                    directiveNode.addTypeName(typeReference);
                }
            }
            moduleNode.addDirective(directiveNode);
        }

        for (Annotation annotation : m.getAnnotationList()) {
            // TODO: Annotation details
            JlsName annotationTypeName = NameUtil.createTypeName(annotation.getTypeName().getNameString());
            AppliedAnnotationNode aan = new AppliedAnnotationNode(annotationTypeName);
            moduleNode.addAppliedAnnotationNode(aan);
        }

        // TODO: Constant values contained in this module
        // TODO: Packages contained in this module
        return moduleNode;
    }

    @Override
    public PackageNode modelPackage(PackageDeclaration p) {
        currentPackageName = NameUtil.createPackageName(p.getPackageName());
        PackageNode packageNode = new PackageNode(currentPackageName);
        packageNode.setModuleName(currentModuleName);
        if (sourceFileName != null && sourceFileName.endsWith("package-info.java")) {
            packageNode.setSourcePath(sourceFileName);
        }
        return packageNode;
    }

    @Override
    public ClassNode modelClass(NormalClassDeclaration type) {
        JlsName packageName = getPackageName(type);
        currentTypeName = NameUtil.createTypeName(packageName, type.getNameToken().lexeme());
        ClassNode classNode = new ClassNode(currentTypeName);
        classNode.setSourcePath(sourceFileName);

        ClassBody classBody = type.getClassBody();
        for (ClassBodyDeclaration decl : classBody.getClassBodyDeclarations()) {
            ConstructorDeclaration constructorDecl = decl.getConstructorDeclaration();
            if (constructorDecl != null) {
                MethodNode constructorNode = modelConstructor(constructorDecl, classNode);
                classNode.addConstructor(constructorNode);
            }
            ClassMemberDeclaration member = decl.getClassMemberDeclaration();
            if (member != null) {
                MethodDeclaration methodDecl = member.getMethodDeclaration();
                if (methodDecl != null) {
                    MethodNode methodNode = modelMethod(methodDecl);
                    classNode.addMethod(methodNode);
                }
                FieldDeclaration fieldDecl = member.getFieldDeclaration();
                if (fieldDecl != null) {
                    List<Declaration> declarations = fieldDecl.getDeclarations();
                    for (Declaration declaration : declarations) {
                        if (declaration instanceof VariableDeclarator variableDeclarator) {
                            FieldNode fieldNode = modelField(variableDeclarator);
                            classNode.addField(fieldNode);
                        }
                        // FieldNode fieldNode = modelField(fieldDecl, declaration.getNameToken().lexeme());
                        // classNode.addField(fieldNode);
                    }
                }
            }
        }

        return classNode;
    }

    public EnumNode modelEnum(EnumDeclaration type) {
        JlsName packageName = getPackageName(type);
        currentTypeName = NameUtil.createTypeName(packageName, type.getNameToken().lexeme());
        EnumNode enumNode = new EnumNode(currentTypeName);
        enumNode.setSourcePath(sourceFileName);

        EnumBody enumBody = type.getEnumBody();
        if (enumBody != null) {

            EnumConstantList ecl = enumBody.getEnumConstantList();
            if (ecl != null) {
                for (EnumConstant ec : ecl.getEnumConstantList()) {
                    JlsName fieldName = NameUtil.createMemberName(currentTypeName, ec.getNameToken().lexeme());
                    FieldNode constantNode = new FieldNode(null, fieldName);
                    constantNode.addModifier(Modifier.STATIC);
                    enumNode.addConstant(constantNode);
                }
                // TODO: Other members
            }
        }
        // List<Declaration> declarations = type.getDeclarations();
        // for (Declaration declaration : declarations) {
        //     if (declaration instanceof EnumConstant enumConstant) {
        //         FieldNode constantNode = new FieldNode(null, enumConstant.getNameToken().lexeme());
        //         enumNode.addConstant(constantNode);
        //     }
        //     // TODO: Other members
        // }

        return enumNode;
    }

    public RecordNode modelRecord(RecordDeclaration type) {
        JlsName packageName = getPackageName(type);
        currentTypeName = NameUtil.createTypeName(packageName, type.getNameToken().lexeme());
        RecordNode recordNode = new RecordNode(currentTypeName);
        recordNode.setSourcePath(sourceFileName);

        RecordHeader recordHeadder = type.getRecordHeader();
        RecordComponentList rcl = recordHeadder.getRecordComponentList();
        for (RecordComponent component : rcl.getRecordComponentList()) {
            String typeString = component.getUnannType().toString();
            VariableTypeNode vt = ModelUtil.parseVariableType(typeString);
            JlsName fieldName = NameUtil.createMemberName(currentTypeName, component.getNameString());
            FieldNode componentNode = new FieldNode(vt, fieldName);
            recordNode.addField(componentNode);
        }

        return recordNode;
    }

    public AnnotationNode modelAnnotation(AnnotationInterfaceDeclaration type) {
        JlsName packageName = getPackageName(type);
        currentTypeName = NameUtil.createTypeName(packageName, type.getNameToken().lexeme());
        AnnotationNode annotationNode = new AnnotationNode(currentTypeName);
        annotationNode.setSourcePath(sourceFileName);

        return annotationNode;
    }

    public InterfaceNode modelInterface(NormalInterfaceDeclaration type) {
        JlsName packageName = getPackageName(type);
        currentTypeName = NameUtil.createTypeName(packageName, type.getNameToken().lexeme());
        InterfaceNode interfaceNode = new InterfaceNode(currentTypeName);
        interfaceNode.setSourcePath(sourceFileName);

        return interfaceNode;
    }

    @Override
    public FieldNode modelField(VariableDeclarator variableDeclarator) {
        FieldDeclaration fieldDecl = variableDeclarator.getFirstAncestor(FieldDeclaration.class);
        String nameString = variableDeclarator.getNameToken().lexeme();
        JlsName fieldName = NameUtil.createMemberName(currentTypeName, nameString);
        //TODO: typeString here is the short one. We want the long one
        //      Semantic Analyzer is the right place to change it to a long one
        String typeString = fieldDecl.getUnannType().toString();
        VariableTypeNode vt = ModelUtil.parseVariableType(typeString);
        FieldNode fieldNode = new FieldNode(vt, fieldName);
        return fieldNode;
    }

    // TODO: Parameters
    @Override
    public MethodNode modelMethod(MethodDeclaration method) {
        String simpleName = method.getNameToken().lexeme();
        JlsName name = NameUtil.createMemberName(currentTypeName, simpleName);
        String returnType = "void";
        MethodNode methodNode = new MethodNode(returnType, name);

        for (MethodModifier modifier : method.getMethodModifiers()) {
            Annotation anno = modifier.getAnnotation();
            if (anno != null) {
                AppliedAnnotationNode aa = modelAppliedAnnotation(anno);
                methodNode.addAppliedAnnotation(aa);
            }
        }
        // TODO: Null checks?
        FormalParameterList fpl = method.getMethodHeader().getMethodDeclarator().getFormalParameterList();
        addParameters(methodNode, fpl);

        List<MethodInvocation> calls = method.getDescendants(MethodInvocation.class);
        for (MethodInvocation call : calls) {
            methodNode.addCall(call.getMethodName().toString());
        }
        return methodNode;
    }

    // TODO: Parameters
    public MethodNode modelConstructor(ConstructorDeclaration constructor, TypeNode typeNode) {
        MethodNode constructorNode = new MethodNode(null, typeNode.getName());
        for (ConstructorModifier modifier : constructor.getConstructorModifierList()) {
            Annotation anno = modifier.getAnnotation();
            if (anno != null) {
                AppliedAnnotationNode aa = modelAppliedAnnotation(anno);
                constructorNode.addAppliedAnnotation(aa);
            }
        }
        FormalParameterList fpl = constructor.getConstructorDeclarator().getFormalParameterList();
        addParameters(constructorNode, fpl);

        List<MethodInvocation> calls = constructor.getDescendants(MethodInvocation.class);
        for (MethodInvocation call : calls) {
            constructorNode.addCall(call.getMethodName().toString());
        }
        return constructorNode;
    }

    private void addParameters(MethodNode methodNode, FormalParameterList fpl) {
        if (fpl != null && fpl.getFormalParameterList() != null) {
            for (FormalParameter formalParameter : fpl.getFormalParameterList()) {
                ParamNode paramNode = modelParam(formalParameter);
                methodNode.addParam(paramNode);
            }
        }
    }

    @Override
    public ParamNode modelParam(FormalParameter parameter) {
        String typeString = parameter.getUnannType().toString();
        // TODO: This should be FQN
        VariableTypeNode vt = ModelUtil.parseVariableType(typeString);
        JlsName paramName = NameUtil.createMemberName(parameter.getNameString());
        ParamNode paramNode = new ParamNode(vt, paramName);
        return paramNode;
    }

    @Override
    public TypeNode modelType(NormalClassDeclaration t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'modelType'");
    }

    public AppliedAnnotationNode modelAppliedAnnotation(Annotation anno) {
        AppliedAnnotationNode node = new AppliedAnnotationNode();

        TypeName annoTypeName = anno.getFirstDescendant(TypeName.class);
        String annoNameString = annoTypeName.toString();
        JlsName annotationName = NameUtil.createTypeName(currentPackageName, annoNameString);
        node.setTypeName(annotationName);

        SingleElementAnnotation sea = anno.getSingleElementAnnotation();
        if (sea != null) {
            ElementValue elementValue = sea.getElementValue();
            String value = getString(elementValue);
            AnnotationElement element = new AnnotationElement(null, null, value);
            node.addElement(element);
        }
        return node;
    }



    private String getString(ElementValue elementValue) {
        Literal literal = elementValue.getFirstDescendant(Literal.class);
        return literal == null ? null : getString(literal);
    }

    private String getString(Literal literal) {
        StringLiteral stringLiteral = literal.getFirstDescendant(StringLiteral.class);
        return stringLiteral == null ? null : stringLiteral.getValue();
    }

    private JlsName getPackageName(TypeDeclaration type) {
        ASTNode astNode = (ASTNode)type;
        CompilationUnit cu = astNode.getFirstAncestor(CompilationUnit.class);
        PackageDeclaration packageDeclaration = cu.getFirstDescendant(PackageDeclaration.class);
        String nameString = packageDeclaration.toString();
        JlsName packageName = NameUtil.createPackageName(nameString);
        return packageName;
    }

    // private JlsName createTypeName(TypeDeclaration type) {
    //     ASTNode astNode = (ASTNode)type;
    //     CompilationUnit cu = astNode.getFirstAncestor(CompilationUnit.class);
    //     PackageDeclaration packageDeclaration = cu.getFirstDescendant(PackageDeclaration.class);
    //     String simpleName = type.getNameToken().lexeme();
    //     currentTypeName = simpleName;
    //     JlsName className = null;
    //     if (packageDeclaration != null) {
    //         String packageName = packageDeclaration.toString();
    //         currentPackageName = packageName;
    //         String fqn = packageName + "." + simpleName;
    //         currentTypeName = fqn;
    //         className = NameUtil.createName(null, fqn, packageName);
    //     } else {
    //         className = NameUtil.createName(null, simpleName, null);
    //     }
    //     return className;
    // }

}
