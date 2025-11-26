package io.github.qishr.cascara.java.ast.semantic;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

public interface Name {
    public boolean isQualified();
    public Identifier getIdentifier();
    public void setIdentifier(Identifier identifier);
    public Name getQualifier();
    public String getQualifiedName();
    public ASTNode getParent();
    public Name getName();
    public void setName(Name name);
    public Token getNameToken();
    public List<ASTNode> getChildren();
}
