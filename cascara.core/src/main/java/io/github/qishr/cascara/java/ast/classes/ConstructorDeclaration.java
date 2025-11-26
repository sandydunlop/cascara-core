package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ConstructorDeclaration:
/// {ConstructorModifier} ConstructorDeclarator [Throws] ConstructorBody
public class ConstructorDeclaration extends ASTNode implements ScopingConstruct {
    private List<ConstructorModifier> constructorModifierList = new ArrayList<>();
    private ConstructorDeclarator constructorDeclarator = null;
    private Throws throwsClause = null;
    private ConstructorBody constructorBody = null;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    public List<ConstructorModifier> getConstructorModifierList() {
        return constructorModifierList;
    }

    public void addConstructorModifier(ConstructorModifier item) {
        constructorModifierList.add(item);
        addChild(item);
    }

    public ConstructorDeclarator getConstructorDeclarator() {
        return constructorDeclarator;
    }

    public void setConstructorDeclarator(ConstructorDeclarator constructorDeclarator) {
        this.constructorDeclarator = constructorDeclarator;
        addChild(constructorDeclarator);
    }

    public Throws getThrows() {
        return throwsClause;
    }

    public void setThrows(Throws throwsClause) {
        this.throwsClause = throwsClause;
        addChild(throwsClause);
    }

    public ConstructorBody getConstructorBody() {
        return constructorBody;
    }

    public void setConstructorBody(ConstructorBody constructorBody) {
        this.constructorBody = constructorBody;
        addChild(constructorBody);
    }

    @Override
    public String toString() {
        if (constructorDeclarator != null) {
            return constructorDeclarator.toString();
        } else {
            return StringConstant.UNDEFINED;
        }
    }
}
