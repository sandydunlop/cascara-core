package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.ScopingConstruct;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// MethodDeclaration:
/// {MethodModifier} MethodHeader MethodBody
public class MethodDeclaration extends ASTNode implements Identifiable, Declaration, ScopingConstruct {
    private List<MethodModifier> methodModifiers = new ArrayList<>();
    private MethodHeader methodHeader = null;
    private MethodBody methodBody;

    @Override
    public List<Declaration> getDeclarations() {
        return null;
    }

    @Override
    public Token getNameToken() {
        return methodHeader == null ? null : methodHeader.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public MethodHeader getMethodHeader() {
        return methodHeader;
    }

    public void setMethodHeader(MethodHeader methodHeader) {
        this.methodHeader = methodHeader;
        addChild(methodHeader);
    }

    public MethodBody getMethodBody() {
        return methodBody;
    }

    public void setMethodBody(MethodBody classBody) {
        this.methodBody = classBody;
        addChild(classBody);
    }

    public void addMethodModifier(MethodModifier item) {
        methodModifiers.add(item);
        addChild(item);
    }

    public List<MethodModifier> getMethodModifiers() {
        return methodModifiers;
    }
}
