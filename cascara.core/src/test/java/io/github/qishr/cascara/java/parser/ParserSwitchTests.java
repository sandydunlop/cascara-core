package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.expressions.RelationalExpression;
import io.github.qishr.cascara.java.ast.statements.IfThenElseStatement;
import io.github.qishr.cascara.java.ast.statements.SwitchStatement;
import io.github.qishr.cascara.java.parser.SyntaxException;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

class ParserSwitchTests extends ParserTestsBase {
    @BeforeEach
    void setup() {
        parserTestSetup();
    }

    @Test
    void test_parseIfThenElse_emptyBlocks() throws SyntaxException {
        setCode("switch (a().b())");
        SwitchStatement parsed = parser.parseSwitchStatement();
        assertNotNull(parsed);
        List<Primary> constructs = listConstructs(parsed, Primary.class);
        assertEquals(2, constructs.size());
    }
}
