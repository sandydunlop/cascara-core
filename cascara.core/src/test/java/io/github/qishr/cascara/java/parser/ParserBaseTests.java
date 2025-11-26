package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public class ParserBaseTests extends ParserTestsBase {
    @BeforeEach
    void setup() {
        parserTestSetup();
    }

}
