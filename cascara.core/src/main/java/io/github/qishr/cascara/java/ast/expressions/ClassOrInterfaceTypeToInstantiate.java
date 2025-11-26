package io.github.qishr.cascara.java.ast.expressions;

import io.github.qishr.cascara.common.Pair;
import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;

import java.util.ArrayList;
import java.util.List;

/// ClassOrInterfaceTypeToInstantiate:
/// {Annotation} Identifier {. {Annotation} Identifier} [TypeArgumentsOrDiamond]
public class ClassOrInterfaceTypeToInstantiate extends ASTNode {
    // private List<Annotation> annotationList = new ArrayList<>();
    // private Identifier identifier = null;
    private List<Pair<List<Annotation>, Identifier>> annotationsIdentifierPairList = new ArrayList<>();
    private TypeArgumentsOrDiamond typeArgumentsOrDiamond = null;

    public void addAnnotationsIdentifierPairList(Pair<List<Annotation>, Identifier> item) {
        annotationsIdentifierPairList.add(item);
        for (Annotation annotation : item.getL()) {
            addChild(annotation);
        }
        addChild(item.getR());
    }

    public TypeArgumentsOrDiamond getTypeArgumentsOrDiamond() {
        return typeArgumentsOrDiamond;
    }
    public void setTypeArgumentsOrDiamond(TypeArgumentsOrDiamond typeArgumentsOrDiamond) {
        this.typeArgumentsOrDiamond = typeArgumentsOrDiamond;
        addChild(typeArgumentsOrDiamond);
    }


}
