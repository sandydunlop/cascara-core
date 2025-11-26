package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.EnumConstant;
import io.github.qishr.cascara.java.ast.expressions.Assignment;
import io.github.qishr.cascara.java.ast.expressions.AssignmentExpression;
import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.expressions.PrimaryNoNewArray;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.names.AmbiguousName;
import io.github.qishr.cascara.java.ast.names.ExpressionName;
import io.github.qishr.cascara.java.ast.names.PackageOrTypeName;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.statements.BlockStatement;
import io.github.qishr.cascara.java.ast.statements.LocalVariableDeclaration;
import io.github.qishr.cascara.java.ast.types.ClassType;
import io.github.qishr.cascara.java.ast.types.VariableInitializer;
import io.github.qishr.cascara.java.parser.SyntaxException;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

class ParserNameTests extends ParserTestsBase {

    @BeforeEach
    void setup() {
        parserTestSetup();
    }

    @Test
    void test_parseAmbiguousName_1() throws SyntaxException {
        setCode("a.b.c");
        AmbiguousName name = parser.parseAmbiguousName();
        assertNotNull(name);
        assertEquals("a", name.getName().getName().getIdentifier().getNameToken().lexeme());
        assertEquals("b", name.getName().getIdentifier().getNameToken().lexeme());
        assertEquals("c", name.getIdentifier().getNameToken().lexeme());
    }

    @Test
    void test_parseExpressionName_1() throws SyntaxException {
        setCode("a.b.c");
        ExpressionName name = parser.parseExpressionName();
        assertNotNull(name);
        assertEquals("a", name.getName().getName().getIdentifier().getNameToken().lexeme());
        assertEquals("b", name.getName().getIdentifier().getNameToken().lexeme());
        assertEquals("c", name.getIdentifier().getNameToken().lexeme());
    }

    @Test
    void test_parseExpressionName_2() throws SyntaxException {
        setCode("a.b(");
        ExpressionName exp = parser.parseExpressionName();
        assertNotNull(exp);
        assertEquals("a", exp.getIdentifier().getNameToken().lexeme());
    }

    //

    // TODO
    // parseModuleName
    // pakageName

    @Test
    void test_parseTypeName() throws SyntaxException {
        setCode("pkg.type");
        TypeName construct = parser.parseTypeName();
        assertNotNull(construct);
        List<Identifier> identifiers = listConstructs(construct, Identifier.class);
        assertEquals(2, identifiers.size());
        assertEquals("pkg", construct.getPackageOrTypeName().getNameToken().lexeme());
        assertEquals("type", construct.getTypeIdentifier().getNameToken().lexeme());
    }

    @Test
    void test_parsePackageOrTypeName() throws SyntaxException {
        setCode("pkg.type");
        PackageOrTypeName construct = parser.parsePackageOrTypeName();
        assertNotNull(construct);
        List<Identifier> identifiers = listConstructs(construct, Identifier.class);
        assertEquals(2, identifiers.size());
        assertEquals("pkg", construct.getPackageOrTypeName().getNameToken().lexeme());
        assertEquals("type", construct.getIdentifier().getNameToken().lexeme());
    }

    @Test
    void test_parseClassType() throws SyntaxException {
        setCode("pkg0.pkg1.Type");
        ClassType parsed = parser.parseClassType();
        assertNotNull(parsed);
        List<Identifier> ids = parsed.getDescendants(Identifier.class);
        assertEquals("pkg0", ids.get(0).toString());
        assertEquals("pkg1", ids.get(1).toString());
        assertEquals("Type", ids.get(2).toString());
    }

    @Test
    void test_nameClassification0() throws SyntaxException {
        setCode("class T {enum Color { RED, GREEN, BLUE } " +
                    "void main() {Color c = Color.RED;}}");

        BlockStatement parsed = parser.parseBlockStatement();

        List<EnumConstant> enumConstants = parsed.getDescendants(EnumConstant.class);
        assertEquals(3, enumConstants.size());

        TypeIdentifier typeIdentifier = parsed.getFirstDescendant(TypeIdentifier.class);

        LocalVariableDeclaration lvd = parsed.getFirstDescendant(LocalVariableDeclaration.class);
        assertNotNull(lvd);

        TypeIdentifier ti = lvd.getFirstDescendant(TypeIdentifier.class);
        assertEquals("Color", ti.getNameToken().lexeme());

        VariableInitializer vi = lvd.getFirstDescendant(VariableInitializer.class);
        AssignmentExpression ae = lvd.getFirstAncestor(AssignmentExpression.class);
        // assertNotNull(ae);
        Primary primary = lvd.getFirstDescendant(Primary.class);
        assertNotNull(primary);
        PrimaryNoNewArray pnna = primary.getPrimaryNoNewArray();
    }

    @Test
    void test_nameClassification() throws SyntaxException {
        setCode("class T {enum Color { RED, GREEN, BLUE } " +
                "void main() {Color c = Color.RED;}}");
        BlockStatement bs = parser.parseBlockStatement();
        VariableInitializer vi = bs.getFirstDescendant(VariableInitializer.class);
        List<Identifier> ids = vi.getDescendants(Identifier.class);

        Identifier id = ids.getLast();
        assertEquals("RED", id.getNameToken().lexeme());

        // RED is an Identifier.
        //
        // If we get Identifier from the AST here it will have parent TypeIdentifier.
        // What should its parent be? ExpressionName?
        //
        // When the name is being resolved, which part needs updated?
        //
        /// ExpressionName is defined as:
        /// Identifier
        /// AmbiguousName . Identifier

        ASTNode parent = id.getParent();
        assertNotNull(parent);
    }
}
