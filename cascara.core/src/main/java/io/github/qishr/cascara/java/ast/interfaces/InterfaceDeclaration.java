package io.github.qishr.cascara.java.ast.interfaces;


import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// InterfaceDeclaration:
/// NormalInterfaceDeclaration
/// AnnotationInterfaceDeclaration
public class InterfaceDeclaration extends ASTNode implements Identifiable {
    private NormalInterfaceDeclaration normalInterfaceDeclaration = null;
    private AnnotationInterfaceDeclaration annotationInterfaceDeclaration = null;

    public Declaration getDeclaration() {
        if (normalInterfaceDeclaration != null) {
            return normalInterfaceDeclaration;
        } else if (annotationInterfaceDeclaration != null) {
            return annotationInterfaceDeclaration;
        } else {
            return null;
        }
    }

    @Override
    public Token getNameToken() {
        if (normalInterfaceDeclaration != null) {
            return normalInterfaceDeclaration.getNameToken();
        } else if (annotationInterfaceDeclaration != null) {
            return annotationInterfaceDeclaration.getNameToken();
        }
        return null;
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public NormalInterfaceDeclaration getNormalInterfaceDeclaration() {
        return normalInterfaceDeclaration;
    }

    public void setNormalInterfaceDeclaration(NormalInterfaceDeclaration normalInterfaceDeclaration) {
        this.normalInterfaceDeclaration = normalInterfaceDeclaration;
        addChild(normalInterfaceDeclaration);
    }

    public AnnotationInterfaceDeclaration getAnnotationInterfaceDeclaration() {
        return annotationInterfaceDeclaration;
    }

    public void setAnnotationInterfaceDeclaration(AnnotationInterfaceDeclaration annotationInterfaceDeclaration) {
        this.annotationInterfaceDeclaration = annotationInterfaceDeclaration;
        addChild(annotationInterfaceDeclaration);
    }

}
