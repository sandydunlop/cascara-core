package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// CharacterLiteral:
/// ' SingleCharacter '
/// ' EscapeSequence '
public class CharacterLiteral extends ASTNode {
    private char value = '\0';

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }


}
