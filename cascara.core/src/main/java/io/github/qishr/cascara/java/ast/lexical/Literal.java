package io.github.qishr.cascara.java.ast.lexical;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Literal:
/// IntegerLiteral
/// FloatingPointLiteral
/// BooleanLiteral
/// CharacterLiteral
/// StringLiteral
/// TextBlock
/// NullLiteral
public class Literal extends ASTNode {
    private IntegerLiteral integerLiteral = null;
    private FloatingPointLiteral floatingPointLiteral = null;
    private BooleanLiteral booleanLiteral = null;
    private CharacterLiteral characterLiteral = null;
    private StringLiteral stringLiteral = null;
    private TextBlock textBlock = null;
    private NullLiteral nullLiteral = null;

    public IntegerLiteral getIntegerLiteral() {
        return integerLiteral;
    }

    public void setIntegerLiteral(IntegerLiteral integerLiteral) {
        this.integerLiteral = integerLiteral;
        addChild(integerLiteral);
    }

    public FloatingPointLiteral getFloatingPointLiteral() {
        return floatingPointLiteral;
    }

    public void setFloatingPointLiteral(FloatingPointLiteral floatingPointLiteral) {
        this.floatingPointLiteral = floatingPointLiteral;
        addChild(floatingPointLiteral);
    }

    public BooleanLiteral getBooleanLiteral() {
        return booleanLiteral;
    }

    public void setBooleanLiteral(BooleanLiteral booleanLiteral) {
        this.booleanLiteral = booleanLiteral;
        addChild(booleanLiteral);
    }

    public CharacterLiteral getCharacterLiteral() {
        return characterLiteral;
    }

    public void setCharacterLiteral(CharacterLiteral characterLiteral) {
        this.characterLiteral = characterLiteral;
        addChild(characterLiteral);
    }

    public StringLiteral getStringLiteral() {
        return stringLiteral;
    }

    public void setStringLiteral(StringLiteral stringLiteral) {
        this.stringLiteral = stringLiteral;
        addChild(stringLiteral);
    }

    public TextBlock getTextBlock() {
        return textBlock;
    }

    public void setTextBlock(TextBlock textBlock) {
        this.textBlock = textBlock;
        addChild(textBlock);
    }

    public NullLiteral getNullLiteral() {
        return nullLiteral;
    }

    public void setNullLiteral(NullLiteral nullLiteral) {
        this.nullLiteral = nullLiteral;
        addChild(nullLiteral);
    }
}
