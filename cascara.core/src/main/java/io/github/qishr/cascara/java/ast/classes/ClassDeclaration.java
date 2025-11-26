package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ClassDeclaration:
/// NormalClassDeclaration
/// EnumDeclaration
/// RecordDeclaration
public class ClassDeclaration extends ASTNode implements Identifiable {
    private NormalClassDeclaration normalClassDeclaration = null;
    private EnumDeclaration enumDeclaration = null;
    private RecordDeclaration recordDeclaration = null;

    public Declaration getDeclaration() {
        if (normalClassDeclaration != null) {
            return normalClassDeclaration;
        } else if (enumDeclaration != null) {
            return enumDeclaration;
        } else if (recordDeclaration != null) {
            return recordDeclaration;
        } else {
            return null;
        }
    }

    @Override
    public Token getNameToken() {
        if (normalClassDeclaration != null) {
            return normalClassDeclaration.getNameToken();
        } else if (enumDeclaration != null) {
            return enumDeclaration.getNameToken();
        } else if (recordDeclaration != null) {
            return recordDeclaration.getNameToken();
        } else {
            return null;
        }
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public NormalClassDeclaration getNormalClassDeclaration() {
        return normalClassDeclaration;
    }

    public void setNormalClassDeclaration(NormalClassDeclaration normalClassDeclaration) {
        this.normalClassDeclaration = normalClassDeclaration;
        addChild(normalClassDeclaration);
    }

    public EnumDeclaration getEnumDeclaration() {
        return enumDeclaration;
    }

    public void setEnumDeclaration(EnumDeclaration enumDeclaration) {
        this.enumDeclaration = enumDeclaration;
        addChild(enumDeclaration);
    }

    public RecordDeclaration getRecordDeclaration() {
        return recordDeclaration;
    }

    public void setRecordDeclaration(RecordDeclaration recordDeclaration) {
        this.recordDeclaration = recordDeclaration;
        addChild(recordDeclaration);
    }
}
