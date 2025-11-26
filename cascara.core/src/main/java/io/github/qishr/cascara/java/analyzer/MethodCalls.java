package io.github.qishr.cascara.java.analyzer;

import javax.tools.*;

import com.sun.source.util.*;
import com.sun.source.tree.*;
import java.io.*;
import java.util.*;

public class MethodCalls extends TreeScanner<Void, Void> {
    private List<String> methodsCalled = new ArrayList<>();

    public MethodCalls(File sourceFile)  throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjects(sourceFile);
        for (JavaFileObject javaFileObject : compilationUnits) {
            // Parse the source file
            JavacTask task = (JavacTask) compiler.getTask(null, fileManager, null, null, null, Arrays.asList(javaFileObject));
            Iterable<? extends CompilationUnitTree> units = task.parse();
            for (CompilationUnitTree unit : units) {
                // Visit the AST
                unit.accept(this, null);
            }
        }
        fileManager.close();
    }

    @Override
    public Void visitMethodInvocation(MethodInvocationTree node, Void unused) {
        methodsCalled.add(node.getMethodSelect().toString());
        return super.visitMethodInvocation(node, unused);
    }

    public boolean isMethodCalled(String methodNameToCheck) {
        return methodsCalled.contains(methodNameToCheck);
    }
}
