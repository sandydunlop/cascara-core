package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassBody;
import io.github.qishr.cascara.java.ast.types.TypeArguments;

/// UnqualifiedClassInstanceCreationExpression:
/// new [TypeArguments] ClassOrInterfaceTypeToInstantiate ( [ArgumentList] ) [ClassBody]
public class UnqualifiedClassInstanceCreationExpression extends ASTNode {
    private TypeArguments typeArguments = null;
    private ClassOrInterfaceTypeToInstantiate classOrInterfaceTypeToInstantiate = null;
    private ArgumentList argumentList = null;
    private ClassBody classBody = null;

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
    }

    public ClassOrInterfaceTypeToInstantiate getClassOrInterfaceTypeToInstantiate() {
        return classOrInterfaceTypeToInstantiate;
    }

    public void setClassOrInterfaceTypeToInstantiate(ClassOrInterfaceTypeToInstantiate classOrInterfaceTypeToInstantiate) {
        this.classOrInterfaceTypeToInstantiate = classOrInterfaceTypeToInstantiate;
        addChild(classOrInterfaceTypeToInstantiate);
    }

    public ArgumentList getArgumentList() {
        return argumentList;
    }

    public void setArgumentList(ArgumentList argumentList) {
        this.argumentList = argumentList;
        addChild(argumentList);
    }

    public ClassBody getClassBody() {
        return classBody;
    }

    public void setClassBody(ClassBody classBody) {
        this.classBody = classBody;
        addChild(classBody);
    }

}
