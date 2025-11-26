package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.semantic.Modifier;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// FieldModifier:
/// (one of)
/// Annotation public protected private
/// static final transient volatile
public class FieldModifier extends ASTNode implements Modifier {
    private Annotation annotation = null;
    private TokenType type = TokenType.UNRECOGNIZED;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    @Override
    public TokenType getTokenType() {
        return type;
    }

    public void setTokenType(TokenType type) {
        this.type = type;
    }
}
