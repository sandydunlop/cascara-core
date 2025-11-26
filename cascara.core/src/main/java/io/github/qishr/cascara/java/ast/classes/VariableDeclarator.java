package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.semantic.ConstantFieldOrVariableType;
import io.github.qishr.cascara.java.ast.semantic.Declaration;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.VariableInitializer;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// VariableDeclarator:
/// VariableDeclaratorId [= VariableInitializer]
public class VariableDeclarator extends ASTNode implements Identifiable, Declaration {
    private VariableDeclaratorId variableDeclaratorId = null;
    private VariableInitializer variableInitializer = null;

    @Override
    public Token getNameToken() {
        return variableDeclaratorId == null ? null : variableDeclaratorId.getNameToken();
    }

    @Override
    public String getNameString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public VariableDeclaratorId getVariableDeclaratorId() {
        return variableDeclaratorId;
    }

    public void setVariableDeclaratorId(VariableDeclaratorId variableDeclaratorId) {
        this.variableDeclaratorId = variableDeclaratorId;
        addChild(variableDeclaratorId);
    }

    public VariableInitializer getVariableInitializer() {
        return variableInitializer;
    }

    public void setVariableInitializer(VariableInitializer variableInitializer) {
        this.variableInitializer = variableInitializer;
        addChild(variableInitializer);
    }

    public ConstantFieldOrVariableType getDeclaredType() {
        ASTNode declarationGroup = getParent();
        if (declarationGroup == null) {
            return null;
        }
        ASTNode declaration = declarationGroup.getParent();
        if (declaration instanceof ConstantFieldOrVariableType type) {
            return type;
        } else {
            return null;
        }
    }
}
