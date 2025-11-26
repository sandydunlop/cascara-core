package io.github.qishr.cascara.java.parser;

import io.github.qishr.cascara.java.parser.Tokenizer.Token;

public class ExpectedConstructException extends SyntaxException {
    public ExpectedConstructException(String expected, Token actual) {
        super("Expected construct: " + expected +
              ", got " + actual.type() + " at " + actual.line());
    }
}
