package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceDeclaration;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// TopLevelClassOrInterfaceDeclaration:
/// ClassDeclaration
/// InterfaceDeclaration
/// ;
public class TopLevelClassOrInterfaceDeclaration extends ASTNode implements Identifiable {
    private ClassDeclaration classDeclaration = null;
    private InterfaceDeclaration interfaceDeclaration = null;

    public TopLevelClassOrInterfaceDeclaration() {
        // Nothing to see here
    }

    public Declaration getDeclaration() {
        if (classDeclaration != null) {
            return classDeclaration.getDeclaration();
        } else if (interfaceDeclaration != null) {
            return interfaceDeclaration.getDeclaration();
        } else {
            return null;
        }
    }

    @Override
    public Token getNameToken() {
        if (classDeclaration != null) {
            return classDeclaration.getNameToken();
        } else if (interfaceDeclaration != null) {
            return interfaceDeclaration.getNameToken();
        }
        return null;
    }

    @Override
    public String getNameString() {
        return toString();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public ClassDeclaration getClassDeclaration() {
        return classDeclaration;
    }
    public void setClassDeclaration(ClassDeclaration classDeclaration) {
        this.classDeclaration = classDeclaration;
        addChild(classDeclaration);
    }
    public InterfaceDeclaration getInterfaceDeclaration() {
        return interfaceDeclaration;
    }
    public void setInterfaceDeclaration(InterfaceDeclaration interfaceDeclaration) {
        this.interfaceDeclaration = interfaceDeclaration;
        addChild(interfaceDeclaration);
    }


}
