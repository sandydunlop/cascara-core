package io.github.qishr.cascara.java.ast.expressions;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ArgumentList:
/// Expression {, Expression}
public class ArgumentList extends ASTNode {
    private List<Expression> expressionList = new ArrayList<>();

    //TODO: FOr this and others like it, ansure list contains one item before returning non-null
    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void addExpression(Expression item) {
        expressionList.add(item);
        addChild(item);
    }
}
