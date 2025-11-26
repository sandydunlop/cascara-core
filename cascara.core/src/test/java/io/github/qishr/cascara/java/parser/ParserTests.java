package io.github.qishr.cascara.java.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassLiteral;
import io.github.qishr.cascara.java.ast.classes.ConstructorDeclaration;
import io.github.qishr.cascara.java.ast.classes.MethodDeclaration;
import io.github.qishr.cascara.java.ast.classes.Throws;
import io.github.qishr.cascara.java.ast.classes.UnannClassType;
import io.github.qishr.cascara.java.ast.classes.VariableDeclarator;
import io.github.qishr.cascara.java.ast.expressions.ArgumentList;
import io.github.qishr.cascara.java.ast.expressions.Assignment;
import io.github.qishr.cascara.java.ast.expressions.CastExpression;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.expressions.LambdaExpression;
import io.github.qishr.cascara.java.ast.expressions.MethodInvocation;
import io.github.qishr.cascara.java.ast.expressions.PostIncrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PostfixExpression;
import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.expressions.UnaryExpression;
import io.github.qishr.cascara.java.ast.expressions.UnqualifiedClassInstanceCreationExpression;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.names.ExpressionName;
import io.github.qishr.cascara.java.ast.statements.BasicForStatement;
import io.github.qishr.cascara.java.ast.statements.BlockStatement;
import io.github.qishr.cascara.java.ast.statements.BlockStatements;
import io.github.qishr.cascara.java.ast.statements.ForStatement;
import io.github.qishr.cascara.java.ast.statements.IfThenStatement;
import io.github.qishr.cascara.java.ast.statements.LocalVariableDeclarationStatement;
import io.github.qishr.cascara.java.ast.statements.ReturnStatement;
import io.github.qishr.cascara.java.ast.statements.Statement;
import io.github.qishr.cascara.java.ast.statements.StatementWithoutTrailingSubstatement;
import io.github.qishr.cascara.java.ast.statements.SwitchRule;
import io.github.qishr.cascara.java.ast.structures.SingleTypeImportDeclaration;
import io.github.qishr.cascara.java.ast.types.TypeArgumentList;
import io.github.qishr.cascara.java.ast.types.TypeArguments;
import io.github.qishr.cascara.java.parser.SyntaxException;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

class ParserTests extends ParserTestsBase {

    @BeforeAll
    static void initAll() {
    }

    @BeforeEach
    void setup() {
        parserTestSetup();
    }

    @Test
    void test_parseModuleName() throws SyntaxException {
        addToken(TokenType.KEYWORD_IMPORT, "");
        addToken(TokenType.IDENTIFIER, "com");
        addToken(TokenType.DOT, "");
        addToken(TokenType.IDENTIFIER, "example");
        addToken(TokenType.DOT, "");
        addToken(TokenType.IDENTIFIER, "TypeName");
        addToken(TokenType.SEMICOLON, "");

        SingleTypeImportDeclaration decl = parser.parseSingleTypeImportDeclaration();
        assertNotNull(decl);
    }

    /// Check that there is no match at the end of a block
    @Test
    void test_parseUnannClassType_0() throws SyntaxException {
        setStack(TokenType.RIGHT_BRACE, "");
        UnannClassType construct = parser.parseUnannClassType();
        assertNull(construct);

        setStack(TokenType.SEMICOLON, "");
        construct = parser.parseUnannClassType();
        assertNull(construct);
    }

    /// TypeIdentifier [TypeArguments]
    @Test
    void test_parseUnannClassType_1() throws SyntaxException {
        addToken(TokenType.IDENTIFIER, "TypeName");
        addToken(TokenType.LESS_THAN, "");
        addToken(TokenType.IDENTIFIER, "T");
        addToken(TokenType.GREATER_THAN, "");

        UnannClassType construct = parser.parseUnannClassType();
        assertNotNull(construct);
    }

    /// PackageName . {Annotation} TypeIdentifier [TypeArguments]
    @Test
    void test_parseUnannClassType_2() throws SyntaxException {
        addToken(TokenType.IDENTIFIER, "com");
        addToken(TokenType.DOT, "");
        addToken(TokenType.IDENTIFIER, "example");
        addToken(TokenType.DOT, "");
        addToken(TokenType.IDENTIFIER, "TypeName");
        addToken(TokenType.LESS_THAN, "");
        addToken(TokenType.IDENTIFIER, "T");
        addToken(TokenType.GREATER_THAN, "");

        UnannClassType construct = parser.parseUnannClassType();
        assertNotNull(construct);
    }

    /// UnannClassOrInterfaceType . {Annotation} TypeIdentifier [TypeArguments]
    @Test
    void test_parseUnannClassType_3() throws SyntaxException {
        addToken(TokenType.IDENTIFIER, "Main");
        addToken(TokenType.DOT, "");
        addToken(TokenType.IDENTIFIER, "TypeName");
        addToken(TokenType.LESS_THAN, "");
        addToken(TokenType.IDENTIFIER, "T");
        addToken(TokenType.GREATER_THAN, "");

        UnannClassType construct = parser.parseUnannClassType();
        assertNotNull(construct);
    }

    /// Check that invalid input gives null output
    @Test
    void test_parsePrimary_SUPER () throws SyntaxException {
        addToken(TokenType.KEYWORD_SUPER, "");
        Primary primary = parser.parsePrimary(-1, -1, -1);
        assertNull(primary);
    }

    //TODO: These tests for Primary:
    // new int[10][0]              // ArrayAccess with ArrayCreationExpression
    // new Foo()[0]                 // ArrayAccess with ClassInstanceCreationExpression
    // new int[10].length           // FieldAccess with ArrayCreationExpression
    // new Foo().bar()              // MethodInvocation with ClassInstanceCreationExpression
    // (new Foo()).new Bar()        // (Expression) . new
    // obj.field                    // FieldAccess
    // obj.method()                 // MethodInvocation
    // a[b].c                       // ArrayAccess followed by FieldAccess

    @Test
    void test_parseThrowsClause() throws SyntaxException {
        setCode("throws Exception");
        Throws parsed = parser.parseThrowsClause();
        assertNotNull(parsed);
        List<Identifier> constructs = listConstructs(parsed, Identifier.class);
        assertEquals(1, constructs.size());
    }

    @Test
    void test_parseAnnotation() throws SyntaxException {
        setCode("@JlsChapter(\"4\")");
        Annotation parsed = parser.parseAnnotation();
        assertNotNull(parsed);
        List<Identifier> constructs = listConstructs(parsed, Identifier.class);
        assertEquals(1, constructs.size());
    }

    @Test
    void test_parsePrimary_chainedMethodCalls() throws SyntaxException {
        setCode("a().b();");
        Primary parsed = parser.parsePrimary(-1, -1, -1);
        assertNotNull(parsed);

        MethodInvocation b = parsed.getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(b);
        assertEquals("b", b.getMethodName().toString());

        MethodInvocation a = b.getPrimary().getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(a);
        assertEquals("a", a.getMethodName().toString());
    }

    @Test
    void test_parsePrimary_chainedVariableAndMethodCalls() throws SyntaxException {
        setCode("x.a().b();");
        Primary parsed = parser.parsePrimary(-1, -1, -1);
        assertNotNull(parsed);

        MethodInvocation b = parsed.getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(b);
        assertEquals("b", b.getMethodName().toString());

        // ASTNode a1 = b.getTarget();
        MethodInvocation a = b.getPrimary().getPrimaryNoNewArray().getMethodInvocation();
        //b.getPrimary().getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(a);
        assertEquals("a", a.getMethodName().toString());

        ASTNode x = a.getTarget();
        assertNotNull(x);
    }

    @Test
    void test_parsePrimary_chainedVariableAndMethodCallsWithParameter() throws SyntaxException {
        setCode("x.a().b(p);");
        Primary parsed = parser.parsePrimary(-1, -1, -1);
        assertNotNull(parsed);

        MethodInvocation b = parsed.getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(b);
        assertEquals("b", b.getMethodName().toString());

        MethodInvocation a = b.getPrimary().getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(a);
        assertEquals("a", a.getMethodName().toString());

        ASTNode x = a.getTarget();
        assertNotNull(x);

        ArgumentList al = b.getArgumentList();
        assertNotNull(al);
        List<Identifier> identifiers = b.getArgumentList().getDescendants(Identifier.class);
        Identifier id = identifiers.getFirst();
        assertNotNull(id);
    }

    @Test
    void test_parsePrimary_chainedVariableAndMethodCallsWithParameter2() throws SyntaxException {
        setCode("construct.getAnnotationList().add(annotations);");
        Primary parsed = parser.parsePrimary(-1, -1, -1);
        assertNotNull(parsed);

        MethodInvocation b = parsed.getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(b);
        assertEquals("add", b.getMethodName().toString());

        MethodInvocation a = b.getPrimary().getPrimaryNoNewArray().getMethodInvocation();
        assertNotNull(a);
        assertEquals("getAnnotationList", a.getMethodName().toString());

        ASTNode x = a.getTarget();
        assertNotNull(x);

        ArgumentList al = b.getArgumentList();
        assertNotNull(al);
        List<Identifier> identifiers = b.getArgumentList().getDescendants(Identifier.class);
        Identifier id = identifiers.getFirst();
        assertNotNull(id);
    }

    @Test
    void test_parsePrimary_methodCall() throws SyntaxException {
        setCode("token . type ( )");
        Primary construct = parser.parsePrimary(-1, -1, -1);
        assertNotNull(construct);
        List<MethodInvocation> methodInvocations = listConstructs(construct, MethodInvocation.class);
        MethodInvocation methodInvocation = methodInvocations.getFirst();
        assertEquals("type", methodInvocation.getMethodName().toString());
        assertEquals("token", methodInvocation.getExpressionName().toString());
    }

    // @JlsChapter("3.8")
    // private Identifier parseIdentifier() throws SyntaxException {
    //     trace("parseIdentifier");
    //     Token token = peek();
    //     if (token.type() == TokenType.IDENTIFIER) {
    //         consume();
    //         return new Identifier(token);
    //     }
    //     return null;
    // }

    @Test
    void test_sample_1() throws SyntaxException {
        setCode("trace(\"parseIdentifier\");");
        Statement construct = parser.parseStatement();
        assertNotNull(construct);
    }

    @Test
    void test_sample_2() throws SyntaxException {
        setCode("Token token = peek();");
        BlockStatement construct = parser.parseBlockStatement();
        assertNotNull(construct);
    }

    @Test
    void test_sample_3() throws SyntaxException {
        setCode("if (token.type() == TokenType.IDENTIFIER) {consume();return new Identifier(token);}");
        BlockStatement construct = parser.parseBlockStatement();
        assertNotNull(construct);
    }

    @Test
    void test_sample_4() throws SyntaxException {
        setCode("return new Identifier(token);");
        BlockStatement construct = parser.parseBlockStatement();
        assertNotNull(construct);
    }

    @Test
    void test_sample_5() throws SyntaxException {
        setCode("return new Identifier(token);");
        StatementWithoutTrailingSubstatement construct = parser.parseStatementWithoutTrailingSubstatement();
        assertNotNull(construct);
    }

    @Test
    void test_parseReturnStatement() throws SyntaxException {
        setCode("return new Identifier(token);");
        ReturnStatement construct = parser.parseReturnStatement();
        assertNotNull(construct);
    }

    @Test
    void test_parseUnqualifiedClassInstanceCreationExpression() throws SyntaxException {
        setCode("new Identifier(token);");
        UnqualifiedClassInstanceCreationExpression construct = parser.parseUnqualifiedClassInstanceCreationExpression();
        assertNotNull(construct);
    }

    @Test
    void test_parseForStatement() throws SyntaxException {
        setCode("for (int item = 0; item < 4; item++) ;");
        ForStatement construct = parser.parseForStatement();
        assertNotNull(construct);
    }
    @Test
    void test_parsePostIncrementExpression() throws SyntaxException {
        setCode("item++;");
        PostIncrementExpression construct = parser.parsePostIncrementExpression(-1, -1);
        assertNotNull(construct);
    }


    // THis: There is a loop betwee parsePostfixExpression and the things it calls
    // Need to resolve that

    @Test
    void test_parseBasicForStatement() throws SyntaxException {
        setCode("for (int item = 0; item < 4; item++) ;");
        BasicForStatement construct = parser.parseBasicForStatement();
        assertNotNull(construct);
    }

    // THis:
    @Test
    void test_int() throws SyntaxException {
        setCode("int item = 0;");
        BlockStatement construct = parser.parseBlockStatement();
        assertNotNull(construct);
    }

    @Test
    void test_parsePostfixExpression() throws SyntaxException {
        setCode("item++;");
        PostfixExpression construct = parser.parsePostfixExpression();
        assertNotNull(construct);
    }

    @Test
    void test_parseArgumentList() throws SyntaxException {
        setCode("annotation)");
        ArgumentList construct = parser.parseArgumentList();
        assertNotNull(construct);
        List<Identifier> identifiers = construct.getDescendants(Identifier.class);
        assertNotNull(identifiers);
        // TODO: the identifier is a child of TypeName. We can't tell if it's a type name here. It probably isn't.
    }

    @Test
    void test_gg() throws SyntaxException {
        setCode("construct.getAnnotationList().add(annotations);\n" + //
                        "                for (Annotation item : annotations)");
        BlockStatements construct = parser.parseBlockStatements();
        assertNotNull(construct);
    }

    @Test
    void test_gh() throws SyntaxException {
        setCode("            if (b) {\n" + //
                        "                x.a().b(p);\n" + //
                        "                for (T i : l) {\n" + //
                        "                    m();\n" + //
                        "                }\n" + //
                        "                return construct;\n" + //
                        "            } else {\n" + //
                        "                break;\n" + //
                        "            }\n" + //
                        "");
        Statement construct = parser.parseStatement();
        assertNotNull(construct);
    }

    @Test
    void test_chaining() throws SyntaxException {
        setCode("x.a().b(p);");
        MethodInvocation b = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(b);
        assertEquals("b", b.getMethodName().toString());
        MethodInvocation a = b.getFirstDescendant(MethodInvocation.class);
        assertEquals("a", a.getMethodName().toString());
        ExpressionName x = a.getFirstDescendant(ExpressionName.class);
        assertEquals("x", x.toString());
    }

    @Test
    void test_fieldAccess() throws SyntaxException {
        setCode("this.x = 4;");
        Assignment parsed = parser.parseAssignment();
        assertNotNull(parsed);
    }

    @Test
    void test_nestedCalls() throws SyntaxException {
        setCode("a(b());");
        MethodInvocation a = parser.parseMethodInvocation(null, -1, -1, -1);
        assertEquals("a", a.getMethodName().toString());
        ArgumentList al = a.getFirstDescendant(ArgumentList.class);
        MethodInvocation b = al.getFirstDescendant(MethodInvocation.class);
        assertEquals("b", b.getMethodName().toString());

    }

    @Test
    void test_nestedCalls2() throws SyntaxException {
        setCode("a(b());");
        MethodInvocation a = parser.parseMethodInvocation(null, -1, -1, -1);
        assertEquals("a", a.getMethodName().toString());
        ArgumentList al = a.getFirstDescendant(ArgumentList.class);
        MethodInvocation b = al.getFirstDescendant(MethodInvocation.class);
        assertEquals("b", b.getMethodName().toString());

    }

    @Test
    void test_parseBlockStatement() throws SyntaxException {
        setCode("v.a(b());");
        BlockStatement statement = parser.parseBlockStatement();
        assertNotNull(statement);
    }

    @Test
    void test_parseBlockStatements() throws SyntaxException {
        // setCode("o(); v.a(b()); o(); v.x(y()); o(); return v;");
        // setCode("if (b) { o(); v.a(b()); o(); v.x(y()); o(); return v; } m(); return null;");
        setCode("if (b) { a.b(); x.y(); } ");
        BlockStatements statements = parser.parseBlockStatements();
        assertNotNull(statements);
    }

    @Test
    void test_parens() throws SyntaxException {
        setCode("construct.setValue(token.lexeme().charAt(0));");
        MethodInvocation i = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(i);
    }

    // Starts going wrong around parseAssignmentExpression
    @Test
    void test_nestedParens() throws SyntaxException {
        setCode("construct.setValue((token.lexeme().charAt(0)));");
        MethodInvocation i = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(i);
    }

    @Test
    void test_switchRule() throws SyntaxException {
        setCode("case A -> a();");
        SwitchRule parsed = parser.parseSwitchRule();
        assertNotNull(parsed);
    }

    @Test
    void test_switch() throws SyntaxException {
        setCode("            declaration = switch (type) {\n" + //
                        "                case A -> a();\n" + //
                        "                case B -> b();\n" + //
                        // "                case KEYWORD_ENUM -> parseEnumDeclaration();\n" + //
                        // "                case CONTEXTUAL_RECORD -> parseRecordDeclaration();\n" + //
                        // "                case AT_SIGN -> parseAnnotationInterfaceDeclaration(); // @interface\n" + //
                        "                default -> null; // Should be unreachable due to canParseTopLevelDeclaration()\n" + //
                        "            };\n" + //
                        "");
        VariableDeclarator parsed = parser.parseVariableDeclarator();
        assertNotNull(parsed);
    }

    @Test
    void testThing() throws SyntaxException {
        setCode("(Type)object;");
        CastExpression parsed = parser.parseCastExpression()  ;
        assertNotNull(parsed);
    }

    @Test
    void test___() throws SyntaxException {
        setCode("        if (statement != null) {\n" + //
                        "            construct.setLocalClassOrInterfaceDeclaration((LocalClassOrInterfaceDeclaration)statement);\n" + //
                        "            pop();\n" + //
                        "            return construct;\n" + //
                        "        }");
        Statement construct = parser.parseStatement();
        assertNotNull(construct);
    }

    @Test
    void test_cast() throws SyntaxException {
        setCode("(T)p");
        CastExpression parsed = parser.parseCastExpression()  ;
        Token token = parser.peek();
        assertEquals(0, parser.savedTokenPositions.size());
        assertNotNull(parsed);
    }

    @Test
    void test_stackCheck1() throws SyntaxException {
        setCode("o.m((T)p);");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1)  ;
        Token token = parser.peek();
        assertEquals(0, parser.savedTokenPositions.size());
        assertNotNull(parsed);
    }

    @Test
    void test_stackCheck2() throws SyntaxException {
        setCode("o.m();");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1)  ;
        Token token = parser.peek();
        assertEquals(0, parser.savedTokenPositions.size());
        assertNotNull(parsed);
    }
    @Test
    void test_stackCheck3() throws SyntaxException {
        setCode("m();");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1)  ;
        Token token = parser.peek();
        assertEquals(0, parser.savedTokenPositions.size());
        assertNotNull(parsed);
    }

    @Test
    void test_parseVariableDeclaration() throws SyntaxException {
        setCode("Pair<List<SwitchBlockStatementGroup>,List<SwitchLabel>> pair =\n" + //
                        "                    Pair.of(sbsgList, slList);");
        LocalVariableDeclarationStatement parsed = parser.parseLocalVariableDeclarationStatement();
        assertNotNull(parsed);
    }

    @Test
    void test_parseTypeArguments() throws SyntaxException {
        setCode("<List<SwitchBlockStatementGroup>,List<SwitchLabel>>");
                        TypeArguments parsed = parser.parseTypeArguments();
        assertNotNull(parsed);
    }

    @Test
    void test_parseTypeArguments_simpler1() throws SyntaxException {
        setCode("<List<List<SwitchLabel>>>;");
                        TypeArguments parsed = parser.parseTypeArguments();
        assertNotNull(parsed);
    }

    @Test
    void test_parseTypeArguments_simpler2() throws SyntaxException {
        setCode("<Type1,Type2>");
                        TypeArguments parsed = parser.parseTypeArguments();
        assertNotNull(parsed);
    }

    @Test
    void test_parseTypeArgumentList() throws SyntaxException {
        setCode("Type1,Type2");
        TypeArgumentList parsed = parser.parseTypeArgumentList();
        assertNotNull(parsed);
    }

    @Test
    void test_field1() throws SyntaxException {
        setCode("construct.getPrimary().getFirstInstance(MethodInvocation.class);");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(parsed);
    }

    @Test
    void test_field2() throws SyntaxException {
        setCode("b(T.m);");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(parsed);
    }

    @Test
    void test_dotClass() throws SyntaxException {
        setCode("T.class");
        ClassLiteral parsed = parser.parseClassLiteral();
        assertNotNull(parsed);
    }

    //TODO: This isn't parsing the second method call
    @Test
    void test_method_chaining() throws SyntaxException {
        setCode("getDynamicToolBar().addSection(toolBarSection);");
        MethodInvocation parsed = parser.parseMethodInvocation(null, -1, -1, -1);
        assertNotNull(parsed);
    }

    @Test
    void test_chaining_methods() throws SyntaxException {
        setCode("public Central(Stage primaryStage) {\n" +
                // "        toolBarSection = getToolBarSection();\n" + //
                "        getDynamicToolBar().addSection(toolBarSection);\n" + //
                // "        // TODO: as well as events, have requests\n" + //
                // "        eventBus.subscribe(EventKind.TOOLBAR, this);\n" + //
                "}\n");

        ConstructorDeclaration parsed = parser.parseConstructorDeclaration();
        assertNotNull(parsed);
    }

    @Test
    void test_lambda1() throws SyntaxException {
        setCode("            ToolBarButton openFolderButton = new ToolBarButton(\"Open Folder\", () -> {\n" + //
                        "                showOpenFolderDialog();\n" + //
                        "            });\n" + //
                        "");
        BlockStatement statement = parser.parseBlockStatement();
        assertNotNull(statement);

    }

    @Test
    void test_lambda2() throws SyntaxException {
        setCode("  () -> { showOpenFolderDialog(); }\n");
        LambdaExpression expression = parser.parseLambdaExpression();
        assertNotNull(expression);
    }

    @Test
    void test_m1() throws SyntaxException {
        setCode("    private Panel getPanelForPresentable(DataBroker presentable) {\n" + //
                        "        String presentableId = presentable.getClass().getName();\n" + //
                        "        if (dataPanels.get(presentableId) == null) {\n" + //
                        "            reporter.reportInfo(\"Creating panel for %s\", presentable.getPresentableName());\n" + //
                        "            Panel panel = createPanel(presentable);\n" + //
                        "            getTabPanel().addTab(panel);\n" + //
                        "            dataPanels.put(presentableId, panel);\n" + //
                        "            if (!(presentable instanceof ExtensionListDataBroker)) {\n" + //
                        "                // Don't add \"Extensions\" as it should always be tehre\n" + //
                        "                addToViewMenu(panel);\n" + //
                        "            }\n" + //
                        "        } else {\n" + //
                        "            showPanel(presentableId);\n" + //
                        "        }\n" + //
                        "        Panel panel = dataPanels.get(presentableId);\n" + //
                        "        return panel;\n" + //
                        "    }\n" + //
                        "");
        MethodDeclaration decl = parser.parseMethodDeclaration();
        assertNotNull(decl);
    }

    @Test
    void test_if_statement() throws SyntaxException {
        setCode("   if (!(presentable instanceof ExtensionListDataBroker)) {\n" + //
                "       addToViewMenu(panel);\n" + //
                        "}\n");
        IfThenStatement st = parser.parseIfThenStatement();
        assertNotNull(st);
    }

    @Test
    void test_if_statement2() throws SyntaxException {
        setCode("if (!(presentable)) {\n" + //
                "    addToViewMenu(panel);\n" + //
                "}\n");
        IfThenStatement st = parser.parseIfThenStatement();
        assertNotNull(st);
    }

    @Test
    void test_if_unary() throws SyntaxException {
        setCode("(presentable)");
        UnaryExpression e = parser.parseUnaryExpression();
        assertNotNull(e);
    }

    @Test
    void test_postfix() throws SyntaxException {
        setCode("(presentable)");
        PostfixExpression e = parser.parsePostfixExpression();
        assertNotNull(e);
    }

    @Test
    void test_expression() throws SyntaxException {
        setCode("presentable");
        Expression e = parser.parseExpression();
        assertNotNull(e);
    }

    @Test
    void test_if_statement3() throws SyntaxException {
        setCode("if (!presentable) {\n" + //
                "    addToViewMenu(panel);\n" + //
                "}\n");
        IfThenStatement st = parser.parseIfThenStatement();
        assertNotNull(st);
    }

    @Test
    void test_lambda3() throws SyntaxException {
        setCode("newItem.setOnAction(event -> { showPanel(panel.getDataBroker().getClass().getName()); });");
        Expression e = parser.parseExpression();
        assertNotNull(e);

    }
}
