package io.github.qishr.cascara.java.parser;

import io.github.qishr.cascara.java.parser.Tokenizer.Token;

public class UnexpectedTokenException extends SyntaxException {
    public UnexpectedTokenException(Token token) {
        super("Unxpected token: " + token.type() + " at " + token.line());
    }

}
