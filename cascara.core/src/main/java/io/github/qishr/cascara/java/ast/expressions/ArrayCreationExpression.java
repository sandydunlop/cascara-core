package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.Dims;

/// ArrayCreationExpression:
/// new PrimitiveType DimExprs [Dims]
/// new ClassOrInterfaceType DimExprs [Dims]
/// new PrimitiveType Dims ArrayInitializer
/// new ClassOrInterfaceType Dims ArrayInitializer
public class ArrayCreationExpression extends ASTNode {

}
