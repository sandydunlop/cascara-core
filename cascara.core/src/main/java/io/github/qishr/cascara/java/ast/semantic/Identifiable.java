package io.github.qishr.cascara.java.ast.semantic;

import io.github.qishr.cascara.java.parser.Tokenizer.Token;

public interface Identifiable {
    public Token getNameToken();
    public String getNameString();
    public String toString();
}
