package io.github.qishr.cascara.java.parser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.common.diagnostic.Diagnostic.Level;
import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.parser.Parser;
import io.github.qishr.cascara.java.parser.Tokenizer;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

public class ParserTestsBase {

    StringWriter stringWriter = new StringWriter();

    static Parser parser;
    static Reporter reporter;

    // static TestReporter reporter;

    // class TestReporter implements Reporter {
    //     public StringWriter stringWriter;

    //     @Override
    //     public void print(Diagnostic.Kind kind, String message) {
    //         stringWriter.write(message);
    //     }

    //     @Override
    //     public void print(Diagnostic.Kind kind, DocTreePath path, String message) {
    //         stringWriter.write(message);
    //     }

    //     @Override
    //     public void print(Diagnostic.Kind kind, Element element, String message) {
    //         stringWriter.write(message);
    //     }
    // }

    void parserTestSetup() {
        reporter = new Reporter();
        reporter.setLevel(Level.TRACE);
        parser = new Parser(reporter);
        // reporter.stringWriter = new StringWriter();
        parser.tokenIndex = 0;
        parser.tokens = new ArrayList<>();
    }

    static void setCode(String code) {
        parser.tokens = Tokenizer.tokenize(reporter, code, false, false);
    }

    static <T extends ASTNode> List<T> listConstructs(ASTNode node, Class<T> type) {
        return node.getDescendants(type);
    }

    // static <T extends ASTNode> void traverseConstructs(ASTNode node, Class<T> type, List<T> constructList) {
    //     if (type.isInstance(node)) {
    //         constructList.add(type.cast(node));
    //     }
    //     for (ASTNode child : node.getChildren()) {
    //         traverseConstructs(child, type, constructList);
    //     }
    // }

    static void addToken(TokenType type) {
        parser.tokens.add(new Token(type, "", 0, 0, 0, null));
    }

    static void addToken(TokenType type, String lexeme) {
        parser.tokens.add(new Token(type, lexeme, 0, 0, 0, null));
    }

    static void setStack(TokenType type, String lexeme) {
        parser.tokenIndex = 0;
        parser.tokens = new ArrayList<>();
        parser.tokens.add(new Token(type, lexeme, 0, 0, 0, null));
    }



    void traverse(ASTNode node) {
        System.out.println(node.getClass().getName());
        for (ASTNode child : node.getChildren()) {
            traverse(child);
        }
    }
}