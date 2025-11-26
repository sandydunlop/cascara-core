package io.github.qishr.cascara.java.ast.classes;

import io.github.qishr.cascara.java.ast.ASTNode;

/// Throws:
/// throws ExceptionTypeList
public class Throws extends ASTNode {
    private ExceptionTypeList exceptionTypeList = null;

    public ExceptionTypeList getExceptionTypeList() {
        return exceptionTypeList;
    }

    public void setExceptionTypeList(ExceptionTypeList exceptionTypeList) {
        this.exceptionTypeList = exceptionTypeList;
        addChild(exceptionTypeList);
    }

}
