package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.ast.classes.UnannClassType;
import io.github.qishr.cascara.java.ast.classes.VariableDeclarator;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.parser.SyntaxException;

class ParserAssignmentTests extends ParserTestsBase {

    @BeforeEach
    void setup() {
        parserTestSetup();
    }

    @Test
    void test_parseVariableDeclarator() throws SyntaxException {
        setCode("id = peek();");
        VariableDeclarator parsed = parser.parseVariableDeclarator();
        assertNotNull(parsed);
        List<Identifier> constructs = listConstructs(parsed, Identifier.class);
        assertEquals(2, constructs.size());
    }

    @Test
    void test_parseUnannClassType() throws SyntaxException {
        setCode("List<Annotation> annotations = new ArrayList<>();");
        UnannClassType type = parser.parseUnannClassType();
        assertNotNull(type);
    }
}
