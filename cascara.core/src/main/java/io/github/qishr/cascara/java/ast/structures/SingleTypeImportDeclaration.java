package io.github.qishr.cascara.java.ast.structures;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

/// SingleTypeImportDeclaration:
/// import TypeName ;
public class SingleTypeImportDeclaration extends ASTNode implements Identifiable {
    private TypeName typeName = null;

    @Override
    public Token getNameToken() {
        return typeName == null ? null : typeName.getNameToken();
    }

    @Override
    public String getNameString() {
        return toString();
    }

    @Override
    public String toString() {
        return getNameToken() == null ? StringConstant.UNNAMED : getNameToken().lexeme();
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

}
