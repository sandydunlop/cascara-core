package io.github.qishr.cascara.java.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.types.AdditionalBound;
import io.github.qishr.cascara.java.ast.types.PrimitiveType;
import io.github.qishr.cascara.java.ast.types.ReferenceType;

/// CastExpression:
/// ( PrimitiveType ) UnaryExpression
/// ( ReferenceType {AdditionalBound} ) UnaryExpressionNotPlusMinus
/// ( ReferenceType {AdditionalBound} ) LambdaExpression
public class CastExpression extends ASTNode {
    private PrimitiveType primitiveType = null;
    private UnaryExpression unaryExpression = null;
    private ReferenceType referenceType = null;
    private List<AdditionalBound> additionalBoundList = new ArrayList<>();
    private UnaryExpressionNotPlusMinus unaryExpressionNotPlusMinus = null;
    private LambdaExpression lambdaExpression = null;

    public PrimitiveType getPrimitiveType() {
        return primitiveType;
    }

    public void setPrimitiveType(PrimitiveType primitiveType) {
        this.primitiveType = primitiveType;
        addChild(primitiveType);
    }

    public UnaryExpression getUnaryExpression() {
        return unaryExpression;
    }

    public void setUnaryExpression(UnaryExpression unaryExpression) {
        this.unaryExpression = unaryExpression;
        addChild(unaryExpression);
    }

    public ReferenceType getReferenceType() {
        return referenceType;
    }

    public void setReferenceType(ReferenceType referenceType) {
        this.referenceType = referenceType;
        addChild(referenceType);
    }

    public List<AdditionalBound> getAdditionalBoundList() {
        return additionalBoundList;
    }

    public void addAdditionalBound(AdditionalBound item) {
        additionalBoundList.add(item);
        addChild(item);
    }

    public UnaryExpressionNotPlusMinus getUnaryExpressionNotPlusMinus() {
        return unaryExpressionNotPlusMinus;
    }

    public void setUnaryExpressionNotPlusMinus(UnaryExpressionNotPlusMinus unaryExpressionNotPlusMinus) {
        this.unaryExpressionNotPlusMinus = unaryExpressionNotPlusMinus;
        addChild(unaryExpressionNotPlusMinus);
    }

    public LambdaExpression getLambdaExpression() {
        return lambdaExpression;
    }

    public void setLambdaExpression(LambdaExpression lambdaExpression) {
        this.lambdaExpression = lambdaExpression;
        addChild(lambdaExpression);
    }

}
