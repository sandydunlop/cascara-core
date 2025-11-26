package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.ModuleName;
import io.github.qishr.cascara.java.ast.names.PackageName;
import io.github.qishr.cascara.java.ast.names.TypeName;

/// ModuleDirective:
/// requires {RequiresModifier} ModuleName ;
/// exports PackageName [to ModuleName {, ModuleName}] ;
/// opens PackageName [to ModuleName {, ModuleName}] ;
/// uses TypeName ;
/// provides TypeName with TypeName {, TypeName} ;
public class ModuleDirective extends ASTNode {
    private List<RequiresModifier> requiresModifierList = new ArrayList<>();
    private ModuleName requiresModuleName = null;
    private PackageName exportsPackageName = null;
    private PackageName opensPackageName = null;
    private ModuleName opensModuleName = null;
    private List<ModuleName> toModuleList = new ArrayList<>();
    private TypeName usesTypeName = null;
    private TypeName providesTypeName = null;
    private List<TypeName> withTypeNameList = new ArrayList<>();

    public List<RequiresModifier> getRequiresModifierList() {
        return requiresModifierList;
    }

    public void addRequiresModifier(RequiresModifier item) {
        requiresModifierList.add(item);
        addChild(item);
    }

    public ModuleName getRequiresModuleName() {
        return requiresModuleName;
    }

    public void setRequiresModuleName(ModuleName requiresModuleName) {
        this.requiresModuleName = requiresModuleName;
        addChild(requiresModuleName);
    }

    public PackageName getExportsPackageName() {
        return exportsPackageName;
    }

    public void setExportsPackageName(PackageName exportsPackageName) {
        this.exportsPackageName = exportsPackageName;
        addChild(exportsPackageName);
    }

    public PackageName getOpensPackageName() {
        return opensPackageName;
    }

    public void setOpensPackageName(PackageName toPackageName) {
        this.opensPackageName = toPackageName;
        addChild(toPackageName);
    }

    public ModuleName getOpensModuleName() {
        return opensModuleName;
    }

    public void setOpensModuleName(ModuleName opensModuleName) {
        this.opensModuleName = opensModuleName;
        addChild(opensModuleName);
    }

    public List<ModuleName> getToModuleList() {
        return toModuleList;
    }

    public void addToModule(ModuleName item) {
        toModuleList.add(item);
        addChild(item);
    }

    public TypeName getUsesTypeName() {
        return usesTypeName;
    }

    public void setUsesTypeName(TypeName usesTypeName) {
        this.usesTypeName = usesTypeName;
        addChild(usesTypeName);
    }

    public TypeName getProvidesTypeName() {
        return providesTypeName;
    }

    public void setProvidesTypeName(TypeName providesTypeName) {
        this.providesTypeName = providesTypeName;
        addChild(providesTypeName);
    }

    public List<TypeName> getWithTypeNameList() {
        return withTypeNameList;
    }

    public void addWithTypename(TypeName item) {
        withTypeNameList.add(item);
        addChild(item);
    }
}
