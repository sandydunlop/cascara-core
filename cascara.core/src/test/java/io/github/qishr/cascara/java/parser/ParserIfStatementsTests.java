package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.ast.expressions.RelationalExpression;
import io.github.qishr.cascara.java.ast.statements.IfThenElseStatement;
import io.github.qishr.cascara.java.parser.SyntaxException;

public class ParserIfStatementsTests extends ParserTestsBase {

    @BeforeEach
    void setup() {
        parserTestSetup();
    }

    @Test
    void test_parseIfThenElse_emptyBlocks() throws SyntaxException {
        setCode("if (value == 4) {} else {}");
        IfThenElseStatement statement = parser.parseIfThenElseStatement();
        assertNotNull(statement);
        List<RelationalExpression> constructs = listConstructs(statement, RelationalExpression.class);
        assertEquals(2, constructs.size());
    }
}
