package io.github.qishr.cascara.java.sample;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.anno.JlsChapter;
import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.names.PackageOrTypeName;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.ast.structures.SingleStaticImportDeclaration;
import io.github.qishr.cascara.java.ast.types.Dims;
import io.github.qishr.cascara.java.parser.ParserBase;
import io.github.qishr.cascara.java.parser.SyntaxException;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public class Backup extends ParserBase {
    public Backup(Reporter reporter) {
        super(reporter);
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
        while (acceptAndConsume(TokenType.DOT)) {
            TypeIdentifier ti = parseTypeIdentifier();
            PackageOrTypeName packageOrTypeName = new PackageOrTypeName();
            packageOrTypeName.setPackageOrTypeName(construct.getPackageOrTypeName());
            packageOrTypeName.setIdentifier(construct.getTypeIdentifier().getIdentifier());
            construct.getChildren().remove(construct.getPackageOrTypeName());
            construct.getChildren().remove(construct.getTypeIdentifier());
            construct.setPackageOrTypeName(packageOrTypeName);
            construct.setTypeIdentifier(ti);
            if (construct.getTypeIdentifier() == null) {
                revert();
                return null;
            }
        }
        pop();
        return construct;
    }

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

    @Override
    protected CompilationUnit parseCompilationUnit() throws SyntaxException {
        throw new UnsupportedOperationException("Unimplemented method 'parseCompilationUnit'");
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

    @JlsChapter("4.3")
    private Dims parseDims() throws SyntaxException {
        trace("parseDims");
        Dims construct = null;
        while(true) {
            List<Annotation> annotations = new ArrayList<>();
            for (int item = 0; item < 4; item++) {
                System.out.println();
            }

            if (isTokenType(TokenType.LEFT_BRACKET)) {
                consume(TokenType.LEFT_BRACKET);
                consume(TokenType.RIGHT_BRACKET);
                if (construct == null) {
                    construct = new Dims();
                }
                construct.getAnnotationList().add(annotations);
                for (Annotation item : annotations) {
                    construct.addChild(item);
                }
                return construct;
            } else {
                break;
            }
        }
        return construct;
    }
}
