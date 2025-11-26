package io.github.qishr.cascara.java.ast.interfaces;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.parser.Tokenizer.TokenType;

/// InterfaceModifier:
/// (one of)
/// Annotation public protected private
/// abstract static sealed non-sealed strictfp
public class InterfaceModifier extends ASTNode {
    private Annotation annotation = null;
    private TokenType type = TokenType.UNRECOGNIZED;

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
        addChild(annotation);
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }


}
