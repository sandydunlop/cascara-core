package io.github.qishr.cascara.java.parser;

import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public class ExpectedTokenException extends SyntaxException {
    public ExpectedTokenException(TokenType expected, Token actual) {
        super("Expected token: " + expected +
              ", got " + actual.type() + " at " + actual.line());
    }
}
