package io.github.qishr.cascara.java.ast.semantic;

import io.github.qishr.cascara.java.parser.Tokenizer.Token;

public interface ModifiableAccess {
    public Token getNameToken();
    public String toString();
}
