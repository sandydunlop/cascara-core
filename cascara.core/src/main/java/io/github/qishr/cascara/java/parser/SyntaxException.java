package io.github.qishr.cascara.java.parser;


import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public class SyntaxException extends Exception {
    // private final TokenType expectedType;
    // private final Token actualToken;
    private final String message;

    // public TokenType getExpectedType() {
    //     return expectedType;
    // }

    // public Token getActualToken() {
    //     return actualToken;
    // }

    public SyntaxException(String message) {
        this.message = message;
    }

    // public SyntaxException(TokenType expectedType, Token actualToken) {
    //     this.expectedType = expectedType;
    //     this.actualToken = actualToken;
    // }

    public String getMessage() {
        return message;
        // return String.format("\"%s\" found but %s expected at %d,%d",
        //         actualToken.lexeme(), expectedType, actualToken.line(), actualToken.column());
    }
}
