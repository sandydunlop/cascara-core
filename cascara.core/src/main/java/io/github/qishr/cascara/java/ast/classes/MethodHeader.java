package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// MethodHeader:
/// Result MethodDeclarator [Throws]
/// TypeParameters {Annotation} Result MethodDeclarator [Throws]
public class MethodHeader extends ASTNode implements Identifiable {
    TypeParameters typeParameters = null;
    List<Annotation> annotationList = new ArrayList<>();
    Result result = null;
    MethodDeclarator methodDeclarator = null;
    Throws throwsClause = null;

    @Override
    public Token getNameToken() {
        return methodDeclarator == null ? null : methodDeclarator.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public TypeParameters getTypeParameters() {
        return typeParameters;
    }

    public void setTypeParameters(TypeParameters typeParameters) {
        this.typeParameters = typeParameters;
        addChild(typeParameters);
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
        addChild(result);
    }

    public MethodDeclarator getMethodDeclarator() {
        return methodDeclarator;
    }

    public void setMethodDeclarator(MethodDeclarator methodDeclarator) {
        this.methodDeclarator = methodDeclarator;
        addChild(methodDeclarator);
    }

    public Throws getThrowsClause() {
        return throwsClause;
    }

    public void setThrowsClause(Throws throwsClause) {
        this.throwsClause = throwsClause;
        addChild(throwsClause);
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }
}
