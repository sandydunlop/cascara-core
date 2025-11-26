package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ImportDeclaration:
/// SingleTypeImportDeclaration
/// TypeImportOnDemandDeclaration
/// SingleStaticImportDeclaration
/// StaticImportOnDemandDeclaration
/// SingleModuleImportDeclaration
public class ImportDeclaration extends ASTNode {
    private SingleTypeImportDeclaration singleTypeImportDeclaration = null;
    private TypeImportOnDemandDeclaration typeImportOnDemandDeclaration = null;
    private SingleStaticImportDeclaration singleStaticImportDeclaration = null;
    private StaticImportOnDemandDeclaration staticImportOnDemandDeclaration = null;
    private SingleModuleImportDeclaration singleModuleImportDeclaration = null;

    public SingleTypeImportDeclaration getSingleTypeImportDeclaration() {
        return singleTypeImportDeclaration;
    }

    public void setSingleTypeImportDeclaration(SingleTypeImportDeclaration singleTypeImportDeclaration) {
        this.singleTypeImportDeclaration = singleTypeImportDeclaration;
        addChild(singleTypeImportDeclaration);
    }

    public TypeImportOnDemandDeclaration getTypeImportOnDemandDeclaration() {
        return typeImportOnDemandDeclaration;
    }

    public void setTypeImportOnDemandDeclaration(TypeImportOnDemandDeclaration typeImportOnDemandDeclaration) {
        this.typeImportOnDemandDeclaration = typeImportOnDemandDeclaration;
        addChild(typeImportOnDemandDeclaration);
    }

    public SingleStaticImportDeclaration getSingleStaticImportDeclaration() {
        return singleStaticImportDeclaration;
    }

    public void setSingleStaticImportDeclaration(SingleStaticImportDeclaration singleStaticImportDeclaration) {
        this.singleStaticImportDeclaration = singleStaticImportDeclaration;
        addChild(singleStaticImportDeclaration);
    }

    public StaticImportOnDemandDeclaration getStaticImportOnDemandDeclaration() {
        return staticImportOnDemandDeclaration;
    }

    public void setStaticImportOnDemandDeclaration(StaticImportOnDemandDeclaration staticImportOnDemandDeclaration) {
        this.staticImportOnDemandDeclaration = staticImportOnDemandDeclaration;
        addChild(staticImportOnDemandDeclaration);
    }

    public SingleModuleImportDeclaration getSingleModuleImportDeclaration() {
        return singleModuleImportDeclaration;
    }

    public void setSingleModuleImportDeclaration(SingleModuleImportDeclaration singleModuleImportDeclaration) {
        this.singleModuleImportDeclaration = singleModuleImportDeclaration;
        addChild(singleModuleImportDeclaration);
    }

}
