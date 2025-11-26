package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// MethodModifier:
/// (one of)
/// Annotation public protected private
/// abstract static final synchronized native strictfp
public class MethodModifier extends ASTNode {
    Annotation annotation;
    TokenType value;

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
