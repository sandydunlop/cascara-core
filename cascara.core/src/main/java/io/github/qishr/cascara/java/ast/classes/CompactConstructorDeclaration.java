package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// CompactConstructorDeclaration:
/// {ConstructorModifier} SimpleTypeName ConstructorBody
public class CompactConstructorDeclaration extends ASTNode {
    private List<ConstructorModifier> constructorModifierList = new ArrayList<>();
    private SimpleTypeName simpleTypeName = null;
    private ConstructorBody constructorBody = null;

    public List<ConstructorModifier> getConstructorModifierList() {
        return constructorModifierList;
    }

    public void addConstructorModifier(ConstructorModifier item) {
        constructorModifierList.add(item);
        addChild(item);
    }

    public SimpleTypeName getSimpleTypeName() {
        return simpleTypeName;
    }

    public void setSimpleTypeName(SimpleTypeName simpleTypeName) {
        this.simpleTypeName = simpleTypeName;
        addChild(simpleTypeName);
    }

    public ConstructorBody getConstructorBody() {
        return constructorBody;
    }

    public void setConstructorBody(ConstructorBody constructorBody) {
        this.constructorBody = constructorBody;
        addChild(constructorBody);
    }

}
