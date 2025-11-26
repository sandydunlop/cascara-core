package io.github.qishr.cascara.java.parser;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.anno.Here;
import io.github.qishr.cascara.anno.JlsChapter;
import io.github.qishr.cascara.anno.JlsPreview;
import io.github.qishr.cascara.anno.JlsVersion;
import io.github.qishr.cascara.anno.KeywordGate;
import io.github.qishr.cascara.anno.LeftRecursionEliminated;
import io.github.qishr.cascara.anno.RecursiveLoopDetection;
import io.github.qishr.cascara.common.Pair;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.arrays.ArrayInitializer;
import io.github.qishr.cascara.java.ast.classes.ClassBody;
import io.github.qishr.cascara.java.ast.classes.ClassBodyDeclaration;
import io.github.qishr.cascara.java.ast.classes.ClassDeclaration;
import io.github.qishr.cascara.java.ast.classes.ClassExtends;
import io.github.qishr.cascara.java.ast.classes.ClassImplements;
import io.github.qishr.cascara.java.ast.classes.ClassLiteral;
import io.github.qishr.cascara.java.ast.classes.ClassMemberDeclaration;
import io.github.qishr.cascara.java.ast.classes.ClassModifier;
import io.github.qishr.cascara.java.ast.classes.ClassPermits;
import io.github.qishr.cascara.java.ast.classes.CompactConstructorDeclaration;
import io.github.qishr.cascara.java.ast.classes.ConstructorBody;
import io.github.qishr.cascara.java.ast.classes.ConstructorDeclaration;
import io.github.qishr.cascara.java.ast.classes.ConstructorDeclarator;
import io.github.qishr.cascara.java.ast.classes.ConstructorInvocation;
import io.github.qishr.cascara.java.ast.classes.ConstructorModifier;
import io.github.qishr.cascara.java.ast.classes.EnumBody;
import io.github.qishr.cascara.java.ast.classes.EnumBodyDeclarations;
import io.github.qishr.cascara.java.ast.classes.EnumConstant;
import io.github.qishr.cascara.java.ast.classes.EnumConstantList;
import io.github.qishr.cascara.java.ast.classes.EnumConstantModifier;
import io.github.qishr.cascara.java.ast.classes.EnumDeclaration;
import io.github.qishr.cascara.java.ast.classes.ExceptionType;
import io.github.qishr.cascara.java.ast.classes.ExceptionTypeList;
import io.github.qishr.cascara.java.ast.classes.FieldDeclaration;
import io.github.qishr.cascara.java.ast.classes.FieldModifier;
import io.github.qishr.cascara.java.ast.classes.FormalParameter;
import io.github.qishr.cascara.java.ast.classes.FormalParameterList;
import io.github.qishr.cascara.java.ast.classes.InstanceInitializer;
import io.github.qishr.cascara.java.ast.classes.InterfaceTypeList;
import io.github.qishr.cascara.java.ast.classes.MethodBody;
import io.github.qishr.cascara.java.ast.classes.MethodDeclaration;
import io.github.qishr.cascara.java.ast.classes.MethodDeclarator;
import io.github.qishr.cascara.java.ast.classes.MethodHeader;
import io.github.qishr.cascara.java.ast.classes.MethodModifier;
import io.github.qishr.cascara.java.ast.classes.NormalClassDeclaration;
import io.github.qishr.cascara.java.ast.classes.ReceiverParameter;
import io.github.qishr.cascara.java.ast.classes.RecordBody;
import io.github.qishr.cascara.java.ast.classes.RecordBodyDeclaration;
import io.github.qishr.cascara.java.ast.classes.RecordComponent;
import io.github.qishr.cascara.java.ast.classes.RecordComponentList;
import io.github.qishr.cascara.java.ast.classes.RecordComponentModifier;
import io.github.qishr.cascara.java.ast.classes.RecordDeclaration;
import io.github.qishr.cascara.java.ast.classes.RecordHeader;
import io.github.qishr.cascara.java.ast.classes.Result;
import io.github.qishr.cascara.java.ast.classes.SimpleTypeName;
import io.github.qishr.cascara.java.ast.classes.StaticInitializer;
import io.github.qishr.cascara.java.ast.classes.Throws;
import io.github.qishr.cascara.java.ast.classes.TypeParameterList;
import io.github.qishr.cascara.java.ast.classes.TypeParameters;
import io.github.qishr.cascara.java.ast.classes.UnannArrayType;
import io.github.qishr.cascara.java.ast.classes.UnannClassOrInterfaceType;
import io.github.qishr.cascara.java.ast.classes.UnannClassType;
import io.github.qishr.cascara.java.ast.classes.UnannInterfaceType;
import io.github.qishr.cascara.java.ast.classes.UnannPrimitiveType;
import io.github.qishr.cascara.java.ast.classes.UnannReferenceType;
import io.github.qishr.cascara.java.ast.classes.UnannType;
import io.github.qishr.cascara.java.ast.classes.UnannTypeVariable;
import io.github.qishr.cascara.java.ast.classes.VariableArityParameter;
import io.github.qishr.cascara.java.ast.classes.VariableArityRecordComponent;
import io.github.qishr.cascara.java.ast.classes.VariableDeclarator;
import io.github.qishr.cascara.java.ast.classes.VariableDeclaratorId;
import io.github.qishr.cascara.java.ast.classes.VariableDeclaratorList;
import io.github.qishr.cascara.java.ast.classes.VariableModifier;
import io.github.qishr.cascara.java.ast.expressions.AdditiveExpression;
import io.github.qishr.cascara.java.ast.expressions.AndExpression;
import io.github.qishr.cascara.java.ast.expressions.ArgumentList;
import io.github.qishr.cascara.java.ast.expressions.ArrayAccess;
import io.github.qishr.cascara.java.ast.expressions.ArrayCreationExpression;
import io.github.qishr.cascara.java.ast.expressions.Assignment;
import io.github.qishr.cascara.java.ast.expressions.AssignmentExpression;
import io.github.qishr.cascara.java.ast.expressions.AssignmentOperator;
import io.github.qishr.cascara.java.ast.expressions.CastExpression;
import io.github.qishr.cascara.java.ast.expressions.ClassInstanceCreationExpression;
import io.github.qishr.cascara.java.ast.expressions.ClassOrInterfaceTypeToInstantiate;
import io.github.qishr.cascara.java.ast.expressions.ConciseLambdaParameter;
import io.github.qishr.cascara.java.ast.expressions.ConditionalAndExpression;
import io.github.qishr.cascara.java.ast.expressions.ConditionalExpression;
import io.github.qishr.cascara.java.ast.expressions.ConditionalOrExpression;
import io.github.qishr.cascara.java.ast.expressions.EqualityExpression;
import io.github.qishr.cascara.java.ast.expressions.ExclusiveOrExpression;
import io.github.qishr.cascara.java.ast.expressions.Expression;
import io.github.qishr.cascara.java.ast.expressions.FieldAccess;
import io.github.qishr.cascara.java.ast.expressions.InclusiveOrExpression;
import io.github.qishr.cascara.java.ast.expressions.InstanceofExpression;
import io.github.qishr.cascara.java.ast.expressions.LambdaBody;
import io.github.qishr.cascara.java.ast.expressions.LambdaExpression;
import io.github.qishr.cascara.java.ast.expressions.LambdaParameterList;
import io.github.qishr.cascara.java.ast.expressions.LambdaParameterType;
import io.github.qishr.cascara.java.ast.expressions.LambdaParameters;
import io.github.qishr.cascara.java.ast.expressions.LeftHandSide;
import io.github.qishr.cascara.java.ast.expressions.MethodInvocation;
import io.github.qishr.cascara.java.ast.expressions.MethodReference;
import io.github.qishr.cascara.java.ast.expressions.MultiplicativeExpression;
import io.github.qishr.cascara.java.ast.expressions.NormalLambdaParameter;
import io.github.qishr.cascara.java.ast.expressions.PostDecrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PostIncrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PostfixExpression;
import io.github.qishr.cascara.java.ast.expressions.PreDecrementExpression;
import io.github.qishr.cascara.java.ast.expressions.PreIncrementExpression;
import io.github.qishr.cascara.java.ast.expressions.Primary;
import io.github.qishr.cascara.java.ast.expressions.PrimaryNoNewArray;
import io.github.qishr.cascara.java.ast.expressions.RelationalExpression;
import io.github.qishr.cascara.java.ast.expressions.ShiftExpression;
import io.github.qishr.cascara.java.ast.expressions.SwitchExpression;
import io.github.qishr.cascara.java.ast.expressions.TypeArgumentsOrDiamond;
import io.github.qishr.cascara.java.ast.expressions.UnaryExpression;
import io.github.qishr.cascara.java.ast.expressions.UnaryExpressionNotPlusMinus;
import io.github.qishr.cascara.java.ast.expressions.UnqualifiedClassInstanceCreationExpression;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceBody;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceElementDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceElementModifier;
import io.github.qishr.cascara.java.ast.interfaces.AnnotationInterfaceMemberDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceBody;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceExtends;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceMemberDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceMethodDeclaration;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceMethodModifier;
import io.github.qishr.cascara.java.ast.interfaces.InterfaceModifier;
import io.github.qishr.cascara.java.ast.interfaces.InterfacePermits;
import io.github.qishr.cascara.java.ast.interfaces.NormalInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.lexical.BooleanLiteral;
import io.github.qishr.cascara.java.ast.lexical.CharacterLiteral;
import io.github.qishr.cascara.java.ast.lexical.FloatingPointLiteral;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.IntegerLiteral;
import io.github.qishr.cascara.java.ast.lexical.Literal;
import io.github.qishr.cascara.java.ast.lexical.NullLiteral;
import io.github.qishr.cascara.java.ast.lexical.StringLiteral;
import io.github.qishr.cascara.java.ast.lexical.TextBlock;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.lexical.UnqualifiedMethodIdentifier;
import io.github.qishr.cascara.java.ast.names.AmbiguousName;
import io.github.qishr.cascara.java.ast.names.ExpressionName;
import io.github.qishr.cascara.java.ast.names.MethodName;
import io.github.qishr.cascara.java.ast.names.ModuleName;
import io.github.qishr.cascara.java.ast.names.PackageName;
import io.github.qishr.cascara.java.ast.names.PackageOrTypeName;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.Name;
import io.github.qishr.cascara.java.ast.statements.AssertStatement;
import io.github.qishr.cascara.java.ast.statements.BasicForStatement;
import io.github.qishr.cascara.java.ast.statements.Block;
import io.github.qishr.cascara.java.ast.statements.BlockStatement;
import io.github.qishr.cascara.java.ast.statements.BlockStatements;
import io.github.qishr.cascara.java.ast.statements.BreakStatement;
import io.github.qishr.cascara.java.ast.statements.CaseConstant;
import io.github.qishr.cascara.java.ast.statements.CasePattern;
import io.github.qishr.cascara.java.ast.statements.ContinueStatement;
import io.github.qishr.cascara.java.ast.statements.DoStatement;
import io.github.qishr.cascara.java.ast.statements.EmptyStatement;
import io.github.qishr.cascara.java.ast.statements.EnhancedForStatement;
import io.github.qishr.cascara.java.ast.statements.ExpressionStatement;
import io.github.qishr.cascara.java.ast.statements.ForInit;
import io.github.qishr.cascara.java.ast.statements.ForStatement;
import io.github.qishr.cascara.java.ast.statements.ForStatementNoShortIf;
import io.github.qishr.cascara.java.ast.statements.ForUpdate;
import io.github.qishr.cascara.java.ast.statements.Guard;
import io.github.qishr.cascara.java.ast.statements.IfThenElseStatement;
import io.github.qishr.cascara.java.ast.statements.IfThenElseStatementNoShortIf;
import io.github.qishr.cascara.java.ast.statements.IfThenStatement;
import io.github.qishr.cascara.java.ast.statements.LabeledStatement;
import io.github.qishr.cascara.java.ast.statements.LabeledStatementNoShortIf;
import io.github.qishr.cascara.java.ast.statements.LocalClassOrInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.statements.LocalVariableDeclaration;
import io.github.qishr.cascara.java.ast.statements.LocalVariableDeclarationStatement;
import io.github.qishr.cascara.java.ast.statements.LocalVariableType;
import io.github.qishr.cascara.java.ast.statements.Pattern;
import io.github.qishr.cascara.java.ast.statements.RecordPattern;
import io.github.qishr.cascara.java.ast.statements.ReturnStatement;
import io.github.qishr.cascara.java.ast.statements.Statement;
import io.github.qishr.cascara.java.ast.statements.StatementExpression;
import io.github.qishr.cascara.java.ast.statements.StatementExpressionList;
import io.github.qishr.cascara.java.ast.statements.StatementNoShortIf;
import io.github.qishr.cascara.java.ast.statements.StatementWithoutTrailingSubstatement;
import io.github.qishr.cascara.java.ast.statements.SwitchBlock;
import io.github.qishr.cascara.java.ast.statements.SwitchBlockStatementGroup;
import io.github.qishr.cascara.java.ast.statements.SwitchLabel;
import io.github.qishr.cascara.java.ast.statements.SwitchRule;
import io.github.qishr.cascara.java.ast.statements.SwitchStatement;
import io.github.qishr.cascara.java.ast.statements.SynchronizedStatement;
import io.github.qishr.cascara.java.ast.statements.ThrowStatement;
import io.github.qishr.cascara.java.ast.statements.TryStatement;
import io.github.qishr.cascara.java.ast.statements.TypePattern;
import io.github.qishr.cascara.java.ast.statements.WhileStatement;
import io.github.qishr.cascara.java.ast.statements.WhileStatementNoShortIf;
import io.github.qishr.cascara.java.ast.statements.YieldStatement;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.ModularCompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ModuleDeclaration;
import io.github.qishr.cascara.java.ast.structures.ModuleDirective;
import io.github.qishr.cascara.java.ast.structures.OrdinaryCompilationUnit;
import io.github.qishr.cascara.java.ast.structures.PackageDeclaration;
import io.github.qishr.cascara.java.ast.structures.PackageModifier;
import io.github.qishr.cascara.java.ast.structures.RequiresModifier;
import io.github.qishr.cascara.java.ast.structures.SingleModuleImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.SingleStaticImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.SingleTypeImportDeclaration;
import io.github.qishr.cascara.java.ast.structures.StaticImportOnDemandDeclaration;
import io.github.qishr.cascara.java.ast.structures.TopLevelClassOrInterfaceDeclaration;
import io.github.qishr.cascara.java.ast.structures.TypeImportOnDemandDeclaration;
import io.github.qishr.cascara.java.ast.types.AdditionalBound;
import io.github.qishr.cascara.java.ast.types.ArrayType;
import io.github.qishr.cascara.java.ast.types.ClassOrInterfaceType;
import io.github.qishr.cascara.java.ast.types.ClassType;
import io.github.qishr.cascara.java.ast.types.ConstantDeclaration;
import io.github.qishr.cascara.java.ast.types.ConstantModifier;
import io.github.qishr.cascara.java.ast.types.DefaultValue;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.ast.types.ElementValue;
import io.github.qishr.cascara.java.ast.types.ElementValueArrayInitializer;
import io.github.qishr.cascara.java.ast.types.ElementValueList;
import io.github.qishr.cascara.java.ast.types.ElementValuePair;
import io.github.qishr.cascara.java.ast.types.ElementValuePairList;
import io.github.qishr.cascara.java.ast.types.FloatingPointType;
import io.github.qishr.cascara.java.ast.types.IntegralType;
import io.github.qishr.cascara.java.ast.types.InterfaceType;
import io.github.qishr.cascara.java.ast.types.MarkerAnnotation;
import io.github.qishr.cascara.java.ast.types.NormalAnnotation;
import io.github.qishr.cascara.java.ast.types.NumericType;
import io.github.qishr.cascara.java.ast.types.PrimitiveType;
import io.github.qishr.cascara.java.ast.types.ReferenceType;
import io.github.qishr.cascara.java.ast.types.SingleElementAnnotation;
import io.github.qishr.cascara.java.ast.types.TypeArgument;
import io.github.qishr.cascara.java.ast.types.TypeArgumentList;
import io.github.qishr.cascara.java.ast.types.TypeArguments;
import io.github.qishr.cascara.java.ast.types.TypeBound;
import io.github.qishr.cascara.java.ast.types.TypeParameter;
import io.github.qishr.cascara.java.ast.types.TypeParameterModifier;
import io.github.qishr.cascara.java.ast.types.TypeVariable;
import io.github.qishr.cascara.java.ast.types.VariableInitializer;
import io.github.qishr.cascara.java.ast.types.Wildcard;
import io.github.qishr.cascara.java.ast.types.WildcardBounds;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// JLS uses the Oxford comma :-)
public class Parser extends ParserBase {
    public Parser(Reporter reporter) {
        super(reporter);
    }

    //
    // Chapter 3 - Lexical Structure
    //

    @JlsChapter("3.8")
    private Identifier parseIdentifier() throws SyntaxException {
        trace("parseIdentifier");
        Token token = peek();
        if (token.type() == TokenType.IDENTIFIER) {
            consume();
            return new Identifier(token);
        }
        return null;
    }

    @JlsChapter("3.8")
    private TypeIdentifier parseTypeIdentifier() throws SyntaxException {
        trace("parseTypeIdentifier");
        TypeIdentifier construct = new TypeIdentifier();
        // Not: permits, record, sealed, var, or yield
        switch(peek().type()) {
            case CONTEXTUAL_PERMITS, CONTEXTUAL_RECORD, CONTEXTUAL_SEALED,
                    CONTEXTUAL_VAR, CONTEXTUAL_YIELD:
                unexpectedToken();
                break;
            default:
        }
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("3.8")
    UnqualifiedMethodIdentifier parseUnqualifiedMethodIdentifier() throws SyntaxException {
        trace("parseUnqualifiedMethodIdentifier");
        UnqualifiedMethodIdentifier construct = new UnqualifiedMethodIdentifier();
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    /// Everything parser method this calls should not call any more
    /// parser methods and should use the tokenizer output instead.
    @JlsChapter("3.10")
    private Literal parseLiteral() throws SyntaxException {
        trace("parseLiteral");
        Literal construct = new Literal();
        save();
        construct.setIntegerLiteral(parseIntegerLiteral());
        if (construct.getIntegerLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setFloatingPointLiteral(parseFloatingPointLiteral());
        if (construct.getFloatingPointLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setBooleanLiteral(parseBooleanLiteral());
        if (construct.getBooleanLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setCharacterLiteral(parseCharacterLiteral());
        if (construct.getCharacterLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setStringLiteral(parseStringLiteral());
        if (construct.getStringLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setTextBlock(parseTextBlock());
        if (construct.getTextBlock() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setNullLiteral(parseNullLiteral());
        if (construct.getNullLiteral() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("3.10")
    private IntegerLiteral parseIntegerLiteral() throws SyntaxException {
        trace("parseIntegerLiteral");
        IntegerLiteral construct = new IntegerLiteral();
        Token token = peek();
        if (token.type() == TokenType.INTEGER_LITERAL) {
            consume();
            construct.setValue(Integer.parseInt(token.lexeme()));
            return construct;
        }
        return null;
    }

    @JlsChapter("3.10")
    private FloatingPointLiteral parseFloatingPointLiteral() throws SyntaxException {
        trace("parseFloatingPointLiteral");
        FloatingPointLiteral construct = new FloatingPointLiteral();
        Token token = peek();
        if (token.type() == TokenType.FLOAT_LITERAL) {
            consume();
            construct.setValue(Float.parseFloat(token.lexeme()));
            return construct;
        }
        return null;
    }

    @JlsChapter("3.10")
    private BooleanLiteral parseBooleanLiteral() throws SyntaxException {
        trace("parseBooleanLiteral");
        BooleanLiteral construct = new BooleanLiteral();
        Token token = peek();
        if (token.type() == TokenType.BOOLEAN_LITERAL) {
            consume();
            construct.setValue(Boolean.parseBoolean(token.lexeme()));
            return construct;
        }
        return null;
    }

    @JlsChapter("3.10.4")
    private CharacterLiteral parseCharacterLiteral() throws SyntaxException {
        trace("parseCharacterLiteral");
        CharacterLiteral construct = new CharacterLiteral();
        Token token = peek();
        if (token.type() == TokenType.CHAR_LITERAL) {
            consume();
            construct.setValue(token.lexeme().charAt(0));
            return construct;
        }
        return null;
    }

    @JlsChapter("3.10.5")
    private StringLiteral parseStringLiteral() throws SyntaxException {
        trace("parseStringLiteral");
        StringLiteral construct = new StringLiteral();
        Token token = peek();
        if (token.type() == TokenType.STRING_LITERAL) {
            consume();
            String lexeme = token.lexeme();
            String value = lexeme.substring(1, lexeme.length() - 1);
            construct.setValue(value);
            return construct;
        }
        return null;
    }

    @JlsChapter("3.10.6")
    private TextBlock parseTextBlock() throws SyntaxException {
        unimplemented("parseTextBlock");
        return null;
    }

    @JlsChapter("3.10.8")
    private NullLiteral parseNullLiteral() throws SyntaxException {
        trace("parseNullLiteral");
        NullLiteral construct = new NullLiteral();
        Token token = peek();
        if (token.type() == TokenType.KEYWORD_NULL) {
            consume();
            return construct;
        }
        return null;
    }

    //
    // Chapter 4 - Types, Values, and Variables
    //

    @JlsChapter("4.2")
    private PrimitiveType parsePrimitiveType() throws SyntaxException {
        trace("parsePrimitiveType");
        PrimitiveType construct = new PrimitiveType();
        save();
        construct.setAnnotation(parseAnnotation());
        if (isTokenType(TokenType.KEYWORD_BOOLEAN)) {
            consume();
            construct.setBooleanKeyword(true);
            pop();
            return construct;
        }
        construct.setNumericType(parseNumericType());
        if (construct.getNumericType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.2")
    private NumericType parseNumericType() throws SyntaxException {
        trace("parseNumericType");
        NumericType construct = new NumericType();
        save();
        construct.setIntegralType(parseIntegralType());
        if (construct.getIntegralType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setFloatingPointType(parseFloatingPointType());
        if (construct.getFloatingPointType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.2")
    private IntegralType parseIntegralType() throws SyntaxException {
        trace("parseIntegralType");
        switch (peek().type()) {
            case KEYWORD_BYTE, KEYWORD_SHORT, KEYWORD_INT, KEYWORD_LONG, KEYWORD_CHAR:
                Token token = consume();
                return new IntegralType(token.type());
            default:
                return null;
        }
    }

    @JlsChapter("4.2")
    private FloatingPointType parseFloatingPointType() throws SyntaxException {
        trace("parseFloatingPointType");
        switch (peek().type()) {
            case KEYWORD_FLOAT, KEYWORD_DOUBLE:
                Token token = consume();
                return new FloatingPointType(token.type());
            default:
                return null;
        }
    }

    @JlsChapter("4.3")
    private Dims parseDims() throws SyntaxException {
        trace("parseDims");
        // TODO: First dimension is required
        Dims construct = null;
        while(true) {
            List<Annotation> annotations = new ArrayList<>();
            for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
                annotations.add(item);
            }

            if (isTokenType(TokenType.LEFT_BRACKET)) {
                consume(TokenType.LEFT_BRACKET);
                consume(TokenType.RIGHT_BRACKET);
                if (construct == null) {
                    construct = new Dims();
                }
                construct.addAnnotationList(annotations);
                return construct;
            } else {
                break;
            }
        }
        return construct;
    }

    @JlsChapter("4.3")
    private ReferenceType parseReferenceType() throws SyntaxException {
        trace("parseReferenceType");
        ReferenceType construct = new ReferenceType();
        save();
        construct.setArrayType(parseArrayType());
        if (construct.getArrayType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setTypeVariable(parseTypeVariable());
        if (construct.getTypeVariable() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassOrInterfaceType(parseClassOrInterfaceType());
        if (construct.getClassOrInterfaceType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @Here
    @JlsChapter("4.3")
    ClassType parseClassType() throws SyntaxException {
        trace("parseClassType");
        ClassType construct = new ClassType();
        save();
        while (true) {
            save();
            for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
                construct.addAnnotation(item);
            }
            construct.setTypeIdentifier(parseTypeIdentifier());
            if (construct.getTypeIdentifier() == null) {
                revert();
                return null;
            }
            construct.setTypeArguments(parseTypeArguments());

            if (construct.getTypeIdentifier() != null) {
                pop();
                // TODO: Packagename
                // TODO: How do we tell is this is a packagename?
                if (acceptAndConsume(TokenType.DOT)) {
                    ClassOrInterfaceType coit = new ClassOrInterfaceType(construct);
                    construct = new ClassType();
                    construct.setClassOrInterfaceType(coit);
                } else {
                    break;
                }
            } else {
                revert();
                break;
            }
        }

        // for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
        //     construct.addAnnotation(item);
        // }
        // construct.setTypeIdentifier(parseTypeIdentifier());
        // if (construct.getTypeIdentifier() == null) {
        //     revert();
        //     return null;
        // }

        // construct.setTypeArguments(parseTypeArguments());
        pop();
        return construct;




        // Old code...

        // save();
        // construct.setPackageName(parsePackageName());
        // if (construct.getPackageName() == null) {
        //     rewind();
        //     construct.setClassOrInterfaceType(parseClassOrInterfaceType());
        //     if (construct.getClassOrInterfaceType() == null) {
        //         rewind();
        //     }
        // }
        // if (construct.getPackageName()  != null || construct.getClassOrInterfaceType() != null) {
        //     if (!acceptAndConsume(TokenType.DOT)) {
        //         rewind();
        //         construct = new ClassType();
        //     }
        // }

        // for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
        //     construct.addAnnotation(item);
        // }
        // construct.setTypeIdentifier(parseTypeIdentifier());
        // if (construct.getTypeIdentifier() == null) {
        //     revert();
        //     return null;
        // }
        // construct.setTypeArguments(parseTypeArguments());
        // pop();
        // return construct;
    }

    @JlsChapter("4.3")
    private ArrayType parseArrayType() throws SyntaxException {
        trace("parseArrayType");
        ArrayType construct = new ArrayType();
        save();
        construct.setPrimitiveType(parsePrimitiveType());
        if (construct.getPrimitiveType() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
            revert();
            return null;
        }
        rewind();
        construct.setClassOrInterfaceType(parseClassOrInterfaceType());
        if (construct.getClassOrInterfaceType() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
            revert();
            return null;
        }
        rewind();
        construct.setTypeVariable(parseTypeVariable());
        if (construct.getTypeVariable() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
            revert();
            return null;
        }
        revert();
        return null;
    }

    @JlsChapter("4.3")
    private TypeVariable parseTypeVariable() throws SyntaxException {
        unimplemented("parseTypeVariable");
        return null;
    }

    @JlsChapter("4.3")
    private ClassOrInterfaceType parseClassOrInterfaceType() throws SyntaxException {
        trace("parseClassOrInterfaceType");
        ClassOrInterfaceType construct = new ClassOrInterfaceType();
        save();
        construct.setClassType(parseClassType());
        if (construct.getClassType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setInterfaceType(parseInterfaceType());
        if (construct.getInterfaceType() != null) {
            pop();
            return construct;
        }
        revert();
        return construct;
    }

    @JlsChapter("4.3")
    private InterfaceType parseInterfaceType() throws SyntaxException {
        trace("parseInterfaceType");
        InterfaceType construct = new InterfaceType();
        save();
        construct.setClassType(parseClassType());
        if (construct.getClassType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.4")
    private AdditionalBound parseAdditionalBound() throws SyntaxException {
        trace("parseAdditionalBound");
        AdditionalBound construct = new AdditionalBound();
        if (!isTokenType(TokenType.AMPERSAND)) {
            return null;
        }
        save();
        consume();
        construct.setInterfaceType(parseInterfaceType());
        if (construct.getInterfaceType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.4")
    private TypeParameter parseTypeParameter() throws SyntaxException {
        trace("parseTypeParameter");
        TypeParameter construct = new TypeParameter();
        save();
        for (TypeParameterModifier item = parseTypeParameterModifier(); item != null; item = parseTypeParameterModifier()) {
            construct.addTypeParameterModifier(item);
        }
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setTypeBound(parseTypeBound());
        pop();
        return construct;
    }

    @JlsChapter("4.4")
    private TypeParameterModifier parseTypeParameterModifier() throws SyntaxException {
        trace("parseTypeParameterModifier");
        TypeParameterModifier construct = new TypeParameterModifier();
        save();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("4.4")
    private TypeBound parseTypeBound() throws SyntaxException {
        trace("parseTypeBound");
        TypeBound construct = new TypeBound();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_EXTENDS)) {
            revert();
            return null;
        }
        save();
        construct.setTypeVariable(parseTypeVariable());
        if (construct.getTypeVariable() != null) {
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setClassOrInterfaceType(parseClassOrInterfaceType());
        if (construct.getClassOrInterfaceType() != null) {
            pop();
            pop();
            return construct;
        }
        revert();
        revert();
        return null;
    }

    @JlsChapter("4.5.1")
    TypeArguments parseTypeArguments() throws SyntaxException {
        trace("parseTypeArguments");
        TypeArguments construct = new TypeArguments();
        if (!isTokenType(TokenType.LESS_THAN)) {
            return null;
        }
        save();
        consume();
        construct.setTypeArgumentList(parseTypeArgumentList());
        if (construct.getTypeArgumentList() != null) {
            pop();
            if (isTokenType(TokenType.RIGHT_SHIFT)) {
                splitToken();
            } else if (isTokenType(TokenType.UNSIGNED_RIGHT_SHIFT)) {
                splitToken();
            }
            consume(TokenType.GREATER_THAN);
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.5.1")
    TypeArgumentList parseTypeArgumentList() throws SyntaxException {
        trace("parseTypeArgumentList");
        TypeArgumentList construct = new TypeArgumentList();
        save();
        TypeArgument item = parseTypeArgument();
        if (item == null) {
            revert();
            return null;
        }
        construct.addTypeArgument(item);

        while (acceptAndConsume(TokenType.COMMA)) {
            save();
            item = parseTypeArgument();
            if (item != null) {
                construct.addTypeArgument(item);
                pop();
            } else {
                revert();
                revert();
                return null;
            }
        }

        pop();
        return construct;
    }

    @JlsChapter("4.5.1")
    private TypeArgument parseTypeArgument() throws SyntaxException {
        trace("parseTypeArgument");
        TypeArgument construct = new TypeArgument();
        save();
        construct.setReferenceType(parseReferenceType());
        if (construct.getReferenceType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setWildcard(parseWildcard());
        if (construct.getWildcard() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("4.5.1")
    private Wildcard parseWildcard() throws SyntaxException {
        trace("parseWildcard");
        Wildcard construct = new Wildcard();

        save();
        while (true) {
            save();
            Annotation item = parseAnnotation();
            if (item == null) {
                rewind();
                break;
            }
            pop();
            construct.addAnnotation(item);
        }
        if (!isTokenType(TokenType.QUESTION_MARK)) {
            revert();
            return null;
        }
        pop();
        save();
        construct.setWildcardBounds(parseWildcardBounds());
        if (construct.getWildcardBounds() == null) {
            rewind();
        }
        pop();
        return construct;
    }

    @JlsChapter("4.5.1")
    private WildcardBounds parseWildcardBounds() throws SyntaxException {
        trace("parseWildcardBounds");
        WildcardBounds construct = new WildcardBounds();
        if (isTokenType(TokenType.KEYWORD_EXTENDS)) {
            construct.setExtends(true);
        } else if (isTokenType(TokenType.KEYWORD_SUPER)) {
            construct.setSuper(true);
        } else {
            return null;
        }
        save();
        construct.setReferenceType(parseReferenceType());
        if (construct.getReferenceType() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    //
    // Chapter 6 - Names
    //

    // TODO: Check if this is backwards
    @Here
    @JlsChapter("6.5")
    public ModuleName parseModuleName() throws SyntaxException {
        trace("parseModuleName");
        ModuleName construct = new ModuleName();
        save();
        construct.setIdentifier(parseIdentifier());
        if (isTokenType(TokenType.DOT)) {
            consume();
            construct.setModuleName(parseModuleName());
            if (construct.getModuleName() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    // TODO: Check if this is backwards
    @JlsChapter("6.5")
    private PackageName parsePackageName() throws SyntaxException {
        trace("parsePackageName");
        PackageName construct = new PackageName();
        save();
        construct.setIdentifier(parseIdentifier());
        if (isTokenType(TokenType.DOT)) {
            consume();
            construct.setPackageName(parsePackageName());
            if (construct.getPackageName() == null) {
                revert();
                return null;
            }
        }
        if (construct.getPackageName() == null && construct.getIdentifier() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("6.5")
    TypeName parseTypeName() throws SyntaxException {
        trace("parseTypeName");
        TypeName construct = new TypeName();
        save();
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        while (true) {
            save();
            if (!acceptAndConsume(TokenType.DOT)) {
                pop();
                break;
            }
            TypeIdentifier ti = parseTypeIdentifier();
            if (ti != null) {
                PackageOrTypeName packageOrTypeName = new PackageOrTypeName();
                packageOrTypeName.setPackageOrTypeName(construct.getPackageOrTypeName());
                packageOrTypeName.setIdentifier(construct.getTypeIdentifier().getIdentifier());
                construct.getChildren().remove(construct.getPackageOrTypeName());
                construct.getChildren().remove(construct.getTypeIdentifier());
                construct.setPackageOrTypeName(packageOrTypeName);
                construct.setTypeIdentifier(ti);
                pop();
            } else {
                revert();
                break;
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("6.5")
    PackageOrTypeName parsePackageOrTypeName() throws SyntaxException {
        trace("parsePackageOrTypeName");
        PackageOrTypeName construct = new PackageOrTypeName();
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        while (acceptAndConsume(TokenType.DOT)) {
            Identifier id = parseIdentifier();
            PackageOrTypeName packageOrTypeName = new PackageOrTypeName();
            packageOrTypeName.setPackageOrTypeName(construct.getPackageOrTypeName());
            packageOrTypeName.setIdentifier(construct.getIdentifier());
            construct.getChildren().remove(construct.getPackageOrTypeName());
            construct.getChildren().remove(construct.getIdentifier());
            construct.setPackageOrTypeName(packageOrTypeName);
            construct.setIdentifier(id);
            if (construct.getIdentifier() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    @Here
    @JlsChapter("6.5")
    ExpressionName parseExpressionName() throws SyntaxException {
        trace("parseExpressionName");
        ExpressionName construct = new ExpressionName();
        AmbiguousName ambiguousName;
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        while (acceptAndConsume(TokenType.DOT)) {
            ambiguousName = new AmbiguousName();
            if (construct.getName() instanceof AmbiguousName an) {
                ambiguousName.setAmbiguousName(an);
            } else {
                if (construct.getName() == null) {
                    ambiguousName.setAmbiguousName(null);
                } else {
                    reporter.reportError("Can't set ambiguous name");
                }
            }
            ambiguousName.setIdentifier(construct.getIdentifier());
            construct.getChildren().clear();
            // construct.getChildren().remove(construct.getIdentifier());
            // construct.getChildren().remove(construct.getName());
            construct.setIdentifier(parseIdentifier());
            construct.setName(ambiguousName);
            if (construct.getIdentifier() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("6.5")
    AmbiguousName parseAmbiguousName() throws SyntaxException {
        trace("parseAmbiguousName");
        AmbiguousName construct = new AmbiguousName();
        AmbiguousName name = construct;
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        while (acceptAndConsume(TokenType.DOT)) {
            construct = new AmbiguousName();
            construct.setAmbiguousName(name);
            construct.setIdentifier(parseIdentifier());
            if (construct.getIdentifier() == null) {
                revert();
                return null;
            }
            name = construct;
        }
        pop();
        return construct;
    }

    @JlsChapter("6.5")
    private MethodName parseMethodName() throws SyntaxException {
        trace("parseMethodName");
        MethodName construct = new MethodName();
        save();
        construct.setUnqualifiedMethodIdentifier(parseUnqualifiedMethodIdentifier());
        if (construct.getUnqualifiedMethodIdentifier() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    //
    // Chapter 7 - Structure - Packages and Modules
    //

    @Override
    @JlsChapter("7.3")
    public CompilationUnit parseCompilationUnit() throws SyntaxException {
        trace("parseCompilationUnit");
        CompilationUnit construct = new CompilationUnit();
        save();
        construct.setModularCompilationUnit(parseModularCompilationUnit());
        if (construct.getModularCompilationUnit() != null) {
            if (peek().type() != TokenType.EOF) {
                throw new UnexpectedTokenException(peek());
            }
            pop();
            return construct;
        }
        rewind();
        construct.setOrdinaryCompilationUnit(parseOrdinaryCompilationUnit());
        if (construct.getOrdinaryCompilationUnit() != null) {
            if (peek().type() != TokenType.EOF) {
                throw new ExpectedTokenException(TokenType.EOF, peek());
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("7.3")
    private OrdinaryCompilationUnit parseOrdinaryCompilationUnit() throws SyntaxException {
        trace("parseOrdinaryCompilationUnit");
        OrdinaryCompilationUnit construct = new OrdinaryCompilationUnit();
        construct.setPackageDeclaration(parsePackageDeclaration());
        save();
        for (ImportDeclaration item = parseImportDeclaration(); item != null; item = parseImportDeclaration()) {
            construct.addImportDeclaration(item);
        }
        for (TopLevelClassOrInterfaceDeclaration item = parseTopLevelClassOrInterfaceDeclaration(); item != null; item = parseTopLevelClassOrInterfaceDeclaration()) {
            construct.addTopLevelClassOrInterfaceDeclaration(item);
        }
        // if (construct.getPackageDeclaration() == null || (construct.getImportDeclarationList().isEmpty() && construct.getTopLevelClassOrInterfaceDeclarations().isEmpty())) {
        //     revert();
        //     return null;
        // }
        pop();
        return construct;
    }

    @JlsChapter("7.3")
    private ModularCompilationUnit parseModularCompilationUnit() throws SyntaxException {
        trace("parseModularCompilationUnit");
        ModularCompilationUnit construct = new ModularCompilationUnit();
        save();
        for (ImportDeclaration item = parseImportDeclaration(); item != null; item = parseImportDeclaration()) {
            construct.addImportDeclaration(item);
        }
        construct.setModuleDeclaration(parseModuleDeclaration());
        if (construct.getModuleDeclaration() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("7.4.1")
    private PackageDeclaration parsePackageDeclaration() throws SyntaxException {
        trace("parsePackageDeclaration");
        PackageDeclaration construct = new PackageDeclaration();
        save();
        for (PackageModifier item = parsePackageModifier(); item != null; item = parsePackageModifier()) {
            construct.getPackageModifiers().add(item);
            construct.addChild(item);
        }
        if (!isTokenType(TokenType.KEYWORD_PACKAGE)) {
            revert();
            return null;
        }
        consume(TokenType.KEYWORD_PACKAGE);
        Identifier item = parseIdentifier();
        construct.getIdentifiers().add(item);
        construct.addChild(item);
        while(isTokenType(TokenType.DOT)) {
            consume(TokenType.DOT);
            item = parseIdentifier();
            construct.getIdentifiers().add(item);
            construct.addChild(item);
        }
        consume(TokenType.SEMICOLON);
        pop();
        return construct;
    }

    @JlsChapter("7.4.1")
    private PackageModifier parsePackageModifier() throws SyntaxException {
        unimplemented("parsePackageModifier");
        return null;
    }

    @JlsChapter("7.5")
    private ImportDeclaration parseImportDeclaration() throws SyntaxException {
        trace("parseImportDeclaration");
        ImportDeclaration construct = new ImportDeclaration();
        save();
        construct.setSingleStaticImportDeclaration(parseSingleStaticImportDeclaration());
        if (construct.getSingleStaticImportDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setStaticImportOnDemandDeclaration(parseStaticImportOnDemandDeclaration());
        if (construct.getStaticImportOnDemandDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setTypeImportOnDemandDeclaration(parseTypeImportOnDemandDeclaration());
        if (construct.getTypeImportOnDemandDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setSingleModuleImportDeclaration(parseSingleModuleImportDeclaration());
        if (construct.getSingleModuleImportDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setSingleTypeImportDeclaration(parseSingleTypeImportDeclaration());
        if (construct.getSingleTypeImportDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("7.5")
    SingleTypeImportDeclaration parseSingleTypeImportDeclaration() throws SyntaxException {
        trace("parseSingleTypeImportDeclaration");
        SingleTypeImportDeclaration construct = new SingleTypeImportDeclaration();
        save();
        if (isTokenType(TokenType.KEYWORD_IMPORT)) {
            consume();
            construct.setTypeName(parseTypeName());
            if (construct.getTypeName() != null) {
                consume(TokenType.SEMICOLON);
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("7.5")
    private TypeImportOnDemandDeclaration parseTypeImportOnDemandDeclaration() throws SyntaxException {
        trace("parseTypeImportOnDemandDeclaration");
        TypeImportOnDemandDeclaration construct = new TypeImportOnDemandDeclaration();
        save();
        if (isTokenType(TokenType.KEYWORD_IMPORT)) {
            consume();
            construct.setPackageOrTypeName(parsePackageOrTypeName());
            if (construct.getPackageOrTypeName() != null) {
                if (!acceptAndConsume(TokenType.DOT)) {
                    revert();
                    return null;
                }
                if (!acceptAndConsume(TokenType.STAR)) {
                    revert();
                    return null;
                }
                consume(TokenType.SEMICOLON);
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("7.5")
    private SingleStaticImportDeclaration parseSingleStaticImportDeclaration() throws SyntaxException {
        trace("parseSingleStaticImportDeclaration");
        SingleStaticImportDeclaration construct = new SingleStaticImportDeclaration();
        save();
        if (peek().type() == TokenType.KEYWORD_IMPORT &&
            peek(2).type() == TokenType.KEYWORD_STATIC) {
            consume();
            consume();
            construct.setTypeName(parseTypeName());
            if (construct.getTypeName() == null) {
                revert();
                return null;
                // throw new ExpectedConstructException("type name", peek());
            }
            consume(TokenType.DOT);
            construct.setIdentifier(parseIdentifier());
            if (construct.getIdentifier() == null) {
                revert();
                return null;
            }
            consume(TokenType.SEMICOLON);
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("7.5")
    private StaticImportOnDemandDeclaration parseStaticImportOnDemandDeclaration() throws SyntaxException {
        trace("parseStaticImportOnDemandDeclaration");
        StaticImportOnDemandDeclaration construct = new StaticImportOnDemandDeclaration();
        save();
        if (peek().type() == TokenType.KEYWORD_IMPORT &&
            peek(2).type() == TokenType.KEYWORD_STATIC) {
            consume();
            consume();
            construct.setTypeName(parseTypeName());
            if (construct.getTypeName() == null) {
                revert();
                return null;
                // throw new ExpectedConstructException("type name", peek());
            }
            if (!acceptAndConsume(TokenType.DOT)) {
                revert();
                return null;
            }
            if (!acceptAndConsume(TokenType.STAR)) {
                revert();
                return null;
            }
            consume(TokenType.SEMICOLON);
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("7.5")
    private SingleModuleImportDeclaration parseSingleModuleImportDeclaration() throws SyntaxException {
        trace("parseSingleModuleImportDeclaration");
        SingleModuleImportDeclaration construct = new SingleModuleImportDeclaration();
        if (peek().type() == TokenType.KEYWORD_IMPORT &&
            peek(2).type() == TokenType.CONTEXTUAL_MODULE) {
            consume();
            consume();
            construct.setModuleName(parseModuleName());
            if (construct.getModuleName() != null) {
                return construct;
            }
            throw new ExpectedConstructException("module name", peek());
        }
        return null;
    }

    @JlsChapter("7.6")
    private TopLevelClassOrInterfaceDeclaration parseTopLevelClassOrInterfaceDeclaration() throws SyntaxException {
        trace("parseTopLevelClassOrInterfaceDeclaration");
        TopLevelClassOrInterfaceDeclaration construct = new TopLevelClassOrInterfaceDeclaration();
        save();
        construct.setInterfaceDeclaration(parseInterfaceDeclaration());
        if (construct.getInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassDeclaration(parseClassDeclaration());
        if (construct.getClassDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("7.7")
    private ModuleDeclaration parseModuleDeclaration() throws SyntaxException {
        trace("parseModuleDeclaration");
        save();
        ModuleDeclaration construct = new ModuleDeclaration();
        for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
            construct.addAnnotation(item);
        }
        if (acceptAndConsume(TokenType.KEYWORD_OPEN)) {
            construct.setOpen(true);
        }
        if (!acceptAndConsume(TokenType.CONTEXTUAL_MODULE)) {
            revert();
            return null;
        }
        Identifier item = parseIdentifier();
        if (item == null) {
            revert();
            return null;
        }
        construct.addIdentifier(item);
        while (acceptAndConsume(TokenType.DOT)) {
            item = parseIdentifier();
            if (item == null) {
                revert();
                return null;
            }
            construct.addIdentifier(item);
        }
        consume(TokenType.LEFT_BRACE);
        for (ModuleDirective directive = parseModuleDirective(); directive != null; directive=parseModuleDirective()) {
            construct.addModuleDirective(directive);
        }
        consume(TokenType.RIGHT_BRACE);
        pop();
        return construct;
    }

    @JlsChapter("7.7")
    private ModuleDirective parseModuleDirective() throws SyntaxException {
        trace("parseModuleDirective");
        save();
        ModuleDirective construct = new ModuleDirective();
        switch(peek().type()) {
            case CONTEXTUAL_REQUIRES:
                consume();
                if (parseRequiresModuleDirective(construct)) {
                    pop();
                    return construct;
                }
                break;
            case CONTEXTUAL_EXPORTS:
                consume();
                if (parseExportsOrOpensModuleDirective(construct)) {
                    pop();
                    return construct;
                }
                break;
            case CONTEXTUAL_OPENS:
                consume();
                if (parseExportsOrOpensModuleDirective(construct)) {
                    pop();
                    return construct;
                }
                break;
            case CONTEXTUAL_USES:
                consume();
                if (parseUsesModuleDirective(construct)) {
                    pop();
                    return construct;
                }
                break;
            case CONTEXTUAL_PROVIDES:
                consume();
                if (parseProvidesModuleDirective(construct)) {
                    pop();
                    return construct;
                }
                break;
            default:
        }
        revert();
        return null;
    }

    @JlsChapter("7.7")
    private boolean parseRequiresModuleDirective(ModuleDirective construct) throws SyntaxException {
        for (RequiresModifier item = parseRequiresModifier(); item != null; item = parseRequiresModifier()) {
            construct.addRequiresModifier(item);
        }
        construct.setRequiresModuleName(parseModuleName());
        if (construct.getRequiresModuleName() == null) {
            return false;
        }
        consume(TokenType.SEMICOLON);
        return true;
    }

    @JlsChapter("7.7")
    private boolean parseExportsOrOpensModuleDirective(ModuleDirective construct) throws SyntaxException {
        construct.setExportsPackageName(parsePackageName());
        if (construct.getExportsPackageName() == null) {
            return false;
        }
        if (acceptAndConsume(TokenType.CONTEXTUAL_TO)) {
            ModuleName item = parseModuleName();
            if (item == null) {
                return false;
            }
            construct.addToModule(item);
            while (acceptAndConsume(TokenType.COMMA)) {
                item = parseModuleName();
                if (item == null) {
                    return false;
                }
                construct.addToModule(item);
            }
        }
        consume(TokenType.SEMICOLON);
        return true;
    }

    @JlsChapter("7.7")
    private boolean parseUsesModuleDirective(ModuleDirective construct) throws SyntaxException {
        construct.setUsesTypeName(parseTypeName());
        if (construct.getUsesTypeName() == null) {
            return false;
        }
        consume(TokenType.SEMICOLON);
        return true;
    }

    @JlsChapter("7.7")
    private boolean parseProvidesModuleDirective(ModuleDirective construct) throws SyntaxException {
        construct.setProvidesTypeName(parseTypeName());
        if (construct.getProvidesTypeName() == null) {
            return false;
        }
        consume(TokenType.CONTEXTUAL_WITH);
        TypeName item = parseTypeName();
        if (item == null) {
            return false;
        }
        construct.addWithTypename(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            item = parseTypeName();
            if (item == null) {
                return false;
            }
            construct.addWithTypename(item);
        }
        consume(TokenType.SEMICOLON);
        return true;
    }

    @JlsChapter("7.7")
    private RequiresModifier parseRequiresModifier() throws SyntaxException {
        trace("parseRequiresModifier");
        RequiresModifier construct = new RequiresModifier();
        if (acceptAndConsume(TokenType.KEYWORD_STATIC)) {
            construct.setStatic(true);
            return construct;
        } else if (acceptAndConsume(TokenType.CONTEXTUAL_TRANSITIVE)) {
            construct.setTransitive(true);
            return construct;
        } else {
            return null;
        }
    }

    //
    // Chapter 8 - Classes
    //

    @JlsChapter("8.1")
    ClassDeclaration parseClassDeclaration() throws SyntaxException {
        trace("parseClassDeclaration");
        ClassDeclaration construct = new ClassDeclaration();
        save();
        construct.setEnumDeclaration(parseEnumDeclaration());
        if (construct.getEnumDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setRecordDeclaration(parseRecordDeclaration());
        if (construct.getRecordDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setNormalClassDeclaration(parseNormalClassDeclaration());
        if (construct.getNormalClassDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;


        // int lookahead = countClassModifiers();
        // TokenType type = peek(lookahead).type();
        // ASTNode declaration = null;
        // if (canParseTopLevelDeclaration()) {
        //     switch (type) {
        //         case KEYWORD_CLASS:
        //             construct.setNormalClassDeclaration(parseNormalClassDeclaration());
        //             if (construct.getNormalClassDeclaration() != null) {
        //                 return construct;
        //             }
        //             return null;
        //         case KEYWORD_ENUM:
        //             construct.setEnumDeclaration(parseEnumDeclaration());
        //             if (construct.getEnumDeclaration() != null) {
        //                 return construct;
        //             }
        //             return null;
        //         case CONTEXTUAL_RECORD:
        //             construct.setRecordDeclaration(parseRecordDeclaration());
        //             if (construct.getRecordDeclaration() != null) {
        //                 return construct;
        //             }
        //             return null;
        //         default:
        //             return null;
        //     }
        // }
        // return null;
    }

    /// @see https://docs.oracle.com/javase/specs/jls/se25/html/jls-8.html#jls-ClassDeclaration
    @JlsChapter("8.1")
    NormalClassDeclaration parseNormalClassDeclaration() throws SyntaxException {
        trace("parseNormalClassDeclaration");
        NormalClassDeclaration construct = new NormalClassDeclaration();
        save();
        for (ClassModifier item = parseClassModifier(); item != null; item = parseClassModifier()) {
            construct.addClassModifier(item);
        }
        // List<ClassModifier> modifiers = parseClassModifiers();
        if (!acceptAndConsume(TokenType.KEYWORD_CLASS)) {
            revert();
            return null;
            // This indicates a lookahead failure; should not happen if called correctly
            // throw new ExpectedTokenException(TokenType.KEYWORD_CLASS, peek());
        }
        // consume(); // Consume 'class'
        // for (ClassModifier m : modifiers) {
        //     construct.addClassModifier(m);
        // }

        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        //TODO: Might neet to rewind on some of thse?
        construct.setTypeParameters(parseTypeParameters());
        construct.setClassExtends(parseClassExtends());
        construct.setClassImplements(parseClassImplements());
        construct.setClassPermits(parseClassPermits());
        construct.setClassBody(parseClassBody());
        if (construct.getClassBody() == null) {
            revert();
            return null;
        }

        pop();
        return construct;
    }

    @JlsChapter("8.1.1")
    private ClassModifier parseClassModifier() throws SyntaxException {
        trace("parseClassModifier");
        ClassModifier construct = new ClassModifier();
        save();
        TokenType type = peek().type();
        /// Annotation public protected private
        /// abstract static final sealed non-sealed strictfp
        switch (type) {
            case KEYWORD_PUBLIC, KEYWORD_PROTECTED, KEYWORD_PRIVATE,
                KEYWORD_ABSTRACT, KEYWORD_STATIC, KEYWORD_FINAL,
                CONTEXTUAL_SEALED, CONTEXTUAL_NON_SEALED, KEYWORD_STRICTFP:
                construct.setValue(type);
                consume();
                pop();
                return construct;
            default:
                construct.setAnnotation(parseAnnotation());
                if (construct.getAnnotation() != null) {
                    pop();
                    return construct;
                }
                revert();
                return null;
        }
    }

    @JlsChapter("8.1.2")
    private TypeParameters parseTypeParameters() throws SyntaxException {
        trace("parseTypeParameters");
        TypeParameters construct = new TypeParameters();
        save();
        if (!acceptAndConsume(TokenType.LESS_THAN)) {
            revert();
            return null;
        }
        construct.setTypeParameterList(parseTypeParameterList());
        if (construct.getTypeParameterList() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.GREATER_THAN)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.1.2")
    private TypeParameterList parseTypeParameterList() throws SyntaxException {
        trace("parseTypeParameterList");
        TypeParameterList construct = new TypeParameterList();
        save();
        TypeParameter item = parseTypeParameter();
        if (item == null) {
            revert();
            return null;
        }
        construct.addTypeParameter(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            item = parseTypeParameter();
            if (item == null) {
                revert();
                return null;
            }
            construct.addTypeParameter(item);
        }
        pop();
        return construct;
    }

    @JlsChapter("8.1.4")
    private ClassExtends parseClassExtends() throws SyntaxException {
        trace("parseClassExtends");
        save();
        if (acceptAndConsume(TokenType.KEYWORD_EXTENDS)) {
            ClassExtends construct = new ClassExtends();
            construct.setClassType(parseClassType());
            if (construct.getClassType() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("8.1.5")
    private ClassImplements parseClassImplements() throws SyntaxException {
        trace("parseClassImplements");
        save();
        if (acceptAndConsume(TokenType.KEYWORD_IMPLEMENTS)) {
            ClassImplements construct = new ClassImplements();
            construct.setInterfaceTypeList(parseInterfaceTypeList());
            if (construct.getInterfaceTypeList() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("8.1.5")
    private InterfaceTypeList parseInterfaceTypeList() throws SyntaxException {
        trace("parseInterfaceTypeList");
        InterfaceTypeList construct = new InterfaceTypeList();
        save();
        InterfaceType item = parseInterfaceType();
        if (item == null) {
            revert();
            return null;
        }
        construct.addInterfaceType(item);
        while(acceptAndConsume(TokenType.COMMA)) {
            save();
            item = parseInterfaceType();
            if (item == null) {
                revert();
                return null;
            }
            pop();
            construct.addInterfaceType(item);
        }
        pop();
        return construct;
    }

    @JlsChapter("8.1.6")
    private ClassPermits parseClassPermits() throws SyntaxException {
        trace("parseClassPermits");
        if (isTokenType(TokenType.CONTEXTUAL_PERMITS)) {
            ClassPermits construct = new ClassPermits();
            consume();
            construct.addTypeName(parseTypeName());
            while (isTokenType(TokenType.COMMA)) {
                consume();
                construct.addTypeName(parseTypeName());
            }
            return construct;
        }
        return null;
    }

    @JlsChapter("8.1.7")
    private ClassBody parseClassBody() throws SyntaxException {
        trace("parseClassBody");
        if (!isTokenType(TokenType.LEFT_BRACE)) {
            return null;
        }
        ClassBody construct = new ClassBody();
        consume(TokenType.LEFT_BRACE);
        while (!isTokenType(TokenType.RIGHT_BRACE)) {
            trace("HELP");
            if (tokenIndex > 2245) {
                System.out.println(); //Something consumed the } when it shouldnt
            }
            ClassBodyDeclaration item = parseClassBodyDeclaration();
            if (item != null) {
                construct.getClassBodyDeclarations().add(item);
                construct.addChild(item);
            } else {
                break;
            }
        }
        consume(TokenType.RIGHT_BRACE);
        return construct;
    }

    @JlsChapter("8.1.7")
    private ClassBodyDeclaration parseClassBodyDeclaration() throws SyntaxException {
        trace("parseClassBodyDeclaration");
        ClassBodyDeclaration construct = new ClassBodyDeclaration();
        save();
        construct.setClassMemberDeclaration(parseClassMemberDeclaration());
        if (construct.getClassMemberDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setInstanceInitializer(parseInstanceInitializer());
        if (construct.getInstanceInitializer() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setStaticInitializer(parseStaticInitializer());
        if (construct.getStaticInitializer() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setConstructorDeclaration(parseConstructorDeclaration());
        if (construct.getConstructorDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.1.7")
    private ClassMemberDeclaration parseClassMemberDeclaration() throws SyntaxException {
        trace("parseClassMemberDeclaration");
        ClassMemberDeclaration construct = new ClassMemberDeclaration();
        if (acceptAndConsume(TokenType.SEMICOLON)) {
            return construct;
        }
        save();
        construct.setInterfaceDeclaration(parseInterfaceDeclaration());
        if (construct.getInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassDeclaration(parseClassDeclaration());
        if (construct.getClassDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setMethodDeclaration(parseMethodDeclaration());
        if (construct.getMethodDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setFieldDeclaration(parseFieldDeclaration());
        if (construct.getFieldDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.3")
    private VariableDeclaratorList parseVariableDeclaratorList() throws SyntaxException {
        trace("parseVariableDeclaratorList");
        VariableDeclaratorList construct = new VariableDeclaratorList();
        save();
        VariableDeclarator decl = parseVariableDeclarator();
        if (decl == null) {
            revert();
            return null;
        }
        construct.addVariableDeclarator(decl);
        while(isTokenType(TokenType.COMMA)) {
            consume();
            VariableDeclarator item = parseVariableDeclarator();
            construct.addVariableDeclarator(item); //TODO return null if this is null
        }
        pop();
        return construct;
    }

    @JlsChapter("8.3")
    VariableDeclarator parseVariableDeclarator() throws SyntaxException {
        trace("parseVariableDeclarator");
        VariableDeclarator construct = new VariableDeclarator();
        save();
        construct.setVariableDeclaratorId(parseVariableDeclaratorId());
        if (construct.getVariableDeclaratorId() == null) {
            revert();
            return null;
        }
        if (isTokenType(TokenType.EQUALS)) {
            consume();
            construct.setVariableInitializer(parseVariableInitializer());
        }
        pop();
        return construct;
    }

    @JlsChapter("8.3")
    private FieldDeclaration parseFieldDeclaration() throws SyntaxException {
        trace("parseFieldDeclaration");
        FieldDeclaration construct = new FieldDeclaration();
        save();
        for (FieldModifier item = parseFieldModifier(); item != null; item = parseFieldModifier()) {
            construct.addFieldModifier(item);
        }
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        construct.setVariableDeclaratorList(parseVariableDeclaratorList());
        if (construct.getVariableDeclaratorList() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.SEMICOLON)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.3")
    private VariableDeclaratorId parseVariableDeclaratorId() throws SyntaxException {
        trace("parseVariableDeclaratorId");
        VariableDeclaratorId construct = new VariableDeclaratorId();
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        construct.setDims(parseDims());
        pop();
        return construct;
    }

    @JlsChapter("8.3")
    private UnannType parseUnannType() throws SyntaxException {
        trace("parseUnannType");
        UnannType construct = new UnannType();
        save();
        construct.setUnannPrimitiveType(parseUnannPrimitiveType());
        if (construct.getUnannPrimitiveType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setUnannReferenceType(parseUnannReferenceType());
        if (construct.getUnannReferenceType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.3")
    private UnannPrimitiveType parseUnannPrimitiveType() throws SyntaxException {
        trace("parseUnannPrimitiveType");
        UnannPrimitiveType construct = new UnannPrimitiveType();
        if (isTokenType(TokenType.KEYWORD_BOOLEAN)) {
            consume();
            construct.setBoolean(true);
            return construct;
        }
        save();
        construct.setNumericType(parseNumericType());
        if (construct.getNumericType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.3")
    private UnannReferenceType parseUnannReferenceType() throws SyntaxException {
        trace("parseUnannReferenceType");
        UnannReferenceType construct = new UnannReferenceType();
        save();
        construct.setUnannArrayType(parseUnannArrayType());
        if (construct.getUnannArrayType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setUnannClassOrInterfaceType(parseUnannClassOrInterfaceType());
        if (construct.getUnannClassOrInterfaceType() != null) {
            pop();
            return construct;
        }
        rewind();
        // TODO: How do we tell the diffrence between a TypeVariable and a class type?
        construct.setUnannTypeVariable(parseUnannTypeVariable());
        if (construct.getUnannTypeVariable() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.3")
    private UnannClassOrInterfaceType parseUnannClassOrInterfaceType() throws SyntaxException {
        trace("parseUnannClassOrInterfaceType");
        UnannClassOrInterfaceType construct = new UnannClassOrInterfaceType();
        save();
        construct.setUnannClassType(parseUnannClassType());
        if (construct.getUnannClassType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setUnannInterfaceType(parseUnannInterfaceType());
        if (construct.getUnannInterfaceType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @Here
    @JlsChapter("8.3")
    UnannClassType parseUnannClassType() throws SyntaxException {
        trace("parseUnannClassType");
        UnannClassType construct = new UnannClassType();

        save();
        // Not sure about this...
        for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
            construct.addAnnotation(item);
        }

        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setTypeArguments(parseTypeArguments());
        // pop();
        // TODO: Tidy this up


        //====================================================
        if (isTokenType(TokenType.DOT)) {
            save();
            consume();
            construct.setPackageName(parsePackageName());
            if (construct.getPackageName() != null) {
                pop();
                pop();
                return construct;
            }
            rewind();
            construct.setUnannClassOrInterfaceType(parseUnannClassOrInterfaceType());
            if (construct.getUnannClassOrInterfaceType() != null) {
                pop();
                pop();
                return construct;
            }
            revert();
        }

        // Should only need to check getTypeIdentifier
        if (construct.getTypeIdentifier() == null && construct.getPackageName() == null && construct.getUnannClassOrInterfaceType() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.3")
    private UnannInterfaceType parseUnannInterfaceType() throws SyntaxException {
        trace("parseUnannInterfaceType");
        UnannInterfaceType construct = new UnannInterfaceType();
        construct.setUnannClassType(parseUnannClassType());
        if (construct.getUnannClassType() != null) {
            return construct;
        }
        return null;
    }

    @JlsChapter("8.3")
    private UnannTypeVariable parseUnannTypeVariable() throws SyntaxException {
        trace("parseUnannTypeVariable");
        UnannTypeVariable construct = new UnannTypeVariable();
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() != null) {
            return construct;
        }
        return null;
    }

    @JlsChapter("8.3")
    private UnannArrayType parseUnannArrayType() throws SyntaxException {
        trace("parseUnannArrayType");
        UnannArrayType construct = new UnannArrayType();
        save();
        construct.setUnannPrimitiveType(parseUnannPrimitiveType());
        if (construct.getUnannPrimitiveType() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
        }
        rewind();
        construct.setUnannClassOrInterfaceType(parseUnannClassOrInterfaceType());
        if (construct.getUnannClassOrInterfaceType() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
        }
        rewind();
        construct.setUnannTypeVariable(parseUnannTypeVariable());
        if (construct.getUnannTypeVariable() != null) {
            construct.setDims(parseDims());
            if (construct.getDims() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("8.3")
    VariableInitializer parseVariableInitializer() throws SyntaxException {
        trace("parseVariableInitializer");
        VariableInitializer construct = new VariableInitializer();
        save();
        construct.setExpression(parseExpression());
        if (construct.getExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setArrayInitializer(parseArrayInitializer());
        if (construct.getArrayInitializer() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.3.1")
    private FieldModifier parseFieldModifier() throws SyntaxException {
        trace("parseFieldModifier");
        FieldModifier construct = new FieldModifier();
        save();
        TokenType type = peek().type();
        /// Annotation public protected private
        /// static final transient volatile
        switch (type) {
            case KEYWORD_PUBLIC, KEYWORD_PROTECTED, KEYWORD_PRIVATE,
                KEYWORD_STATIC, KEYWORD_FINAL, KEYWORD_TRANSIENT,
                KEYWORD_VOLATILE:
                construct.setTokenType(type);
                consume();
                pop();
                return construct;
            default:
                construct.setAnnotation(parseAnnotation());
                if (construct.getAnnotation() != null) {
                    pop();
                    return construct;
                }
                revert();
                return null;
        }
    }

    @JlsChapter("8.4")
    MethodDeclaration parseMethodDeclaration() throws SyntaxException {
        trace("parseMethodDeclaration");
        MethodDeclaration construct = new MethodDeclaration();
        save();
        for (MethodModifier item = parsMethodModifier(); item != null; item = parsMethodModifier()) {
            construct.addMethodModifier(item);
        }
        construct.setMethodHeader(parseMethodHeader());
        if (construct.getMethodHeader() == null) {
            revert();
            return null;
        }
        construct.setMethodBody(parseMethodBody());
        if (construct.getMethodBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.4")
    private MethodHeader parseMethodHeader() throws SyntaxException {
        trace("parseMethodHeader");
        MethodHeader construct = new MethodHeader();
        save();
        construct.setTypeParameters(parseTypeParameters());
        if (construct.getTypeParameters() != null) {
            for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
                construct.addAnnotation(item);
            }
        }
        construct.setResult(parseResult());
        if (construct.getResult() == null) {
            revert();
            return null;
        }
        construct.setMethodDeclarator(parseMethodDeclarator());
        if (construct.getMethodDeclarator() == null) {
            revert();
            return null;
        }
        construct.setThrowsClause(parseThrowsClause());
        pop();
        return construct;
    }

    @JlsChapter("8.4")
    private MethodDeclarator parseMethodDeclarator() throws SyntaxException {
        trace("parseMethodDeclarator");
        MethodDeclarator construct = new MethodDeclarator();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            return null;
        }
        save();
        if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        construct.setReceiverParameter(parseReceiverParameter());
        if (construct.getReceiverParameter() != null) {
            if (!acceptAndConsume(TokenType.COMMA)) {
                rewind();
            }
        }
        pop();

        construct.setFormalParameterList(parseFormalParameterList());
        consume(TokenType.RIGHT_PAREN);
        construct.setDims(parseDims());
        return construct;
    }

    @JlsChapter("8.4")
    private ReceiverParameter parseReceiverParameter() throws SyntaxException {
        unimplemented("parseReceiverParameter");
        return null;
    }

    @JlsChapter("8.4.1")
    private FormalParameterList parseFormalParameterList() throws SyntaxException {
        trace("parseFormalParameterList");
        FormalParameterList construct = new FormalParameterList();
        save();
        FormalParameter param = parseFormalParameter();
        if (param != null) {
            construct.addFormalParameter(param);
            while(isTokenType(TokenType.COMMA)) {
                consume();
                FormalParameter item = parseFormalParameter();
                construct.addFormalParameter(item);
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.4.1")
    private FormalParameter parseFormalParameter() throws SyntaxException {
        trace("parseFormalParameter");
        FormalParameter construct = new FormalParameter();
        save();
        for (VariableModifier item = parseVariableModifier(); item != null; item = parseVariableModifier()) {
            construct.addVariableModifier(item);
        }
        construct.setVariableArityParameter(parseVariableArityParameter());
        if (construct.getVariableArityParameter() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        construct.setVariableDeclaratorId(parseVariableDeclaratorId());
        if (construct.getVariableDeclaratorId() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.4.1")
    private VariableArityParameter parseVariableArityParameter() throws SyntaxException {
        unimplemented("parseVariableArityParameter");
        return null;
    }

    @JlsChapter("8.4.1")
    private VariableModifier parseVariableModifier() throws SyntaxException {
        unimplemented("parseVariableModifier");
        return null;
    }

    @JlsChapter("8.4.3")
    private MethodModifier parsMethodModifier() throws SyntaxException {
        trace("parsMethodModifier");
        MethodModifier construct = new MethodModifier();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() != null) {
            return construct;
        }
        switch(peek().type()) {
            case KEYWORD_PUBLIC, KEYWORD_PROTECTED, KEYWORD_PRIVATE, KEYWORD_ABSTRACT,
                    KEYWORD_STATIC, KEYWORD_FINAL, KEYWORD_SYNCHRONIZED, KEYWORD_NATIVE,
                    KEYWORD_STRICTFP:
                construct.setValue(peek().type());
                consume();
                return construct;
            default:
                // Don't do anything other than return null
        }
        return null;
    }

    @JlsChapter("8.4.5")
    private Result parseResult() throws SyntaxException {
        trace("parseResult");
        Result construct = new Result();
        if (isTokenType(TokenType.KEYWORD_VOID)) {
            consume();
            construct.setVoid(true);
        } else {
            construct.setUnannType(parseUnannType());
        }
        return construct;
    }

    @JlsChapter("8.4.6")
    private Throws parseThrows() throws SyntaxException {
        trace("parseThrows");
        Throws construct = new Throws();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_THROWS)) {
            construct.setExceptionTypeList(parseExceptionTypeList());
            if (construct.getExceptionTypeList() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("8.4.7")
    private MethodBody parseMethodBody() throws SyntaxException {
        trace("parseMethodBody");
        MethodBody construct = new MethodBody();
        save();
        if (isTokenType(TokenType.SEMICOLON)) {
            consume();
        } else {
            construct.setBlock(parseBlock());
            if (construct.getBlock() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("8.5.6")
    Throws parseThrowsClause() throws SyntaxException {
        trace("parseThrowsClause");
        Throws construct = new Throws();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_THROWS)) {
            construct.setExceptionTypeList(parseExceptionTypeList());
            if (construct.getExceptionTypeList() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("8.5.6")
    private ExceptionTypeList parseExceptionTypeList() throws SyntaxException {
        trace("parseExceptionTypeList");
        ExceptionTypeList construct = new ExceptionTypeList();
        save();
        ExceptionType item = parseExceptionType();
        if (item == null) {
            revert();
            return null;
        }
        construct.addExceptionType(item);
        while(acceptAndConsume(TokenType.COMMA)) {
            item = parseExceptionType();
            if (item == null) {
                revert();
                return null;
            }
            construct.addExceptionType(item);
        }
        pop();
        return construct;
    }

    @JlsChapter("8.5.6")
    private ExceptionType parseExceptionType() throws SyntaxException {
        ExceptionType construct = new ExceptionType();
        save();
        construct.setClassType(parseClassType());
        if (construct.getClassType() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setTypeVariable(parseTypeVariable());
        if (construct.getTypeVariable() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.6")
    private InstanceInitializer parseInstanceInitializer() throws SyntaxException {
        unimplemented("parseInstanceInitializer");
        return null;
    }

    @JlsChapter("8.7")
    private StaticInitializer parseStaticInitializer() throws SyntaxException {
        unimplemented("parseStaticInitializer");
        return null;
    }

    @JlsChapter("8.8")
    ConstructorDeclaration parseConstructorDeclaration() throws SyntaxException {
        trace("parseConstructorDeclaration");
        ConstructorDeclaration construct = new ConstructorDeclaration();
        save();
        for (ConstructorModifier item = parseConstructorModifier(); item != null; item = parseConstructorModifier()) {
            construct.addConstructorModifier(item);
        }
        construct.setConstructorDeclarator(parseConstructorDeclarator());
        if (construct.getConstructorDeclarator() == null) {
            revert();
            return null;
        }
        construct.setThrows(parseThrows());
        construct.setConstructorBody(parseConstructorBody());
        if (construct.getConstructorBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.8")
    private ConstructorDeclarator parseConstructorDeclarator() throws SyntaxException {
        trace("parseConstructorDeclarator");
        ConstructorDeclarator construct = new ConstructorDeclarator();
        save();
        construct.setTypeParameters(parseTypeParameters());
        construct.setSimpleTypeName(parseSimpleTypeName());
        if (construct.getSimpleTypeName() == null) {
            revert();
            return null;
        }
        if (!isTokenType(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        consume();

        save();
        construct.setReceiverParameter(parseReceiverParameter());
        if (construct.getReceiverParameter() != null) {
            if (!acceptAndConsume(TokenType.COMMA)) {
                rewind();
            }
        }
        pop();

        construct.setFormalParameterList(parseFormalParameterList());

        consume(TokenType.RIGHT_PAREN);
        pop();
        return construct;
    }

    @JlsChapter("8.8")
    private SimpleTypeName parseSimpleTypeName() throws SyntaxException {
        trace("parseSimpleTypeName");
        SimpleTypeName construct = new SimpleTypeName();
        save();
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.8.3")
    private ConstructorModifier parseConstructorModifier() throws SyntaxException {
        trace("parseConstructorModifier");
        ConstructorModifier construct = new ConstructorModifier();
        switch(peek().type()) {
            case KEYWORD_PUBLIC:
                consume();
                construct.setPublic(true);
                return construct;
            case KEYWORD_PROTECTED:
                consume();
                construct.setProtected(true);
                return construct;
            case KEYWORD_PRIVATE:
                consume();
                construct.setPrivate(true);
                return construct;
            default:
                save();
                construct.setAnnotation(parseAnnotation());
                if (construct.getAnnotation() != null) {
                    pop();
                    return construct;
                }
                revert();
        }
        return null;
    }

    @JlsChapter("8.8.7")
    private ConstructorBody parseConstructorBody() throws SyntaxException {
        trace("parseConstructorBody");
        save();
        if (!acceptAndConsume(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        ConstructorBody construct = new ConstructorBody();
        construct.setBlockStatements(parseBlockStatements());
        construct.setConstructorInvocation(parseConstructorInvocation());
        construct.setMoreBlockStatements(parseBlockStatements());
        if (isTokenType(TokenType.RIGHT_BRACE)) {
            consume();
            pop();
            return construct;
        }
        rewind();
        construct.setBlockStatements(parseBlockStatements());
        if (isTokenType(TokenType.RIGHT_BRACE)) {
            consume();
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.8.7.1")
    private ConstructorInvocation parseConstructorInvocation() throws SyntaxException {
        trace("parseConstructorInvocation");
        ConstructorInvocation construct = new ConstructorInvocation();
        save();
        construct.setTypeArguments(parseTypeArguments());
        if (isTokenType(TokenType.KEYWORD_THIS)) {
            consume();
            if (acceptAndConsume(TokenType.LEFT_PAREN)) {
                construct.setArgumentList(parseArgumentList());
                consume(TokenType.RIGHT_PAREN);
                consume(TokenType.SEMICOLON);
                pop();
                return construct;
            } else {
                rewind();
            }
        }
        rewind();
        construct.setExpressionName(parseExpressionName());
        if (construct.getExpressionName() == null) {
            rewind();
            construct.setPrimary(parsePrimary(-1, -1, -1));
            if (construct.getPrimary() == null) {
                rewind();
            }
        }

        construct.setTypeArguments(parseTypeArguments());
        if (isTokenType(TokenType.KEYWORD_SUPER)) {
            consume();
            consume(TokenType.LEFT_PAREN);
            construct.setArgumentList(parseArgumentList());
            consume(TokenType.RIGHT_PAREN);
            consume(TokenType.SEMICOLON);
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.9")
    private EnumDeclaration parseEnumDeclaration() throws SyntaxException {
        trace("parseEnumDeclaration");
        EnumDeclaration construct = new EnumDeclaration();
        save();
        for (ClassModifier item = parseClassModifier(); item != null; item = parseClassModifier()) {
            construct.addClassModifier(item);
        }
        if (!acceptAndConsume(TokenType.KEYWORD_ENUM)) {
            revert();
            return null;
        }
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setClassImplements(parseClassImplements());
        construct.setEnumBody(parseEnumBody());
        if (construct.getEnumBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.9.1")
    private EnumBody parseEnumBody() throws SyntaxException {
        trace("parseEnumBody");
        EnumBody construct = new EnumBody();
        save();
        if (!acceptAndConsume(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        construct.setEnumConstantList(parseEnumConstantList());
        if (acceptAndConsume(TokenType.COMMA)) {
            construct.setHasComma(true);
        }
        construct.setEnumBodyDeclarations(parseEnumBodyDeclarations());
        if (!acceptAndConsume(TokenType.RIGHT_BRACE)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.9.1")
    private EnumConstantList parseEnumConstantList() throws SyntaxException {
        trace("parseEnumConstantList");
        EnumConstantList construct = new EnumConstantList();
        save();
        EnumConstant item = parseEnumConstant();
        if (item == null) {
            revert();
            return null;
        }
        construct.addEnumConstant(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            save();
            item = parseEnumConstant();
            if (item == null) {
                revert();
                revert();
                return null;
            }
            construct.addEnumConstant(item);
            pop();
        }
        pop();
        return construct;
    }

    @JlsChapter("8.9.1")
    private EnumConstant parseEnumConstant() throws SyntaxException {
        trace("parseEnumConstant");
        EnumConstant construct = new EnumConstant();
        save();
        for (EnumConstantModifier item = parseEnumConstantModifier(); item != null; item = parseEnumConstantModifier()) {
            construct.addEnumConstantModifier(item);
        }
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        if (acceptAndConsume(TokenType.LEFT_PAREN)) {
            construct.setArgumentList(parseArgumentList());
            if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
                revert();
                return null;
            }
        }
        construct.setClassBody(parseClassBody());
        return construct;
    }

    @JlsChapter("8.9.1")
    private EnumConstantModifier parseEnumConstantModifier() throws SyntaxException {
        trace("parseEnumConstantModifier");
        EnumConstantModifier construct = new EnumConstantModifier();
        save();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    private EnumBodyDeclarations parseEnumBodyDeclarations() throws SyntaxException {
        trace("parseEnumBodyDeclarations");
        EnumBodyDeclarations construct = new EnumBodyDeclarations();
        if (!acceptAndConsume(TokenType.SEMICOLON)) {
            return null;
        }
        for (ClassBodyDeclaration item = parseClassBodyDeclaration(); item != null; item = parseClassBodyDeclaration()) {
            construct.addClassBodyDeclaration(item);
        }
        return construct;
    }

    @JlsChapter("8.10")
    private RecordDeclaration parseRecordDeclaration() throws SyntaxException {
        trace("parseRecordDeclaration");
        RecordDeclaration construct = new RecordDeclaration();
        save();
        for (ClassModifier item = parseClassModifier(); item != null; item = parseClassModifier()) {
            construct.addClassModifier(item);
        }
        if (!acceptAndConsume(TokenType.CONTEXTUAL_RECORD)) {
            revert();
            return null;
        }
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setTypeParameters(parseTypeParameters());
        construct.setRecordHeader(parseRecordHeader());
        if (construct.getRecordHeader() == null) {
            revert();
            return null;
        }
        construct.setClassImplements(parseClassImplements());
        construct.setRecordBody(parseRecordBody());
        if (construct.getRecordBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.1")
    private RecordHeader parseRecordHeader() throws SyntaxException {
        trace("parseRecordHeader");
        RecordHeader construct = new RecordHeader();
        save();
        if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        construct.setRecordComponentList(parseRecordComponentList());
        if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.1")
    private RecordComponentList parseRecordComponentList() throws SyntaxException {
        trace("parseRecordComponentList");
        RecordComponentList construct = new RecordComponentList();

        save();
        RecordComponent item = parseRecordComponent();
        if (item == null) {
            revert();
            return null;
        }
        construct.addRecordComponent(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            save();
            item = parseRecordComponent();
            if (item == null) {
                revert();
                return null;
            } else {
                pop();
                construct.addRecordComponent(item);
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.1")
    private RecordComponent parseRecordComponent() throws SyntaxException {
        trace("parseRecordComponent");
        RecordComponent construct = new RecordComponent();
        save();
        construct.setVariableArityRecordComponent(parseVariableArityRecordComponent());
        if (construct.getVariableArityRecordComponent() == null) {
            rewind();
        } else {
            pop();
            return construct;
        }
        for (RecordComponentModifier item = parseRecordComponentModifier(); item != null; item = parseRecordComponentModifier()) {
            construct.addRecordComponentModifier(item);
        }
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.1")
    private RecordComponentModifier parseRecordComponentModifier() throws SyntaxException {
        trace("parseRecordComponentModifier");
        RecordComponentModifier construct = new RecordComponentModifier();
        save();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.1")
    private VariableArityRecordComponent parseVariableArityRecordComponent() throws SyntaxException {
        trace("parseVariableArityRecordComponent");
        VariableArityRecordComponent construct = new VariableArityRecordComponent();
        save();
        for (RecordComponentModifier item = parseRecordComponentModifier(); item != null; item = parseRecordComponentModifier()) {
            construct.addRecordComponentModifier(item);
        }
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
            construct.addAnnotation(item);
        }
        if (!acceptAndConsume(TokenType.DOT_DOT_DOT)) {
            revert();
            return null;
        }
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.2")
    private RecordBody parseRecordBody() throws SyntaxException {
        trace("parseRecordBody");
        RecordBody construct = new RecordBody();
        save();
        if (!acceptAndConsume(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        for (RecordBodyDeclaration item = parseRecordBodyDeclaration(); item != null; item = parseRecordBodyDeclaration()) {
            construct.addRecordBodyDeclaration(item);
        }
        if (!acceptAndConsume(TokenType.RIGHT_BRACE)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("8.10.2")
    private RecordBodyDeclaration parseRecordBodyDeclaration() throws SyntaxException {
        trace("parseRecordBodyDeclaration");
        RecordBodyDeclaration construct = new RecordBodyDeclaration();
        save();
        construct.setClassBodyDeclaration(parseClassBodyDeclaration());
        if (construct.getClassBodyDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setCompactConstructorDeclaration(parseCompactConstructorDeclaration());
        if (construct.getCompactConstructorDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("8.10.4.2")
    private CompactConstructorDeclaration parseCompactConstructorDeclaration() throws SyntaxException {
        trace("parseCompactConstructorDeclaration");
        CompactConstructorDeclaration construct = new CompactConstructorDeclaration();
        save();
        for (ConstructorModifier item = parseConstructorModifier(); item != null; item = parseConstructorModifier()) {
            construct.addConstructorModifier(item);
        }
        construct.setSimpleTypeName(parseSimpleTypeName());
        if (construct.getSimpleTypeName() == null) {
            revert();
            return null;
        }
        construct.setConstructorBody(parseConstructorBody());
        if (construct.getConstructorBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    //
    // Chapter 9 - Interfaces
    //

    @JlsChapter("9.1")
    private InterfaceDeclaration parseInterfaceDeclaration() throws SyntaxException {
        trace("parseInterfaceDeclaration");
        InterfaceDeclaration construct = new InterfaceDeclaration();
        save();
        construct.setAnnotationInterfaceDeclaration(parseAnnotationInterfaceDeclaration());
        if (construct.getAnnotationInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setNormalInterfaceDeclaration(parseNormalInterfaceDeclaration());
        if (construct.getNormalInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("9.1.1")
    private InterfaceModifier parseInterfaceModifier() throws SyntaxException {
        trace("parseInterfaceModifier");
        InterfaceModifier construct = new InterfaceModifier();
        save();
        switch (peek().type()) {
            case KEYWORD_PUBLIC, KEYWORD_PROTECTED, KEYWORD_PRIVATE, KEYWORD_ABSTRACT,
                    KEYWORD_STATIC, CONTEXTUAL_SEALED, CONTEXTUAL_NON_SEALED, KEYWORD_STRICTFP:
                construct.setType(peek().type());
                consume();
                pop();
                return construct;
            default:
                construct.setAnnotation(parseAnnotation());
                if (construct.getAnnotation() != null) {
                    pop();
                    return construct;
                }
                revert();
                return null;
        }
    }

    @JlsChapter("9.3")
    private ConstantDeclaration parseConstantDeclaration() throws SyntaxException {
        trace("parseConstantDeclaration");
        ConstantDeclaration construct = new ConstantDeclaration();
        save();
        for (ConstantModifier item = parseConstantModifier(); item != null; item = parseConstantModifier()) {
            construct.addConstantModifier(item);
        }
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        construct.setVariableDeclaratorList(parseVariableDeclaratorList());
        if (construct.getVariableDeclaratorList() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.SEMICOLON)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.3")
    private ConstantModifier parseConstantModifier() throws SyntaxException {
        trace("parseConstantModifier");
        ConstantModifier construct = new ConstantModifier();
        if (acceptAndConsume(TokenType.KEYWORD_PUBLIC)) {
            construct.setPublic(true);
            return construct;
        }
        if (acceptAndConsume(TokenType.KEYWORD_STATIC)) {
            construct.setStatic(true);
            return construct;
        }
        if (acceptAndConsume(TokenType.KEYWORD_FINAL)) {
            construct.setFinal(true);
            return construct;
        }
        save();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("9.1")
    private NormalInterfaceDeclaration parseNormalInterfaceDeclaration() throws SyntaxException {
        trace("parseNormalInterfaceDeclaration");
        NormalInterfaceDeclaration construct = new NormalInterfaceDeclaration();
        save();
        for (InterfaceModifier item = parseInterfaceModifier(); item != null; item = parseInterfaceModifier()) {
            construct.addInterfaceModifier(item);
        }

        if (!acceptAndConsume(TokenType.KEYWORD_INTERFACE)) {
            revert();
            return null;
        }

        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setTypeParameters(parseTypeParameters());
        construct.setInterfaceExtends(parseInterfaceExtends());
        construct.setInterfacePermits(parseInterfacePermits());
        construct.setInterfaceBody(parseInterfaceBody());
        if (construct.getInterfaceBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.1.3")
    private InterfaceExtends parseInterfaceExtends() throws SyntaxException {
        trace("parseInterfaceExtends");
        InterfaceExtends construct = new InterfaceExtends();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_EXTENDS)) {
            revert();
            return null;
        }
        construct.setInterfaceTypeList(parseInterfaceTypeList());
        if (construct.getInterfaceTypeList() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.1.4")
    private InterfacePermits parseInterfacePermits() throws SyntaxException {
        trace("parseInterfacePermits");
        InterfacePermits construct = new InterfacePermits();
        save();
        if (!acceptAndConsume(TokenType.CONTEXTUAL_PERMITS)) {
            revert();
            return null;
        }
        TypeName item = parseTypeName();
        if (item == null) {
            revert();
            return null;
        }
        construct.addTypeName(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            item = parseTypeName();
            if (item == null) {
                // TODO: Should this throw an exception as the comma indicates this should work?
                revert();
                return null;
            }
            construct.addTypeName(item);
        }
        pop();
        return construct;
    }

    @JlsChapter("9.1.5")
    private InterfaceBody parseInterfaceBody() throws SyntaxException {
        trace("parseInterfaceBody");
        InterfaceBody construct = new InterfaceBody();
        save();
        if (!acceptAndConsume(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        for (InterfaceMemberDeclaration item = parseInterfaceMemberDeclaration(); item != null; item = parseInterfaceMemberDeclaration()) {
            construct.addInterfaceMemberDeclaration(item);
        }
        if (!acceptAndConsume(TokenType.RIGHT_BRACE)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.1.15")
    private InterfaceMemberDeclaration parseInterfaceMemberDeclaration() throws SyntaxException {
        trace("parseInterfaceMemberDeclaration");
        InterfaceMemberDeclaration construct = new InterfaceMemberDeclaration();
        save();
        construct.setConstantDeclaration(parseConstantDeclaration());
        if (construct.getConstantDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setInterfaceMethodDeclaration(parseInterfaceMethodDeclaration());
        if (construct.getInterfaceMethodDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassDeclaration(parseClassDeclaration());
        if (construct.getClassDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setInterfaceDeclaration(parseInterfaceDeclaration());
        if (construct.getInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        if (acceptAndConsume(TokenType.SEMICOLON)) {
            pop();
            // TODO: Mark it as empty?
            return construct;
        }
        revert();
        return null;
    }

    // @JlsChapter("9.3")
    // private ConstantDeclaration parseConstantDeclaration() {
    //     trace("parseConstantDeclaration");
    //     ConstantDeclaration construct = new ConstantDeclaration();
    //     save();
    //     for (ConstantModifier item = parseConstantModifier(); item != null; item = parseConstantModifier()) {
    //         construct.addConstantModifier(item);
    //     }
    //     construct.setUnannType(parseUnannType());
    //     if (construct.getUnannType() == null) {
    //         revert();
    //         return null;
    //     }
    //     construct.setVariableDeclaratorList(parseVariableDeclaratorList());
    //     if (construct.getVariableDeclaratorList() == null) {
    //         revert();
    //         return null;
    //     }
    //     if (!acceptAndConsume(TokenType.SEMICOLON)) {
    //         revert();
    //         return null;
    //     }
    //     pop();
    //     return construct;
    // }

    @JlsChapter("9.4")
    private InterfaceMethodDeclaration parseInterfaceMethodDeclaration() throws SyntaxException {
        trace("parseInterfaceMethodDeclaration");
        InterfaceMethodDeclaration construct = new InterfaceMethodDeclaration();
        save();
        for (InterfaceMethodModifier item = parseInterfaceMethodModifier(); item != null; item = parseInterfaceMethodModifier()) {
            construct.addInterfaceMethodModifier(item);
        }
        construct.setMethodHeader(parseMethodHeader());
        if (construct.getMethodHeader() == null) {
            revert();
            return null;
        }
        construct.setMethodBody(parseMethodBody());
        if (construct.getMethodBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.4")
    private InterfaceMethodModifier parseInterfaceMethodModifier() throws SyntaxException {
        trace("parseInterfaceMethodModifier");
        InterfaceMethodModifier construct = new InterfaceMethodModifier();
        switch (peek().type()) {
            case KEYWORD_PUBLIC, KEYWORD_PRIVATE, KEYWORD_ABSTRACT,
                    KEYWORD_DEFAULT, KEYWORD_STATIC, KEYWORD_STRICTFP:
                construct.setType(peek().type());
                consume();
                return construct;
            default:
            construct.setAnnotation(parseAnnotation());
            if (construct.getAnnotation() != null) {
                return construct;
            }
            return null;
        }
    }

    @JlsChapter("9.6")
    private AnnotationInterfaceDeclaration parseAnnotationInterfaceDeclaration() throws SyntaxException {
        trace("parseAnnotationInterfaceDeclaration");
        AnnotationInterfaceDeclaration construct = new AnnotationInterfaceDeclaration();
        save();
        for (InterfaceModifier item = parseInterfaceModifier(); item != null; item = parseInterfaceModifier()) {
            construct.addInterfaceModifier(item);
        }
        if (!acceptAndConsume(TokenType.AT_SIGN)) {
            revert();
            return null;
        }
        consume(TokenType.KEYWORD_INTERFACE);
        construct.setTypeIdentifier(parseTypeIdentifier());
        if (construct.getTypeIdentifier() == null) {
            revert();
            return null;
        }
        construct.setAnnotationInterfaceBody(parseAnnotationInterfaceBody());
        if (construct.getAnnotationInterfaceBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.6.1")
    private AnnotationInterfaceBody parseAnnotationInterfaceBody() throws SyntaxException {
        trace("parseAnnotationInterfaceBody");
        AnnotationInterfaceBody construct = new AnnotationInterfaceBody();
        save();
        if (!acceptAndConsume(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        for (AnnotationInterfaceMemberDeclaration item = parseAnnotationInterfaceMemberDeclaration(); item != null; item = parseAnnotationInterfaceMemberDeclaration()) {
            construct.addAnnotationInterfaceMemberDeclaration(item);
        }
        if (!acceptAndConsume(TokenType.RIGHT_BRACE)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.6.1")
    private AnnotationInterfaceMemberDeclaration parseAnnotationInterfaceMemberDeclaration() throws SyntaxException {
        trace("parseAnnotationInterfaceMemberDeclaration");
        AnnotationInterfaceMemberDeclaration construct = new AnnotationInterfaceMemberDeclaration();
        save();
        construct.setAnnotationInterfaceElementDeclaration(parseAnnotationInterfaceElementDeclaration());
        if (construct.getAnnotationInterfaceElementDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setConstantDeclaration(parseConstantDeclaration());
        if (construct.getConstantDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassDeclaration(parseClassDeclaration());
        if (construct.getClassDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setInterfaceDeclaration(parseInterfaceDeclaration());
        if (construct.getInterfaceDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        if (acceptAndConsume(TokenType.SEMICOLON)) {
            pop();
            return construct;
        }
        return null;
    }

    @JlsChapter("9.6.1")
    private AnnotationInterfaceElementDeclaration parseAnnotationInterfaceElementDeclaration() throws SyntaxException {
        trace("parseAnnotationInterfaceElementDeclaration");
        AnnotationInterfaceElementDeclaration construct = new AnnotationInterfaceElementDeclaration();
        save();
        for (AnnotationInterfaceElementModifier item = parseAnnotationInterfaceElementModifier(); item != null; item = parseAnnotationInterfaceElementModifier()) {
            construct.addAnnotationInterfaceElementModifier(item);
        }
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() == null) {
            revert();
            return null;
        }
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
            revert();
            return null;
        }
        construct.setDims(parseDims());
        construct.setDefaultValue(parseDefaultValue());
        if (acceptAndConsume(TokenType.SEMICOLON)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.6.1")
    private AnnotationInterfaceElementModifier parseAnnotationInterfaceElementModifier() throws SyntaxException {
        trace("parseAnnotationInterfaceElementModifier");
        AnnotationInterfaceElementModifier construct = new AnnotationInterfaceElementModifier();
        if (isTokenType(TokenType.KEYWORD_PRIVATE)) {
            construct.setPrivate(true);
            return construct;
        }
        if (isTokenType(TokenType.KEYWORD_PUBLIC)) {
            construct.setPublic(true);
            return construct;
        }
        save();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.6.2")
    private DefaultValue parseDefaultValue() throws SyntaxException {
        unimplemented("parseDefaultValue");
        return null;
    }

    @JlsChapter("9.7")
    Annotation parseAnnotation() throws SyntaxException {
        trace("parseAnnotation");
        Annotation construct = new Annotation();
        save();
        construct.setSingleElementAnnotation(parseSingleElementAnnotation());
        if (construct.getSingleElementAnnotation() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setNormalAnnotation(parseNormalAnnotation());
        if (construct.getNormalAnnotation() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setMarkerAnnotation(parseMarkerAnnotation());
        if (construct.getMarkerAnnotation() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("9.7.1")
    private NormalAnnotation parseNormalAnnotation() throws SyntaxException {
        trace("parseNormalAnnotation");
        NormalAnnotation construct = new NormalAnnotation();
        save();
        if (!acceptAndConsume(TokenType.AT_SIGN)) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        construct.setElementValuePairList(parseElementValuePairList());
        if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.7.1")
    private ElementValuePairList parseElementValuePairList() throws SyntaxException {
        trace("parseElementValuePairList");
        ElementValuePairList construct = new ElementValuePairList();
        save();
        ElementValuePair item = parseElementValuePair();
        if (item == null) {
            revert();
            return null;
        }
        construct.addElementValuePair(item);
        while (acceptAndConsume(TokenType.COMMA)) {
            save();
            if (item == null) {
                revert();
                return null;
            } else {
                pop();
                construct.addElementValuePair(item);
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("9.7.1")
    private ElementValuePair parseElementValuePair() throws SyntaxException {
        trace("parseElementValuePair");
        ElementValuePair construct = new ElementValuePair();
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.EQUALS)) {
            revert();
            return null;
        }
        construct.setElementValue(parseElementValue());
        if (construct.getElementValue() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("9.7")
    private MarkerAnnotation parseMarkerAnnotation() throws SyntaxException {
        trace("parseMarkerAnnotation");
        MarkerAnnotation construct = new MarkerAnnotation();
        save();
        if (acceptAndConsume(TokenType.AT_SIGN)) {
            construct.setTypeName(parseTypeName());
            if (construct.getTypeName() != null) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("9.7.1")
    private ElementValue parseElementValue() throws SyntaxException {
        trace("parseElementValue");
        ElementValue construct = new ElementValue();
        save();
        construct.setConditionalExpression(parseConditionalExpression());
        if (construct.getConditionalExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setElementValueArrayInitializer(parseElementValueArrayInitializer());
        if (construct.getElementValueArrayInitializer() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setAnnotation(parseAnnotation());
        if (construct.getAnnotation() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("9.7.1")
    private ElementValueArrayInitializer parseElementValueArrayInitializer() throws SyntaxException {
        trace("parseElementValueArrayInitializer");
        ElementValueArrayInitializer construct = new ElementValueArrayInitializer();
        save();

        while(true) {
            Boolean comma = false;
            ElementValueList evl = parseElementValueList();
            if (acceptAndConsume(TokenType.COMMA)) {
                comma = true;
            }
            if (evl == null && !comma) {
                break;
            }
            construct.addElementValueListPair(Pair.of(evl,comma));
        }

        if (!construct.getElementValuePairList().isEmpty()) {
            pop();
            return construct;
        }

        revert();
        return null;
    }

    private ElementValueList parseElementValueList() throws SyntaxException {
        trace("parseElementValueList");
        ElementValueList construct = new ElementValueList();
        save();
        ElementValue ev = parseElementValue();
        if (ev == null) {
            revert();
            return null;
        }
        construct.addElementValue(ev);
        while (acceptAndConsume(TokenType.COMMA)) {
            ev = parseElementValue();
            if (ev == null) {
                revert();
                return null;
            }
            construct.addElementValue(ev);
        }
        pop();
        return construct;
    }

    @JlsChapter("9.7.3")
    private SingleElementAnnotation parseSingleElementAnnotation() throws SyntaxException {
        trace("parseSingleElementAnnotation");
        SingleElementAnnotation construct = new SingleElementAnnotation();
        save();
        if (acceptAndConsume(TokenType.AT_SIGN)) {
            construct.setTypeName(parseTypeName());
            if (construct.getTypeName() == null) {
                revert();
                return null;
            }
            if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
                revert();
                return null;
            }
            construct.setElementValue(parseElementValue());
            if (construct.getElementValue() == null) {
                revert();
                return null;
            }
            if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
                revert();
                return null;
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    //
    // CHapter 10 - Arrays
    //

    private ArrayInitializer parseArrayInitializer() throws SyntaxException {
        unimplemented("parseArrayInitializer");
        return null;
    }

    //
    // Chapter 14 - Blocks and Statements
    //

    protected List<VariableModifier> parseVariableModifiers() throws SyntaxException {
        trace("parseVariableModifiers");
        // TODO: This returns an empty list instead of null. Check caller wants that
        // TODO: Are these consume() calls supposed ot be here?
        List<VariableModifier> modifiers = new ArrayList<>();
        while (isClassModifierOrAnnotationToken(peek().type())) {
            if (isTokenType(TokenType.KEYWORD_FINAL)) {
                consume();
                VariableModifier modifier = new VariableModifier();
                modifier.setFinal(true);
            } else if (peek().type() == TokenType.AT_SIGN) {
                consume();
                // It's a full annotation
                Annotation annotation = parseAnnotation();
                VariableModifier modifier = new VariableModifier(annotation);
                modifiers.add(modifier);
            } else {
                // Should be unreachable
                break;
            }
        }
        return modifiers;
    }

    private Block parseBlock() throws SyntaxException {
        trace("parseBlock");
        Block construct = new Block();
        save();
        if (!isTokenType(TokenType.LEFT_BRACE)) {
            revert();
            return null;
        }
        consume(TokenType.LEFT_BRACE);
        if (isTokenType(TokenType.RIGHT_BRACE)) {
            consume();
            pop();
            return construct;
        }
        construct.setBlockStatements(parseBlockStatements()); // Pete Tong
        if (construct.getBlockStatements() == null) {
            revert();
            return null;
        }
        if (acceptAndConsume(TokenType.RIGHT_BRACE)) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.2")
    BlockStatements parseBlockStatements() throws SyntaxException {
        trace("parseBlockStatements");
        BlockStatements construct = new BlockStatements();
        save();
        BlockStatement blockStatement = parseBlockStatement();
        if (blockStatement == null) {
            revert();
            return null;
        }
        construct.addBlockStatement(blockStatement);
        for (BlockStatement item = parseBlockStatement(); item != null; item = parseBlockStatement()) {
            construct.addBlockStatement(item);
        }
        pop();
        return construct;
    }

    @JlsChapter("14.2")
    BlockStatement parseBlockStatement() throws SyntaxException {
        trace("parseBlockStatement");
        if (isTokenType(TokenType.RIGHT_BRACE)) {
            return null;
        }
        BlockStatement construct = new BlockStatement();
        save();
        ASTNode statement = parseLocalClassOrInterfaceDeclaration();
        if (statement != null) {
            construct.setLocalClassOrInterfaceDeclaration((LocalClassOrInterfaceDeclaration)statement);
            pop();
            return construct;
        }
        rewind();
        statement = parseLocalVariableDeclarationStatement();
        if (statement != null) {
            construct.setLocalVariableDeclarationStatement((LocalVariableDeclarationStatement)statement);
            pop();
            return construct;
        }
        rewind();
        statement = parseStatement();
        if (statement != null) {
            construct.setStatement((Statement)statement);
            pop();
            return construct;
        }
        revert();
        return null;
    }

    private LocalClassOrInterfaceDeclaration parseLocalClassOrInterfaceDeclaration() throws SyntaxException {
        trace("parseLocalClassOrInterfaceDeclaration");
        LocalClassOrInterfaceDeclaration construct = new LocalClassOrInterfaceDeclaration();
        construct.setClassDeclaration(parseClassDeclaration());
        if (construct.getClassDeclaration() != null) {
            return construct;
        }
        construct.setNormalInterfaceDeclaration(parseNormalInterfaceDeclaration());
        if (construct.getNormalInterfaceDeclaration() != null) {
            return construct;
        }
        return null;
        // int lookahead = countClassModifiers();
        // TokenType type = peek(lookahead).type();
        // ASTNode declaration = null;
        // if (canParseTopLevelDeclaration()) {
        //     declaration = switch (type) {
        //         case KEYWORD_CLASS -> parseNormalClassDeclaration();
        //         case KEYWORD_INTERFACE -> parseNormalInterfaceDeclaration();
        //         case KEYWORD_ENUM -> parseEnumDeclaration();
        //         case CONTEXTUAL_RECORD -> parseRecordDeclaration();
        //         case AT_SIGN -> parseAnnotationInterfaceDeclaration(); // @interface
        //         default -> null; // Should be unreachable due to canParseTopLevelDeclaration()
        //     };
        // }
        // if (declaration == null) {
        //     return null;
        // }
        // return new LocalClassOrInterfaceDeclaration(declaration);
    }

    @JlsChapter("14.4.2")
    LocalVariableDeclarationStatement parseLocalVariableDeclarationStatement() throws SyntaxException {
        trace("parseLocalVariableDeclarationStatement");
        LocalVariableDeclarationStatement construct = new LocalVariableDeclarationStatement();
        save();
        construct.setLocalVariableDeclaration(parseLocalVariableDeclaration());
        if (!isTokenType(TokenType.SEMICOLON)) {
            revert();
            return null;
        }
        consume(TokenType.SEMICOLON);
        pop();
        return construct;
    }

    @JlsChapter("14.4")
    private LocalVariableDeclaration parseLocalVariableDeclaration() throws SyntaxException {
        trace("parseLocalVariableDeclaration");
        LocalVariableDeclaration construct = new LocalVariableDeclaration();
        save();
        List<VariableModifier> modifiers = parseVariableModifiers();
        for (VariableModifier modifier : modifiers) {
            construct.addVariableModifier(modifier);
        }
        construct.setLocalVariableType(parseLocalVariableType());
        if (construct.getLocalVariableType() == null) {
            revert();
            return null;
        }
        construct.setVariableDeclaratorList(parseVariableDeclaratorList());
        if (construct.getVariableDeclaratorList() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("14.4")
    private LocalVariableType parseLocalVariableType() throws SyntaxException {
        trace("parseLocalVariableType");
        LocalVariableType construct = new LocalVariableType();
        save();
        if (isTokenType(TokenType.CONTEXTUAL_VAR)) {
            consume();
            construct.setVar(true);
        } else {
            construct.setUnannType(parseUnannType());
            if (construct.getUnannType() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("14.5")
    Statement parseStatement() throws SyntaxException {
        trace("parseStatement");
        Statement construct = new Statement();
        save();
        construct.setStatementWithoutTrailingSubstatement(parseStatementWithoutTrailingSubstatement());
        if (construct.getStatementWithoutTrailingSubstatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setLabeledStatement(parseLabeledStatement());
        if (construct.getLabeledStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setIfThenElseStatement(parseIfThenElseStatement());
        if (construct.getIfThenElseStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setIfThenStatement(parseIfThenStatement());
        if (construct.getIfThenStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setWhileStatement(parseWhileStatement());
        if (construct.getWhileStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setForStatement(parseForStatement());
        if (construct.getForStatement() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @Here
    @JlsChapter("14.5")
    StatementWithoutTrailingSubstatement parseStatementWithoutTrailingSubstatement() throws SyntaxException {
        trace("parseStatementWithoutTrailingSubstatement");
        StatementWithoutTrailingSubstatement construct = new StatementWithoutTrailingSubstatement();
        save();
        construct.setBlock(parseBlock());
        if (construct.getBlock() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setEmptyStatement(parseEmptyStatement());
        if (construct.getEmptyStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setExpressionStatement(parseExpressionStatement()); //TODO: Goes wrong here
        if (construct.getExpressionStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setAssertStatement(parseAssertStatement());
        if (construct.getAssertStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setSwitchStatement(parseSwitchStatement());
        if (construct.getSwitchStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setDoStatement(parseDoStatement());
        if (construct.getDoStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setBreakStatement(parseBreakStatement());
        if (construct.getBreakStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setContinueStatement(parseContinueStatement());
        if (construct.getContinueStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setReturnStatement(parseReturnStatement());
        if (construct.getReturnStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setSynchronizedStatement(parseSynchronizedStatement());
        if (construct.getSynchronizedStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setThrowStatement(parseThrowStatement());
        if (construct.getThrowStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setTryStatement(parseTryStatement());
        if (construct.getTryStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setYieldStatement(parseYieldStatement());
        if (construct.getYieldStatement() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.5")
    private StatementNoShortIf parseStatementNoShortIf() throws SyntaxException {
        trace("parseStatementNoShortIf");
        StatementNoShortIf construct = new StatementNoShortIf();
        save();
        construct.setStatementWithoutTrailingSubstatement(parseStatementWithoutTrailingSubstatement());
        if (construct.getStatementWithoutTrailingSubstatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setLabeledStatementNoShortIf(parseLabeledStatementNoShortIf());
        if (construct.getLabeledStatementNoShortIf() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setIfThenElseStatementNoShortIf(parseIfThenElseStatementNoShortIf());
        if (construct.getIfThenElseStatementNoShortIf() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setWhileStatementNoShortIf(parseWhileStatementNoShortIf());
        if (construct.getWhileStatementNoShortIf() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setForStatementNoShortIf(parseForStatementNoShortIf());
        if (construct.getForStatementNoShortIf() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.5")
    private LabeledStatementNoShortIf parseLabeledStatementNoShortIf() throws SyntaxException {
        unimplemented("parseLabeledStatementNoShortIf");
        return null;
    }

    @JlsChapter("14.6")
    private EmptyStatement parseEmptyStatement() throws SyntaxException {
        unimplemented("parseEmptyStatement");
        return null;
    }

    @JlsChapter("14.7")
    private LabeledStatement parseLabeledStatement() throws SyntaxException {
        unimplemented("parseLabeledStatement");
        return null;
    }

    @JlsChapter("14.8")
    private ExpressionStatement parseExpressionStatement() throws SyntaxException {
        trace("parseExpressionStatement");
        ExpressionStatement construct = new ExpressionStatement();
        save();
        construct.setStatementExpression(parseStatementExpression());
        if (construct.getStatementExpression() == null) {
            revert();
            return null;
        }
        if (acceptAndConsume(TokenType.SEMICOLON)) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.8")
    private StatementExpression parseStatementExpression() throws SyntaxException {
        trace("parseStatementExpression");
        if (tokenIndex == 7) {
            reporter.reportInfo("*** " + upcomingTokens());
        }
        StatementExpression construct = new StatementExpression();
        save();
        construct.setAssignment(parseAssignment());
        if (construct.getAssignment() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPreIncrementExpression(parsePreIncrementExpression());
        if (construct.getPreIncrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPreDecrementExpression(parsePreDecrementExpression());
        if (construct.getPreDecrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPostIncrementExpression(parsePostIncrementExpression(-1, -1));
        if (construct.getPostIncrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPostDecrementExpression(parsePostDecrementExpression(-1, -1));
        if (construct.getPostDecrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setMethodInvocation(parseMethodInvocation(null, -1, -1, -1));
        if (construct.getMethodInvocation() != null) {
            reporter.reportDebug(construct.getMethodInvocation().getMethodName().toString());
            pop();
            return construct;
        }
        rewind();
        construct.setClassInstanceCreationExpression(parseClassInstanceCreationExpression(-1, -1, -1));
        if (construct.getClassInstanceCreationExpression() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.9")
    IfThenStatement parseIfThenStatement() throws SyntaxException {
        trace("parseIfThenStatement");
        IfThenStatement construct = new IfThenStatement();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_IF)) {
            revert();
            return null;
        }
        consume(TokenType.LEFT_PAREN);
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            revert();
            return null;
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatement(parseStatement());
        if (construct.getStatement() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("14.9")
    IfThenElseStatement parseIfThenElseStatement() throws SyntaxException {
        trace("parseIfThenElseStatement");
        IfThenElseStatement construct = new IfThenElseStatement();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_IF)) {
            revert();
            return null;
        }
        consume(TokenType.LEFT_PAREN);
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            throw new ExpectedConstructException("expression", peek());
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatementNoShortIf(parseStatementNoShortIf());
        if (construct.getStatementNoShortIf() == null) {
            revert();
            return null;
        }
        if (!isTokenType(TokenType.KEYWORD_ELSE)) {
            revert();
            return null;
        }
        consume(TokenType.KEYWORD_ELSE);
        construct.setStatement(parseStatement());
        if (construct.getStatement() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("14.9")
    private IfThenElseStatementNoShortIf parseIfThenElseStatementNoShortIf() throws SyntaxException {
        trace("parseIfThenElseStatementNoShortIf");
        IfThenElseStatementNoShortIf construct = new IfThenElseStatementNoShortIf();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_IF)) {
            revert();
            return null;
        }
        consume(TokenType.LEFT_PAREN);
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            throw new ExpectedConstructException("expression", peek());
            // restore();
            // return null;
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatementNoShortIf(parseStatementNoShortIf());
        if (construct.getStatementNoShortIf() == null) {
            throw new ExpectedConstructException("statement", peek());
            // restore();
            // return null;
        }
        construct.setLastStatementNoShortIf(parseStatementNoShortIf());
        if (construct.getLastStatementNoShortIf() == null) {
            throw new ExpectedConstructException("statement", peek());
            // restore();
            // return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("14.10")
    private AssertStatement parseAssertStatement() throws SyntaxException {
        unimplemented("parseAssertStatement");
        return null;
    }

    @JlsChapter("14.11.1")
    SwitchStatement parseSwitchStatement() throws SyntaxException {
        trace("parseSwitchStatement");
        SwitchStatement construct = new SwitchStatement();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_SWITCH)) {
            consume(TokenType.LEFT_PAREN);
            construct.setExpression(parseExpression());
            if (construct.getExpression() == null) {
                revert();
                return null;
            }
            consume(TokenType.RIGHT_PAREN);
            construct.setSwitchBlock(parseSwitchBlock());
            if (construct.getSwitchBlock() == null) {
                revert();
                return null;
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private SwitchBlock parseSwitchBlock() throws SyntaxException {
        trace("parseSwitchBlock");
        SwitchBlock construct = new SwitchBlock();
        if (!isTokenType(TokenType.LEFT_BRACE)) {
            return null;
        }
        save();
        consume();
        save();
        SwitchRule item = parseSwitchRule();
        if (item != null) {
            construct.addSwitchRule(item);
            while(true) {
                save();
                item = parseSwitchRule();
                if (item != null) {
                    pop();
                    construct.addSwitchRule(item);
                } else {
                    revert();
                    break;
                }
            }
            if (acceptAndConsume(TokenType.RIGHT_BRACE)) {
                pop();
                pop();
                return construct;
            }
        }
        rewind();

        while(true) {
            List<SwitchBlockStatementGroup> sbsgList = new ArrayList<>();
            SwitchBlockStatementGroup sbsg = null;
            while(true) {
                save();
                sbsg = parseSwitchBlockStatementGroup();
                if (sbsg != null) {
                    pop();
                    sbsgList.add(sbsg);
                } else {
                    revert();
                    break;
                }
            }
            List<SwitchLabel> slList = new ArrayList<>();
            SwitchLabel sl = null;
            while(true) {
                save();
                sl = parseSwitchLabel();
                if (sl != null) {
                    consume(TokenType.COLON);
                    pop();
                    slList.add(sl);
                } else {
                    revert();
                    break;
                }
            }
            if (sbsgList.isEmpty() && slList.isEmpty()) {
                break;
            }
            Pair<List<SwitchBlockStatementGroup>,List<SwitchLabel>> pair =
                    Pair.of(sbsgList, slList);
            construct.addStatementGroupPair(pair);
        }
        if (acceptAndConsume(TokenType.RIGHT_BRACE)) {
            pop();
            pop();
            return construct;
        }

        revert();
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    SwitchRule parseSwitchRule() throws SyntaxException {
        trace("parseSwitchRule");
        SwitchRule construct = new SwitchRule();
        save();
        construct.setSwitchLabel(parseSwitchLabel());
        if (construct.getSwitchLabel() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.ARROW)) {
            // TODO: Why was this comment here?
            // Do not revert() here as pop() has just been called
            revert();
            return null;
        }
        save();
        construct.setExpression(parseExpression());
        if (construct.getExpression() != null) {
            consume(TokenType.SEMICOLON);
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setBlock(parseBlock());
        if (construct.getBlock() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setThrowStatement(parseThrowStatement());
        if (construct.getThrowStatement() != null) {
            pop();
            pop();
            return construct;
        }
        revert();
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private SwitchBlockStatementGroup parseSwitchBlockStatementGroup() throws SyntaxException {
        trace("parseSwitchBlockStatementGroup");
        SwitchBlockStatementGroup construct = new SwitchBlockStatementGroup();
        save();
        SwitchLabel sl = parseSwitchLabel();
        if (sl == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.COLON)) {
            revert();
            return null;
        }
        construct.addSwitchLabel(sl);
        while(true) {
            save();
            sl = parseSwitchLabel();
            if (sl == null) {
                revert();
                break;
            }
            pop();
            consume(TokenType.COLON);
            construct.addSwitchLabel(sl);
        }
        //BlockStatements
        construct.setBlockStatements(parseBlockStatements());
        if (construct.getBlockStatements() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private SwitchLabel parseSwitchLabel() throws SyntaxException {
        trace("parseSwitchLabel");
        SwitchLabel construct = new SwitchLabel();
        if (acceptAndConsume(TokenType.KEYWORD_DEFAULT)) {
            construct.setDefault(true);
            return construct;
        }
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_CASE)) {
            revert();
            return null;
        }
        if (acceptAndConsume(TokenType.KEYWORD_NULL)) {
            construct.setNull(true);
            if (isTokenType(TokenType.COMMA)) {
                consume(TokenType.KEYWORD_DEFAULT);
                construct.setDefault(true);
            }
            pop();
            return construct;
        }

        save();
        CasePattern cp = parseCasePattern();
        if (cp != null) {
            construct.addCasePattern(cp);
            while(acceptAndConsume(TokenType.COMMA)) {
                save();
                cp = parseCasePattern();
                if (cp != null) {
                    pop();
                    construct.addCasePattern(cp);
                } else {
                    revert();
                    break;
                }
            }
            pop();
            pop();
            return construct;
        }
        rewind();
        CaseConstant cc = parseCaseConstant();
        if (cc != null) {
            construct.addCaseConstant(cc);
            while (acceptAndConsume(TokenType.COMMA)) {
                save();
                cc = parseCaseConstant();
                if (cc != null) {
                    pop();
                    construct.addCaseConstant(cc);
                } else {
                    revert();
                    break;
                }
            }
            pop();
            pop();
            return construct;
        }
        revert();
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private CaseConstant parseCaseConstant() throws SyntaxException {
        trace("parseCaseConstant");
        CaseConstant construct = new CaseConstant();
        save();
        construct.setConditionalExpression(parseConditionalExpression());
        if (construct.getConditionalExpression() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private CasePattern parseCasePattern() throws SyntaxException {
        trace("parseCasePattern");
        CasePattern construct = new CasePattern();
        save();
        construct.setPattern(parsePattern());
        if (construct.getPattern() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.11.1")
    private Guard parseGuard() throws SyntaxException {
        trace("parseGuard");
        Guard construct = new Guard();
        save();
        if (!acceptAndConsume(TokenType.CONTEXTUAL_WHEN)) {
            revert();
            return null;
        }
        construct.setExpression(parseExpression());
        if (construct.getExpression() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.12")
    @KeywordGate(TokenType.KEYWORD_WHILE)
    private WhileStatement parseWhileStatement() throws SyntaxException {
        trace("parseWhileStatement");
        WhileStatement construct = new WhileStatement();
        if (!isTokenType(TokenType.KEYWORD_WHILE)) {
            return null;
        }
        save();
        consume();
        consume(TokenType.LEFT_PAREN);
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            revert();
            return null;
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatement(parseStatement());
        if (construct.getStatement() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("14.12")
    private WhileStatementNoShortIf parseWhileStatementNoShortIf() throws SyntaxException {
        unimplemented("parseWhileStatementNoShortIf");
        return null;
    }

    @JlsChapter("14.13")
    private DoStatement parseDoStatement() throws SyntaxException {
        unimplemented("parseDoStatement");
        return null;
    }

    @JlsChapter("14.14")
    ForStatement parseForStatement() throws SyntaxException {
        trace("parseForStatement");
        ForStatement construct = new ForStatement();
        save();
        construct.setBasicForStatement(parseBasicForStatement());
        if (construct.getBasicForStatement() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setEnhancedForStatement(parseEnhancedForStatement());
        if (construct.getEnhancedForStatement() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.14.1")
    @KeywordGate(TokenType.KEYWORD_FOR)
    BasicForStatement parseBasicForStatement() throws SyntaxException {
        trace("parseBasicForStatement");
        BasicForStatement construct = new BasicForStatement();
        if (!isTokenType(TokenType.KEYWORD_FOR)) {
            return null;
        }
        save();
        consume();
        consume(TokenType.LEFT_PAREN);

        save();
        construct.setForInit(parseForInit());
        if (construct.getForInit() == null) {
            rewind();
        }
        if (!acceptAndConsume(TokenType.SEMICOLON)) {
            revert();
            revert();
            return null;
        }
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            rewind();
        }
        consume(TokenType.SEMICOLON);
        construct.setForUpdate(parseForUpdate());
        if (construct.getForUpdate() == null) {
            rewind();
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatement(parseStatement());
        if (construct.getStatement() == null) {
            rewind();
        }
        pop();
        pop();
        return construct;
    }

    @JlsChapter("14.14.1")
    private ForInit parseForInit() throws SyntaxException {
        trace("parseForInit");
        ForInit construct = new ForInit();
        save();
        construct.setLocalVariableDeclaration(parseLocalVariableDeclaration());
        if (construct.getLocalVariableDeclaration() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setStatementExpressionList(parseStatementExpressionList());
        if (construct.getStatementExpressionList() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.14.1")
    private ForUpdate parseForUpdate() throws SyntaxException {
        trace("parseForUpdate");
        ForUpdate construct = new ForUpdate();
        save();
        construct.setStatementExpressionList(parseStatementExpressionList());
        if (construct.getStatementExpressionList() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.14.1")
    private StatementExpressionList parseStatementExpressionList() throws SyntaxException {
        trace("parseStatementExpressionList");
        StatementExpressionList construct = new StatementExpressionList();
        save();
        StatementExpression item = parseStatementExpression();
        if (item == null) {
            revert();
            return null;
        }
        construct.addStatementExpression(item);
        // TODO: For all while lists make sure there is save and rewind
        while (acceptAndConsume(TokenType.COMMA)) {
            save();
            item = parseStatementExpression();
            if (item != null) {
                pop();
                construct.addStatementExpression(item);
            } else {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

    @JlsChapter("14.14.1")
    @KeywordGate(TokenType.KEYWORD_FOR)
    private EnhancedForStatement parseEnhancedForStatement() throws SyntaxException {
        trace("parseEnhancedForStatement");
        EnhancedForStatement construct = new EnhancedForStatement();
        if (!isTokenType(TokenType.KEYWORD_FOR)) {
            return null;
        }
        consume();
        consume(TokenType.LEFT_PAREN);
        construct.setLocalVariableDeclaration(parseLocalVariableDeclaration());
        if (construct.getLocalVariableDeclaration() == null) {
            throw new ExpectedConstructException("variable declaration", peek());
        }
        consume(TokenType.COLON);
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            throw new ExpectedConstructException("expression", peek());
        }
        consume(TokenType.RIGHT_PAREN);
        construct.setStatement(parseStatement());
        if (construct.getStatement() == null) {
            throw new ExpectedConstructException("statement", peek());
        }
        return construct;
    }

    @JlsChapter("14.14.1")
    private ForStatementNoShortIf parseForStatementNoShortIf() throws SyntaxException {
        unimplemented("parseForStatementNoShortIf");
        return null;
    }

    @JlsChapter("14.15")
    @KeywordGate(TokenType.KEYWORD_BREAK)
    private BreakStatement parseBreakStatement() throws SyntaxException {
        trace("parseBreakStatement");
        BreakStatement construct = new BreakStatement();
        if (!isTokenType(TokenType.KEYWORD_BREAK)) {
            return null;
        }
        consume();
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() == null) {
            revert();
        }
        consume(TokenType.SEMICOLON);
        return construct;
    }

    @JlsChapter("14.16")
    private ContinueStatement parseContinueStatement() throws SyntaxException {
        unimplemented("parseContinueStatement");
        return null;
    }

    @JlsChapter("14.17")
    @KeywordGate(TokenType.KEYWORD_RETURN)
    ReturnStatement parseReturnStatement() throws SyntaxException {
        trace("parseReturnStatement");
        ReturnStatement construct = new ReturnStatement();
        if (!acceptAndConsume(TokenType.KEYWORD_RETURN)) {
            return null;
        }
        construct.setExpression(parseExpression());
        consume(TokenType.SEMICOLON);
        return construct;
    }

    @JlsChapter("14.18")
    private ThrowStatement parseThrowStatement() throws SyntaxException {
        trace("parseThrowStatement");
        ThrowStatement construct = new ThrowStatement();
        if (!isTokenType(TokenType.KEYWORD_THROW)) {
            return null;
        }
        if (acceptAndConsume(TokenType.KEYWORD_THROW)) {
            construct.setExpression(parseExpression());
            if (construct.getExpression() != null) {
                consume(TokenType.SEMICOLON);
                return construct;
            }
        }
        throw new ExpectedConstructException("expression", peek());
    }

    @JlsChapter("14.19")
    private SynchronizedStatement parseSynchronizedStatement() throws SyntaxException {
        unimplemented("parseSynchronizedStatement");
        return null;
    }

    @JlsChapter("14.20")
    private TryStatement parseTryStatement() throws SyntaxException {
        unimplemented("parseTryStatement");
        return null;
    }

    @JlsChapter("14.21")
    private YieldStatement parseYieldStatement() throws SyntaxException {
        unimplemented("parseYieldStatement");
        return null;
    }

    @JlsChapter("14.30.1")
    private Pattern parsePattern() throws SyntaxException {
        trace("parsePattern");
        Pattern construct = new Pattern();
        save();
        construct.setTypePattern(parseTypePattern());
        if (construct.getTypePattern() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setRecordPattern(parseRecordPattern());
        if (construct.getRecordPattern() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.30.1")
    private TypePattern parseTypePattern() throws SyntaxException {
        trace("parseTypePattern");
        TypePattern construct = new TypePattern();
        save();
        construct.setLocalVariableDeclaration(parseLocalVariableDeclaration());
        if (construct.getLocalVariableDeclaration() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("14.30.1")
    @JlsPreview(14)
    @JlsVersion(16)
    private RecordPattern parseRecordPattern() throws SyntaxException {
        unimplemented("parseRecordPattern");
        return null;
    }

    //
    // Chapter 15 - Expressions
    //

    @JlsChapter("15.24")
    @LeftRecursionEliminated
    private ConditionalOrExpression parseConditionalOrExpression() throws SyntaxException {
        trace("parseConditionalOrExpression");
        ConditionalOrExpression construct = new ConditionalOrExpression();
        save();
        construct.setConditionalAndExpression(parseConditionalAndExpression());
        if (construct.getConditionalAndExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.PIPE_PIPE)) {
            Token operatorToken = peek();
            consume();
            save();
            ConditionalAndExpression rightConstruct = parseConditionalAndExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected ConditionalAndExpression after ConditionalOrExpression operator." + operatorToken);
            }
            pop();
            ConditionalOrExpression nextExpression = new ConditionalOrExpression();
            nextExpression.setConditionalOrExpression(construct);
            nextExpression.setConditionalAndExpression(rightConstruct);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private ConditionalAndExpression parseConditionalAndExpression() throws SyntaxException {
        trace("parseConditionalAndExpression");
        ConditionalAndExpression construct = new ConditionalAndExpression();
        save();
        construct.setInclusiveOrExpression(parseInclusiveOrExpression());
        if (construct.getInclusiveOrExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.AMPERSAND_AMPERSAND)) {
            Token operatorToken = peek();
            consume();
            save();
            InclusiveOrExpression rightConstruct = parseInclusiveOrExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected InclusiveOrExpression after ConditionalAndExpression operator." + operatorToken);
            }
            pop();
            ConditionalAndExpression nextExpression = new ConditionalAndExpression();
            nextExpression.setConditionalAndExpression(construct);
            nextExpression.setInclusiveOrExpression(rightConstruct);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private InclusiveOrExpression parseInclusiveOrExpression() throws SyntaxException {
        trace("parseInclusiveOrExpression");
        InclusiveOrExpression construct = new InclusiveOrExpression();
        save();
        construct.setExclusiveOrExpression(parseExclusiveOrExpression());
        if (construct.getExclusiveOrExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.PIPE)) {
            Token operatorToken = peek();
            consume();
            save();
            ExclusiveOrExpression rightConstruct = parseExclusiveOrExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected ExclusiveOrExpression after inclusiveorexpression operator." + operatorToken);
            }
            pop();
            InclusiveOrExpression nextExpression = new InclusiveOrExpression();
            nextExpression.setInclusiveOrExpression(construct);
            nextExpression.setExclusiveOrExpression(rightConstruct);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private ExclusiveOrExpression parseExclusiveOrExpression() throws SyntaxException {
        trace("parseExclusiveOrExpression");
        ExclusiveOrExpression construct = new ExclusiveOrExpression();
        save();
        construct.setAndExpression(parseAndExpression());
        if (construct.getAndExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.CARET)) {
            Token operatorToken = peek();
            consume();
            save();
            AndExpression rightConstruct = parseAndExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected AndExpression after exclusiveorexpression operator." + operatorToken);
            }
            pop();
            ExclusiveOrExpression nextExpression = new ExclusiveOrExpression();
            nextExpression.setExclusiveOrExpression(construct);
            nextExpression.setAndExpression(rightConstruct);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private AndExpression parseAndExpression() throws SyntaxException {
        trace("parseAndExpression");
        AndExpression construct = new AndExpression();
        save();
        construct.setEqualityExpression(parseEqualityExpression());
        if (construct.getEqualityExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.AMPERSAND)) {
            Token operatorToken = peek();
            consume();
            save();
            EqualityExpression rightConstruct = parseEqualityExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected EqualityExpression after qualityexpression operator." + operatorToken);
            }
            pop();
            AndExpression nextExpression = new AndExpression();
            nextExpression.setAndExpression(construct);
            nextExpression.setEqualityExpression(rightConstruct);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private EqualityExpression parseEqualityExpression() throws SyntaxException {
        trace("parseEqualityExpression");
        EqualityExpression construct = new EqualityExpression();
        save();
        construct.setRelationalExpression(parseRelationalExpression());
        if (construct.getRelationalExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.EQUALS_EQUALS) || isTokenType(TokenType.BANG_EQUALS)) {
            Token operatorToken = peek();
            consume();
            save();
            RelationalExpression rightConstruct = parseRelationalExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected RelationalExpression after qualityexpression operator." + operatorToken);
            }
            pop();
            EqualityExpression nextExpression = new EqualityExpression();
            nextExpression.setEqualityExpression(construct);
            nextExpression.setRelationalExpression(rightConstruct);

            nextExpression.setEqual(operatorToken.type() == TokenType.EQUALS_EQUALS);
            nextExpression.setNotEqual(operatorToken.type() == TokenType.BANG_EQUALS);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private RelationalExpression parseRelationalExpression() throws SyntaxException {
        trace("parseRelationalExpression");
        RelationalExpression construct = new RelationalExpression();
        save();
        construct.setShiftExpression(parseShiftExpression());
        if (construct.getShiftExpression() != null) {
            while (isTokenType(TokenType.LESS_THAN) || isTokenType(TokenType.GREATER_THAN) || isTokenType(TokenType.LESS_EQUALS) || isTokenType(TokenType.GREATER_EQUALS)) {
                Token operatorToken = peek();
                consume();
                save();
                ShiftExpression rightConstruct = parseShiftExpression();
                if (rightConstruct == null) {
                    revert();
                    break;
                }
                pop();
                RelationalExpression nextExpression = new RelationalExpression();
                nextExpression.setRelationalExpression(construct);
                nextExpression.setShiftExpression(rightConstruct);

                nextExpression.setLessThan(operatorToken.type() == TokenType.LESS_THAN);
                nextExpression.setGreterThan(operatorToken.type() == TokenType.GREATER_THAN);
                nextExpression.setLessThanOrEqualTo(operatorToken.type() == TokenType.LESS_EQUALS);
                nextExpression.setGreterThanOrEqualTo(operatorToken.type() == TokenType.GREATER_EQUALS);

                construct = nextExpression;
            }
            if (construct.getShiftExpression() != null) {
                if (construct.isGreterThan() ||
                    construct.isGreterThanOrEqualTo() ||
                    construct.isLessThan() ||
                    construct.isLessThanOrEqualTo()) {
                    pop();
                    return construct;
                } else {
                    InstanceofExpression ioe = parseInstanceofExpression(construct);
                    if (ioe != null) {
                        construct = new RelationalExpression();
                        construct.setInstanceofExpression(ioe);
                        pop();
                        return construct;
                    } else {
                        pop();
                        return construct;
                    }
                }
            }
        }
        revert();
        return null;
    }


    @LeftRecursionEliminated
    private ShiftExpression parseShiftExpression() throws SyntaxException {
        trace("parseShiftExpression");
        ShiftExpression construct = new ShiftExpression();
        save();
        construct.setAdditiveExpression(parseAdditiveExpression());
        if (construct.getAdditiveExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.LEFT_SHIFT) || isTokenType(TokenType.RIGHT_SHIFT) || isTokenType(TokenType.UNSIGNED_RIGHT_SHIFT)) {
            Token operatorToken = peek();
            consume();
            save();
            AdditiveExpression rightConstruct = parseAdditiveExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected AdditiveExpression after shiftexpression operator." + operatorToken);
            }
            pop();
            ShiftExpression nextExpression = new ShiftExpression();
            nextExpression.setShiftExpression(construct);
            nextExpression.setAdditiveExpression(rightConstruct);

            nextExpression.setShiftLeft(operatorToken.type() == TokenType.LEFT_SHIFT);
            nextExpression.setShiftRight(operatorToken.type() == TokenType.RIGHT_SHIFT);
            nextExpression.setUnsignedShiftRight(operatorToken.type() == TokenType.UNSIGNED_RIGHT_SHIFT);

            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private AdditiveExpression parseAdditiveExpression() throws SyntaxException {
        trace("parseAdditiveExpression");
        AdditiveExpression construct = new AdditiveExpression();
        save();
        construct.setMultiplicativeExpression(parseMultiplicativeExpression());
        if (construct.getMultiplicativeExpression() == null) {
            revert();
            return null;
        }
        while (isTokenType(TokenType.PLUS) || isTokenType(TokenType.MINUS)) {
            Token operatorToken = peek();
            consume();
            save();
            MultiplicativeExpression rightConstruct = parseMultiplicativeExpression();
            if (rightConstruct == null) {
                revert();
                throw new SyntaxException("Expected MultiplicativeExpression after additive operator." + operatorToken);
            }
            pop();
            AdditiveExpression nextExpression = new AdditiveExpression();
            nextExpression.setAdditiveExpression(construct);
            nextExpression.setMultiplicativeExpression(rightConstruct);
            nextExpression.setAdd(operatorToken.type() == TokenType.PLUS);
            nextExpression.setSubtract(operatorToken.type() == TokenType.MINUS);
            construct = nextExpression;
        }
        pop();
        return construct;
    }

    @LeftRecursionEliminated
    private MultiplicativeExpression parseMultiplicativeExpression() throws SyntaxException {
        trace("parseMultiplicativeExpression");
        MultiplicativeExpression construct = new MultiplicativeExpression();
        save();
        construct.setUnaryExpression(parseUnaryExpression());
        if (construct.getUnaryExpression() == null) {
            revert();
            return null;
        }
        // pop();
        while (isTokenType(TokenType.STAR) || isTokenType(TokenType.SLASH) || isTokenType(TokenType.PERCENT)) {
            Token operatorToken = peek();
            consume();
            save();
            UnaryExpression rightUnary = parseUnaryExpression();
            if (rightUnary == null) {
                revert();
                throw new SyntaxException("Expected UnaryExpression after multiplicative operator." + operatorToken);
            }
            pop();
            MultiplicativeExpression nextExpression = new MultiplicativeExpression();
            nextExpression.setMultiplicativeExpression(construct);
            nextExpression.setUnaryExpression(rightUnary);
            nextExpression.setMultiply(operatorToken.type() == TokenType.STAR);
            nextExpression.setDivide(operatorToken.type() == TokenType.SLASH);
            nextExpression.setModulo(operatorToken.type() == TokenType.PERCENT);
            construct = nextExpression;
        }
        pop();
        return construct;
    }

    /// @throws SyntaxException
    /// @see https://docs.oracle.com/javase/specs/jls/se25/html/jls-15.html#jls-Primary
    @JlsChapter("15.8")
    @RecursiveLoopDetection("PrimaryNoNewArray")
    Primary parsePrimary(int classIndex, int methodIndex, int fieldIndex) throws SyntaxException {
        trace("parsePrimary");
        Primary construct = new Primary();
        save();
        construct.setArrayCreationExpression(parseArrayCreationExpression());
        if (construct.getArrayCreationExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPrimaryNoNewArray(parsePrimaryNoNewArray(construct, classIndex, methodIndex, fieldIndex));
        if (construct.getPrimaryNoNewArray() != null) {
            save();
            if (fieldIndex == -1 && acceptAndConsume(TokenType.DOT)) {
            // if (isTokenType(TokenType.DOT)) {
                    // TODO: This needs to work with PrimaryNoNewArray and ArrayCreationExpression
                Primary chained = parsePrimary(classIndex, methodIndex, fieldIndex);
                if (chained != null) {
                    chain(chained, construct);
                    construct = chained;
                    pop();
                } else {
                    pop();
                }
            } else {
                pop();
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.8")
    @RecursiveLoopDetection("PrimaryNoNewArray")
    private PrimaryNoNewArray parsePrimaryNoNewArray(Primary primary, int classIndex, int methodIndex, int fieldIndex) throws SyntaxException {
            trace("parsePrimaryNoNewArray");
        PrimaryNoNewArray construct = new PrimaryNoNewArray();
        if (acceptAndConsume(TokenType.KEYWORD_THIS)) {
            construct.setThis(true);
            return construct;
        }
        save();
        if (acceptAndConsume(TokenType.LEFT_PAREN)) {
            construct.setExpression(parseExpression());
            if (construct.getExpression() != null) {
                consume(TokenType.RIGHT_PAREN);
                pop();
                return construct;
            }
        }
        rewind();
        construct.setLiteral(parseLiteral());
        if (construct.getLiteral() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setClassLiteral(parseClassLiteral());
        if (construct.getClassLiteral() != null) {
            pop();
            return construct;
        }

        rewind();
        construct.setArrayAccess(parseArrayAccess());
        if (construct.getArrayAccess() != null) {
            pop();
            return construct;
        }
        rewind();
        if (tokenIndex != classIndex) {
            construct.setClassInstanceCreationExpression(parseClassInstanceCreationExpression(classIndex, methodIndex, fieldIndex));
            if (construct.getClassInstanceCreationExpression() != null) {
                pop();
                return construct;
            }
        } else {
            reporter.reportDebug("PrimaryNoNewArray -> InstanceCreationExpression loop avoided");
        }
        rewind();
        // if (tokenIndex != methodIndex) {
            construct.setMethodInvocation(parseMethodInvocation(primary, classIndex, methodIndex, fieldIndex));
            if (tokenIndex == 2248) {
                System.out.println();
            }
            if (construct.getMethodInvocation() != null) {
                // TODO:
                //
                // For everything called from this (parsePrimaryNoNewArray) that can
                // Have a Primary (and maybe others line ExpressionName, TypeName...)
                // on the left, parsePrimaryNoNewArray (or parsePrimary) needs called
                // again.
                //
                // The new construct will take this construct as its left construct
                // and then it will take the palce of this construct.
                //
                // This should let test_parsePrimary_chainedMethodCalls work.
                // save();
                // if (acceptAndConsume(TokenType.DOT)) {
                //     // TODO: This needs to work with PrimaryNoNewArray and ArrayCreationExpression
                //     Primary chained = parsePrimary(classIndex, methodIndex, fieldIndex);
                //     if (chained != null) {
                //         setLeft(construct, chained.getPrimaryNoNewArray());
                //         construct = chained.getPrimaryNoNewArray();
                //     } else {
                //         pop();
                //     }
                // } else {
                //     pop();
                // }

                pop();
                return construct;
            }
        // }  else {
        //     reporter.reportDebug("PrimaryNoNewArray -> MethodInvocation loop avoided");
        // }
        rewind();
        construct.setMethodReference(parseMethodReference());
        if (construct.getMethodReference() != null) {
            pop();
            return construct;
        }
        rewind();
        if (tokenIndex != fieldIndex) {
            construct.setFieldAccess(parseFieldAccess(primary, classIndex, methodIndex, fieldIndex));
            if (construct.getFieldAccess() != null) {
                pop();
                return construct;
            }
        } else {
            reporter.reportDebug("PrimaryNoNewArray -> FieldAccess loop avoided");
        }
        rewind();

        construct.setTypeName(parseTypeName());
        if (construct.getTypeName() != null) {
            if (isTokenType(TokenType.KEYWORD_THIS)) {
                construct.setThis(true);
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.8.2")
    ClassLiteral parseClassLiteral() throws SyntaxException {
        trace("parseClassLiteral");
        ClassLiteral construct = new ClassLiteral();
        save();
        if (!acceptAndConsume(TokenType.KEYWORD_VOID)) {
            if (!acceptAndConsume(TokenType.KEYWORD_BOOLEAN)) {
                construct.setNumericType(parseNumericType());
                if (construct.getNumericType() == null) {
                    rewind();
                    construct.setTypeName(parseTypeName());
                    if (construct.getTypeName() == null) {
                        revert();
                        return null;
                    }
                }
            } else {
                construct.setBoolean(true);
            }
            construct.setDimensions(parseBrackets());
        } else {
            construct.setVoid(true);
        }

        if (!acceptAndConsume(TokenType.DOT)) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.KEYWORD_CLASS)) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    // TODO: Check this works
    private int parseBrackets() throws SyntaxException {
        int count = 0;
        save();
        while (acceptAndConsume(TokenType.LEFT_BRACKET)) {
            if (acceptAndConsume(TokenType.RIGHT_BRACKET)) {
                count++;
            } else {
                revert();
                return 0;
            }
        }
        pop();
        return count;
    }

    @JlsChapter("15.10.3")
    private ArrayAccess parseArrayAccess() throws SyntaxException {
        unimplemented("parseArrayAccess");
        return null;
    }

    @JlsChapter("15.11")
    @RecursiveLoopDetection("Primary")
    private FieldAccess parseFieldAccess(Primary primary, int classIndex, int methodIndex, int fieldIndex) throws SyntaxException {
        trace("parseFieldAccess");
        FieldAccess construct = new FieldAccess();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_SUPER)) {
            construct.setSuper(true);
        } else {
            // TODO: This code ain't right
            if (primary == null) {
                construct.setPrimary(parsePrimary(classIndex, methodIndex, tokenIndex));
            } else {
                construct.setPrimary(primary);
            }
            if (construct.getPrimary() == null) {
                rewind();
                construct.setTypeName(parseTypeName());
                if (construct.getTypeName() == null) {
                    revert();
                    return null;
                } else {
                    if (isTokenType(TokenType.DOT) && peek(2).type() == TokenType.KEYWORD_SUPER) {
                        consume();
                        consume();
                        construct.setSuper(true);
                    }
                }
            }
        }

        // if (construct.getPrimary() != null) {
        //     FieldAccess fieldAccess = construct.getPrimary().getFirstDescendant(FieldAccess.class);
        //     if (fieldAccess != null) {
        //         pop();
        //         return fieldAccess;
        //     }
        // }

        if (!acceptAndConsume(TokenType.DOT)) {
            revert();
            return null;
        }
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.12")
    Expression parseExpression() throws SyntaxException {
        trace("parseExpression");
        save();
        Expression construct = new Expression();
        construct.setLambdaExpression(parseLambdaExpression());
        if (construct.getLambdaExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setAssignmentExpression(parseAssignmentExpression());
        if (construct.getAssignmentExpression() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @Here
    @JlsChapter("15.12")
    @RecursiveLoopDetection("Primary")
    MethodInvocation parseMethodInvocation(Primary primary, int classIndex, int methodIndex, int fieldIndex) throws SyntaxException {
        trace("parseMethodInvocation");
        if (tokenIndex >= 2244) {
            System.out.println();
        }
        MethodInvocation construct = new MethodInvocation();
        save();
        construct.setMethodName(parseMethodName());
        if (construct.getMethodName() != null) {
            if (acceptAndConsume(TokenType.LEFT_PAREN)) {
                if (!isTokenType(TokenType.RIGHT_PAREN)) {
                    // TODO: FOr all things similar to this, check if right paren is next token and skip the argument list
                    construct.setArgumentList(parseArgumentList());
                }
                if (acceptAndConsume(TokenType.RIGHT_PAREN)) {
                    if (isTokenType(TokenType.DOT)) {
                        rewind();
                    } else {
                        pop();
                        return construct;
                    }
                }
            }
        }
        rewind();

        construct = new MethodInvocation();
        if (isTokenType(TokenType.KEYWORD_SUPER)) {
            construct.setSuper(true);
        } else {
            if (primary == null) {
                // TODO: Because of this null check, it is possible that methodIndex etc aren't needed here
                construct.setPrimary(parsePrimary(classIndex, tokenIndex, fieldIndex));
            }
            if (construct.getPrimary() == null) {
                rewind();
                construct.setExpressionName(parseExpressionName());
                if (construct.getExpressionName() == null) {
                    rewind();
                    construct.setTypeName(parseTypeName());
                    if (construct.getTypeName() == null) {
                        revert();
                        return null;
                    } else {
                        if (isTokenType(TokenType.DOT) && peek(2).type() == TokenType.KEYWORD_SUPER) {
                            consume();
                            consume();
                            construct.setSuper(true);
                        }
                    }
                }
            }
        }

        if (construct.getPrimary() != null) {
            MethodInvocation methodInvocation = construct.getPrimary().getFirstDescendant(MethodInvocation.class);
            if (methodInvocation != null) {
                pop();
                return methodInvocation;
            }
        }

        if (construct.getExpressionName() != null) {
            if (acceptAndConsume(TokenType.DOT)) {
                construct.setTypeArguments(parseTypeArguments());
                construct.setMethodName(parseMethodName());
            } else {

                    // && isTokenType(TokenType.LEFT_PAREN)) {

                // Unambiguating the ambiguous part of the ExpressionName

                UnqualifiedMethodIdentifier umi = new UnqualifiedMethodIdentifier();
                umi.setIdentifier(construct.getExpressionName().getIdentifier());

                ExpressionName en = new ExpressionName();
                if (construct.getExpressionName().getName() != null) {
                    Name ambiguousName = construct.getExpressionName().getName();
                    en.setName(ambiguousName.getName());
                    en.setIdentifier(ambiguousName.getIdentifier());

                    // en.setAmbiguousName(construct.getExpressionName().getAmbiguousName().getAmbiguousName());
                    // en.setIdentifier(construct.getExpressionName().getAmbiguousName().getIdentifier());
                }

                MethodName methodName = new MethodName();
                methodName.setUnqualifiedMethodIdentifier(umi);

                construct = new MethodInvocation();
                construct.setMethodName(methodName);
                construct.setExpressionName(en);
            }
        } else {
            if (!acceptAndConsume(TokenType.DOT)) {
                revert();
                return null;
            }
            construct.setTypeArguments(parseTypeArguments());
            construct.setMethodName(parseMethodName());
        }

        if (construct.getMethodName() == null) {
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.LEFT_PAREN)) {
            revert();
            return null;
        }
        construct.setArgumentList(parseArgumentList()); // TODO: If this fails, rewind bu tdont revert
        consume(TokenType.RIGHT_PAREN);
        pop();
        return construct;
    }

    @JlsChapter("15.13")
    private MethodReference parseMethodReference() throws SyntaxException {
        unimplemented("parseMethodReference");
        return null;
    }

    @JlsChapter("15.14")
    PostfixExpression parsePostfixExpression() throws SyntaxException {
        return parsePostfixExpression(-1, -1);
    }

    @RecursiveLoopDetection("PostfixExpression")
    PostfixExpression parsePostfixExpression(int decrementIndex, int incrementIndex) throws SyntaxException {
        trace("parsePostfixExpression");
        PostfixExpression construct = new PostfixExpression();
        save();
        construct.setPrimary(parsePrimary(-1, -1, -1));
        if (construct.getPrimary() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setExpressionName(parseExpressionName());
        if (construct.getExpressionName() != null) {
            pop();
            return construct;
        }
        rewind();
        if (tokenIndex != decrementIndex) {
            construct.setPostDecrementExpression(parsePostDecrementExpression(decrementIndex, incrementIndex));
            if (construct.getPostDecrementExpression() != null) {
                pop();
                return construct;
            }
        } else {
            reporter.reportDebug("PostfixExpression -> PostDecrementExpression loop avoided");
        }
        rewind();
        if (tokenIndex != incrementIndex) {
            construct.setPostIncrementExpression(parsePostIncrementExpression(decrementIndex, incrementIndex));
            if (construct.getPostIncrementExpression() != null) {
                pop();
                return construct;
            }
        } else {
            reporter.reportDebug("PostfixExpression -> PostIncrementExpression loop avoided");
        }
        revert();
        return null;
    }

    @Here // This is one of the main parts
    @JlsChapter("15.15")
    private UnaryExpressionNotPlusMinus parseUnaryExpressionNotPlusMinus() throws SyntaxException {
        trace("parseUnaryExpressionNotPlusMinus");
        UnaryExpressionNotPlusMinus construct = new UnaryExpressionNotPlusMinus();
        save();
        if (isTokenType(TokenType.TILDE)) {
            consume();
            construct.setTilde(true);
            construct.setUnaryExpression(parseUnaryExpression());
            pop();
            return construct;
        } else if (isTokenType(TokenType.BANG)) {
            consume();
            construct.setNot(true);
            construct.setUnaryExpression(parseUnaryExpression());
            pop();
            return construct;
        }
        save();
        construct.setCastExpression(parseCastExpression());
        if (construct.getCastExpression() != null) {
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setSwitchExpression(parseSwitchExpression());
        if (construct.getSwitchExpression() != null) {
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setPostfixExpression(parsePostfixExpression());
        if (construct.getPostfixExpression() != null) {
            pop();
            pop();
            return construct;
        }
        revert();
        revert();
        return null;
    }

    @JlsChapter("15.15")
    private PreIncrementExpression parsePreIncrementExpression() throws SyntaxException {
        unimplemented("parsePreIncrementExpression");
        return null;
    }

    @JlsChapter("15.15")
    private PreDecrementExpression parsePreDecrementExpression() throws SyntaxException {
        unimplemented("parsePreDecrementExpression");
        return null;
    }

    @JlsChapter("15.15")
    UnaryExpression parseUnaryExpression() throws SyntaxException {
        trace("parseUnaryExpression");
        UnaryExpression construct = new UnaryExpression();
        save();
        if (isTokenType(TokenType.PLUS)) {
            consume();
            construct.setAdd(true);
            construct.setUnaryExpression(parseUnaryExpression());
            pop();
            return construct;
        } else if (isTokenType(TokenType.MINUS)) {
            consume();
            construct.setSubtract(true);
            construct.setUnaryExpression(parseUnaryExpression());
            pop();
            return construct;
        }
        construct.setPreIncrementExpression(parsePreIncrementExpression());
        if (construct.getPreIncrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setPreDecrementExpression(parsePreDecrementExpression());
        if (construct.getPreDecrementExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setUnaryExpressionNotPlusMinus(parseUnaryExpressionNotPlusMinus());
        if (construct.getUnaryExpressionNotPlusMinus() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.20.2")
    private InstanceofExpression parseInstanceofExpression(RelationalExpression relationalExpression) throws SyntaxException {
        trace("parseInstanceofExpression");
        InstanceofExpression construct = new InstanceofExpression();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_INSTANCEOF)) {
            construct.setRelationalExpression(relationalExpression);
            save();
            construct.setPattern(parsePattern());
            if (construct.getPattern() != null) {
                pop();
                pop();
                return construct;
            }
            rewind();
            construct.setReferenceType(parseReferenceType());
            if (construct.getReferenceType() != null) {
                pop();
                pop();
                return construct;
            }
            revert();
        }
        revert();
        return null;
    }

    private ConditionalExpression parseConditionalExpression() throws SyntaxException {
        trace("parseConditionalExpression");
        ConditionalExpression construct = new ConditionalExpression();
        save();
        construct.setConditionalOrExpression(parseConditionalOrExpression());
        if (construct.getConditionalOrExpression() == null) {
            revert();
            return null;
        }
        if (isTokenType(TokenType.QUESTION_MARK)) {
            consume();
            construct.setExpression(parseExpression());
            if (construct.getExpression() == null) {
                revert();
                return null;
            }
            consume(TokenType.COLON);
            LambdaExpression lambda = parseLambdaExpression();
            if (lambda != null) {
                construct.setLambdaExpression(lambda);
                pop();
                return construct;
            }
            ConditionalExpression conditional = parseConditionalExpression();
            if (conditional != null) {
                construct.setConditionalExpression(conditional);
                pop();
                return construct;
            }
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("15.26")
    private AssignmentExpression parseAssignmentExpression() throws SyntaxException {
        trace("parseAssignmentExpression");
        AssignmentExpression construct = new AssignmentExpression();
        save();
        construct.setConditionalExpression(parseConditionalExpression());
        if (construct.getConditionalExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setAssignment(parseAssignment());
        if (construct.getAssignment() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.26")
    Assignment parseAssignment() throws SyntaxException {
        trace("parseAssignment");
        Assignment construct = new Assignment();
        save();
        construct.setLeftHandSide(parseLeftHandSide());
        if (construct.getLeftHandSide() == null) {
            revert();
            return null;
        }
        construct.setAssignmentOperator(parseAssignmentOperator());
        if (construct.getAssignmentOperator() == null) {
            revert();
            return null;
        }
        construct.setExpression(parseExpression());
        if (construct.getExpression() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    @JlsChapter("15.26")
    private LeftHandSide parseLeftHandSide() throws SyntaxException {
        trace("parseLeftHandSide");
        LeftHandSide construct = new LeftHandSide();
        save();
        construct.setExpressionName(parseExpressionName());
        if (construct.getExpressionName() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setFieldAccess(parseFieldAccess(null, -1, -1, -1));
        if (construct.getFieldAccess() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setArrayAccess(parseArrayAccess());
        if (construct.getArrayAccess() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.26")
    private AssignmentOperator parseAssignmentOperator() throws SyntaxException {
        trace("parseAssignmentOperator");
        // =  *=  /=  %=  +=  -=  <<=  >>=  >>>=  &=  ^=  |=
        switch (peek().type()) {
            case EQUALS, STAR_EQUALS, SLASH_EQUALS, PERCENT_EQUALS, PLUS_EQUALS, MINUS_EQUALS,
                    LEFT_SHIFT_EQUALS, RIGHT_SHIFT_EQUALS, UNSIGNED_RIGHT_SHIFT_EQUALS,
                    AMPERSAND_EQUALS, CARET_EQUALS, PIPE_EQUALS:
                Token token = consume();
                AssignmentOperator construct = new AssignmentOperator();
                construct.setType(token.type());
                return construct;
            default:
                break;
        }
        return null;
    }

    LambdaExpression parseLambdaExpression() throws SyntaxException {
        trace("parseLambdaExpression");
        LambdaExpression construct = new LambdaExpression();
        save();
        construct.setLambdaParameters(parseLambdaParameters());
        if (construct.getLambdaParameters() == null) {
            revert();
            return null;
        }
        if (!isTokenType(TokenType.ARROW)) {
            revert();
            return null;
        }
        consume();
        construct.setLambdaBody(parseLambdaBody());
        if (construct.getLambdaBody() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    private LambdaParameters parseLambdaParameters() throws SyntaxException {
        trace("parseLambdaParameters");
        LambdaParameters construct = new LambdaParameters();
        save();
        if (acceptAndConsume(TokenType.LEFT_PAREN)) {
            construct.setLambdaParameterList(parseLambdaParameterList());
            // if (construct.getLambdaParameterList() == null) {
            //     revert();
            //     return null;
            // }
            if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
                revert();
                return null;
            }
            pop();
            return construct;
        }
        construct.setConciseLambdaParameter(parseConciseLambdaParameter());
        if (construct.getConciseLambdaParameter() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    private LambdaBody parseLambdaBody() throws SyntaxException {
        trace("parseLambdaBody");
        LambdaBody construct = new LambdaBody();
        save();
        construct.setBlock(parseBlock());
        if (construct.getBlock() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setExpression(null);
        if (construct.getExpression() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    private LambdaParameterList parseLambdaParameterList() throws SyntaxException {
        trace("parseLambdaParameterList");
        LambdaParameterList construct = new LambdaParameterList();
        save();
        NormalLambdaParameter normal = parseNormalLambdaParameter();
        if (normal != null) {
            construct.addNormalLambdaParameter(normal);
            while(isTokenType(TokenType.COMMA)) {
                consume();
                NormalLambdaParameter item = parseNormalLambdaParameter();
                construct.addNormalLambdaParameter(item);
            }
            pop();
            return construct;
        }
        rewind();
        ConciseLambdaParameter concise = parseConciseLambdaParameter();
        if (concise != null) {
            construct.addConciseLambdaParameter(concise);
            while(isTokenType(TokenType.COMMA)) {
                consume();
                ConciseLambdaParameter item = parseConciseLambdaParameter();
                construct.addConciseLambdaParameter(item);
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    private NormalLambdaParameter parseNormalLambdaParameter() throws SyntaxException {
        trace("parseNormalLambdaParameter");
        NormalLambdaParameter construct = new NormalLambdaParameter();
        save();
        VariableArityParameter param = parseVariableArityParameter();
        if (param != null) {
            construct.setVariableArityParameter(param);
            pop();
            return construct;
        }
        rewind();
        List<VariableModifier> modifiers = parseVariableModifiers();
        for (VariableModifier item : modifiers) {
            construct.addVariableModifier(item);
        }
        construct.setLambdaParameterType(parseLambdaParameterType());
        if (construct.getLambdaParameterType() == null) {
            revert();
            return null;
        }
        construct.setVariableDeclaratorId(parseVariableDeclaratorId());
        if (construct.getVariableDeclaratorId() == null) {
            revert();
            return null;
        }
        pop();
        return construct;
    }

    private LambdaParameterType parseLambdaParameterType() throws SyntaxException {
        trace("parseLambdaParameterType");
        LambdaParameterType construct = new LambdaParameterType();
        if (isTokenType(TokenType.CONTEXTUAL_VAR)) {
            consume();
            construct.setVar(true);
            return construct;
        }
        save();
        construct.setUnannType(parseUnannType());
        if (construct.getUnannType() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    private ConciseLambdaParameter parseConciseLambdaParameter() throws SyntaxException {
        trace("parseConciseLambdaParameter");
        ConciseLambdaParameter construct = new ConciseLambdaParameter();
        if (isTokenType(TokenType.RESERVED_UNDERSCORE)) {
            consume();
            construct.setUnderscore(true);
            return construct;
        }
        save();
        construct.setIdentifier(parseIdentifier());
        if (construct.getIdentifier() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.9")
    @RecursiveLoopDetection("Primary")
    private ClassInstanceCreationExpression parseClassInstanceCreationExpression(int classIndex, int methodIndex, int fieldIndex) throws SyntaxException {
        trace("parseClassInstanceCreationExpression");
        ClassInstanceCreationExpression construct = new ClassInstanceCreationExpression();
        save();
        construct.setUnqualifiedClassInstanceCreationExpression(parseUnqualifiedClassInstanceCreationExpression());
        if (construct.getUnqualifiedClassInstanceCreationExpression() != null) {
            pop();
            return construct;
        }
        rewind();
        construct.setExpressionName(parseExpressionName());
        if (construct.getExpressionName() != null) {
            if (acceptAndConsume(TokenType.DOT)) {
                construct.setUnqualifiedClassInstanceCreationExpression(parseUnqualifiedClassInstanceCreationExpression());
                if (construct.getUnqualifiedClassInstanceCreationExpression() != null) {
                    pop();
                    return construct;
                }
            }
            revert();
            return null;
        }
        revert();

        save();
        // Problem: calling parsePrimary() here results in infinite recursion.
        // ArrayAccess requires that PrimaryNoNewArray can be ClassInstanceCreationExpression
        // Many places require Primary to be ClassInstanceCreationExpression
        //
        // Solution: boolean flag to parsePrimary and parsePrimaryNoNewArray to
        // indicate that they were called form here, and shouldn't recurse into here.
        construct.setPrimary(parsePrimary(tokenIndex, methodIndex, fieldIndex));
        if (construct.getPrimary() != null) {
            if (acceptAndConsume(TokenType.DOT)) {
                construct.setUnqualifiedClassInstanceCreationExpression(parseUnqualifiedClassInstanceCreationExpression());
                if (construct.getUnqualifiedClassInstanceCreationExpression() != null) {
                    pop();
                    return construct;
                }
            }
            revert();
            return null;
        }
        revert();
        return null;
    }

    @JlsChapter("15.9")
    UnqualifiedClassInstanceCreationExpression parseUnqualifiedClassInstanceCreationExpression() throws SyntaxException {
        trace("parseUnqualifiedClassInstanceCreationExpression");
        UnqualifiedClassInstanceCreationExpression construct = new UnqualifiedClassInstanceCreationExpression();
        save();
        if (acceptAndConsume(TokenType.KEYWORD_NEW)) {
            construct.setTypeArguments(parseTypeArguments());
            construct.setClassOrInterfaceTypeToInstantiate(parseClassOrInterfaceTypeToInstantiate());
            if (construct.getClassOrInterfaceTypeToInstantiate() == null) {
                revert();
                return null;
            }
            if (acceptAndConsume(TokenType.LEFT_PAREN)) {
                if (!isTokenType(TokenType.RIGHT_PAREN)) {
                    construct.setArgumentList(parseArgumentList());
                }
                consume(TokenType.RIGHT_PAREN);
            }
            save();
            construct.setClassBody(parseClassBody());
            if (construct.getClassBody() == null) {
                revert();
            } else {
                pop();
            }
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.9")
    private ClassOrInterfaceTypeToInstantiate parseClassOrInterfaceTypeToInstantiate() throws SyntaxException {
        trace("parseClassOrInterfaceTypeToInstantiate");
        ClassOrInterfaceTypeToInstantiate construct = new ClassOrInterfaceTypeToInstantiate();

        save();
        List<Annotation> annotations = new ArrayList<>();
        for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
            annotations.add(item);
        }
        Identifier identifier = parseIdentifier();
        if (identifier == null) {
            revert();
            return null;
        }
        Pair<List<Annotation>, Identifier> pair = Pair.of(annotations, identifier);
        construct.addAnnotationsIdentifierPairList(pair);
        while(acceptAndConsume(TokenType.COMMA)) {
            annotations = new ArrayList<>();
            for (Annotation item = parseAnnotation(); item != null; item = parseAnnotation()) {
                annotations.add(item);
            }
            identifier = parseIdentifier();
            if (identifier == null) {
                revert();
                return null; // TODO: Check other patterns like this
            }
            pair = Pair.of(annotations, identifier);
            construct.addAnnotationsIdentifierPairList(pair);
        }

        save();
        construct.setTypeArgumentsOrDiamond(parseTypeArgumentsOrDiamond());
        if (construct.getTypeArgumentsOrDiamond() == null) {
            revert();
        } else {
            pop();
        }
        pop();
        return construct;
    }

    @JlsChapter("15.9")
    private TypeArgumentsOrDiamond parseTypeArgumentsOrDiamond() throws SyntaxException {
        trace("parseTypeArgumentsOrDiamond");
        TypeArgumentsOrDiamond construct = new TypeArgumentsOrDiamond();
        save();
        if (acceptAndConsume(TokenType.LESS_THAN)) {
            if (acceptAndConsume(TokenType.GREATER_THAN)) {
                construct.setDiamond(true);
                pop();
                return construct;
            }
        }
        rewind();
        construct.setTypeArguments(parseTypeArguments());
        if (construct.getTypeArguments() != null) {
            pop();
            return construct;
        }
        revert();
        return null;
    }

    @JlsChapter("15.12")
    ArgumentList parseArgumentList() throws SyntaxException {
        trace("parseArgumentList");
        ArgumentList construct = new ArgumentList();
        save();
        Expression item = parseExpression();
        if (item == null) {
            revert();
            return null;
        }
        construct.addExpression(item);
        while(acceptAndConsume(TokenType.COMMA)) {
            item = parseExpression();
            if (item == null) {
                revert();
                return null;
            }
            construct.addExpression(item);
        }
        pop();
        return construct;
    }

    private ArrayCreationExpression parseArrayCreationExpression() throws SyntaxException {
        unimplemented("parseArrayCreationExpression");
        return null;
    }

    @JlsChapter("15.14.2")
    @RecursiveLoopDetection("PostfixExpression")
    PostIncrementExpression parsePostIncrementExpression(int decrementIndex, int incrementIndex) throws SyntaxException {
        trace("parsePostIncrementExpression");
        PostIncrementExpression construct = new PostIncrementExpression();
        save();
        //TODO: Uncomment this
        construct.setPostfixExpression(parsePostfixExpression(decrementIndex, tokenIndex));
        if (construct.getPostfixExpression() != null) {
            if (acceptAndConsume(TokenType.PLUS_PLUS)) {
                pop();
                return construct;
            }
        }
        revert();
        return null;
    }

    @JlsChapter("15.14.3")
    private PostDecrementExpression parsePostDecrementExpression(int decrementIndex, int incrementIndex) throws SyntaxException {
        unimplemented("parsePostDecrementExpression");
        return null;
    }

    @JlsChapter("15.16")
    CastExpression parseCastExpression() throws SyntaxException {
        trace("parseCastExpression");
        CastExpression construct = new CastExpression();
        if (!isTokenType(TokenType.LEFT_PAREN)) {
            return null;
        }
        save();
        consume();
        save();
        construct.setPrimitiveType(parsePrimitiveType());
        if (construct.getPrimitiveType() != null) {
            if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
                revert();
                revert();
                return null;
            }
            construct.setUnaryExpression(parseUnaryExpression());
            if (construct.getUnaryExpression() == null) {
                revert();
                revert();
                return null;
            }
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setReferenceType(parseReferenceType());
        if (construct.getReferenceType() == null) {
            revert();
            revert();
            return null;
        }
        if (!acceptAndConsume(TokenType.RIGHT_PAREN)) {
            revert();
            revert();
            return null;
        }
        pop();
        while (true) {
            save();
            AdditionalBound item = parseAdditionalBound();
            if (item != null) {
                construct.addAdditionalBound(item);
                pop();
            } else {
                revert();
                break;
            }
        }
        save();
        construct.setUnaryExpressionNotPlusMinus(parseUnaryExpressionNotPlusMinus());
        if (construct.getUnaryExpressionNotPlusMinus() != null) {
            pop();
            pop();
            return construct;
        }
        rewind();
        construct.setLambdaExpression(parseLambdaExpression());
        if (construct.getLambdaExpression() != null) {
            pop();
            pop();
            return construct;
        }
        revert();
        revert();
        return null;
    }

    @KeywordGate(TokenType.KEYWORD_SWITCH)
    private SwitchExpression parseSwitchExpression() throws SyntaxException {
        trace("parseSwitchExpression");
        SwitchExpression construct = new SwitchExpression();
        if (!acceptAndConsume(TokenType.KEYWORD_SWITCH)) {
            return null;
        }
        if (acceptAndConsume(TokenType.LEFT_PAREN)) {
            construct.setExpression(parseExpression());
            if (construct.getExpression() == null) {
                throw new ExpectedConstructException("expression", peek());
            }
            consume(TokenType.RIGHT_PAREN);
            construct.setSwitchBlock(parseSwitchBlock());
            if (construct.getSwitchBlock() == null) {
                throw new ExpectedConstructException("switch block", peek());
            }
            return construct;
        }
        throw new ExpectedTokenException(TokenType.LEFT_PAREN, peek());
    }
}
