package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.classes.ClassLiteral;
import io.github.qishr.cascara.java.ast.lexical.Literal;
import io.github.qishr.cascara.java.ast.names.TypeName;

/// PrimaryNoNewArray:
/// Literal
/// ClassLiteral
/// this
/// TypeName . this
/// ( Expression )
/// ClassInstanceCreationExpression
/// FieldAccess
/// ArrayAccess
/// MethodInvocation
/// MethodReference
public class PrimaryNoNewArray extends ASTNode {
    private Literal literal = null;
    private ClassLiteral classLiteral = null;
    private boolean isThis = false;
    private TypeName typeName = null;
    private Expression expression = null;
    private ClassInstanceCreationExpression classInstanceCreationExpression = null;
    private FieldAccess fieldAccess = null;
    private ArrayAccess arrayAccess = null;
    private MethodInvocation methodInvocation = null;
    private MethodReference methodReference = null;

    public Literal getLiteral() {
        return literal;
    }

    public void setLiteral(Literal literal) {
        this.literal = literal;
        addChild(literal);
    }

    public ClassLiteral getClassLiteral() {
        return classLiteral;
    }

    public void setClassLiteral(ClassLiteral classLiteral) {
        this.classLiteral = classLiteral;
        addChild(classLiteral);
    }

    public boolean isThis() {
        return isThis;
    }

    public void setThis(boolean isThis) {
        this.isThis = isThis;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public void setTypeName(TypeName typeName) {
        this.typeName = typeName;
        addChild(typeName);
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
        addChild(expression);
    }

    public ClassInstanceCreationExpression getClassInstanceCreationExpression() {
        return classInstanceCreationExpression;
    }

    public void setClassInstanceCreationExpression(ClassInstanceCreationExpression classInstanceCreationExpression) {
        this.classInstanceCreationExpression = classInstanceCreationExpression;
        addChild(classInstanceCreationExpression);
    }

    public FieldAccess getFieldAccess() {
        return fieldAccess;
    }

    public void setFieldAccess(FieldAccess fieldAccess) {
        this.fieldAccess = fieldAccess;
        addChild(fieldAccess);
    }

    public ArrayAccess getArrayAccess() {
        return arrayAccess;
    }

    public void setArrayAccess(ArrayAccess arrayAccess) {
        this.arrayAccess = arrayAccess;
        addChild(arrayAccess);
    }

    public MethodInvocation getMethodInvocation() {
        return methodInvocation;
    }

    public void setMethodInvocation(MethodInvocation methodInvocation) {
        this.methodInvocation = methodInvocation;
        addChild(methodInvocation);
    }

    public MethodReference getMethodReference() {
        return methodReference;
    }

    public void setMethodReference(MethodReference methodReference) {
        this.methodReference = methodReference;
        addChild(arrayAccess);
    }
}
