package io.github.qishr.cascara.java.ast.interfaces;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.MethodBody;
import io.github.qishr.cascara.java.ast.classes.MethodHeader;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// InterfaceMethodDeclaration:
/// {InterfaceMethodModifier} MethodHeader MethodBody
public class InterfaceMethodDeclaration extends ASTNode implements Identifiable, Declaration {
    private List<InterfaceMethodModifier> interfaceMethodModifierList = new ArrayList<>();
    private MethodHeader methodHeader = null;
    private MethodBody methodBody = null;

    public Token getNameToken() {
        return methodHeader == null ? null : methodHeader.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public String toString() {
        return getNameToken() == null ? StringConstant.UNDEFINED : getNameToken().lexeme();
    }

    public List<InterfaceMethodModifier> getInterfaceMethodModifierList() {
        return interfaceMethodModifierList;
    }

    public void addInterfaceMethodModifier(InterfaceMethodModifier item) {
        interfaceMethodModifierList.add(item);
        addChild(item);
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

    public void setMethodBody(MethodBody methodBody) {
        this.methodBody = methodBody;
        addChild(methodBody);
    }

}
