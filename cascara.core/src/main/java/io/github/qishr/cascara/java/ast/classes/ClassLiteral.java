package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.names.TypeName;
import io.github.qishr.cascara.java.ast.types.NumericType;

/// ClassLiteral:
/// TypeName {[ ]} . class
/// NumericType {[ ]} . class
/// boolean {[ ]} . class
/// void . class
public class ClassLiteral extends ASTNode {
    private TypeName typeName = null;
    private NumericType numericType = null;
    private boolean isBoolean = false;
    private boolean isVoid = false;
    private int dimensions = 0;

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    public NumericType getNumericType() {
        return numericType;
    }

    public void setNumericType(NumericType numericType) {
        this.numericType = numericType;
        addChild(numericType);
    }

    public boolean isBoolean() {
        return isBoolean;
    }

    public void setBoolean(boolean isBoolean) {
        this.isBoolean = isBoolean;
    }

    public boolean isVoid() {
        return isVoid;
    }

    public void setVoid(boolean isVoid) {
        this.isVoid = isVoid;
    }

    public int getDimensions() {
        return dimensions;
    }

    public void addDimension() {
        dimensions++;
    }

    public void setDimensions(int dimensions) {
        this.dimensions = dimensions;
    }
}
