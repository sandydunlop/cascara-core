package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;

/// ExceptionTypeList:
/// ExceptionType {, ExceptionType}
public class ExceptionTypeList extends ASTNode {
    private List<ExceptionType> exceptionTypeList = new ArrayList<>();

    public void addExceptionType(ExceptionType item) {
        exceptionTypeList.add(item);
        addChild(item);
    }

    public List<ExceptionType> getExceptionTypeList() {
        return exceptionTypeList;
    }
}
