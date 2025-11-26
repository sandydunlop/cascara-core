package io.github.qishr.cascara.java.ast.names;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// ModuleName:
/// Identifier
/// ModuleName . Identifier
public class ModuleName extends ASTNode implements Identifiable {
    // TODO implements Name
    private Identifier identifier = null;
    private ModuleName moduleName = null;

    @Override
    public Token getNameToken() {
        return identifier == null ? null : identifier.getNameToken();
    }

    @Override
    public String getNameString() {
        return toString();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
        addChild(identifier);
    }

    public ModuleName getModuleName() {
        return moduleName;
    }

    public void setModuleName(ModuleName moduleName) {
        this.moduleName = moduleName;
        addChild(moduleName);
    }
}
