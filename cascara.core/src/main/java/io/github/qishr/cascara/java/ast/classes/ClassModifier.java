package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// ClassModifier:
/// (one of)
/// Annotation public protected private
/// abstract static final sealed non-sealed strictfp
public class ClassModifier extends ASTNode {
    Annotation annotation;
    TokenType value;

    public ClassModifier() {
        // Nothing to see here
    }

    public ClassModifier(Token token) {
        value = token.type();
    }

    public ClassModifier(Annotation annotation) {
        setAnnotation(annotation);
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public TokenType getValue() {
        return value;
    }

    public void setValue(TokenType value) {
        this.value = value;
    }
}
