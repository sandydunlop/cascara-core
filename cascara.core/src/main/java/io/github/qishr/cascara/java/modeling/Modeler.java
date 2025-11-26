package io.github.qishr.cascara.java.modeling;

import io.github.qishr.cascara.java.model.ClassNode;
import io.github.qishr.cascara.java.model.FieldNode;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.ModuleNode;
import io.github.qishr.cascara.java.model.PackageNode;
import io.github.qishr.cascara.java.model.ParamNode;
import io.github.qishr.cascara.java.model.TypeNode;

public interface Modeler<A,B,C,F,M,P> {
    public ModuleNode modelModule(A m);
    public PackageNode modelPackage(B p);
    public TypeNode modelType(C t);
    public ClassNode modelClass(C t);
    public FieldNode modelField(F f);
    public MethodNode modelMethod(M m);
    public ParamNode modelParam(P p);
}

