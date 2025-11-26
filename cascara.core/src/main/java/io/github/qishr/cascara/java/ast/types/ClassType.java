package io.github.qishr.cascara.java.ast.types;

import java.util.ArrayList;

import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.names.PackageName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;

/// ClassType:
/// {Annotation} TypeIdentifier [TypeArguments]
/// PackageName . {Annotation} TypeIdentifier [TypeArguments]
/// ClassOrInterfaceType . {Annotation} TypeIdentifier [TypeArguments]
public class ClassType extends ASTNode {
    private List<Annotation> annotationList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private PackageName packageName = null;
    private ClassOrInterfaceType classOrInterfaceType = null;
    private TypeArguments typeArguments = null;

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public void setPackageName(PackageName packageName) {
        this.packageName = packageName;
        addChild(packageName);
    }

    public ClassOrInterfaceType getClassOrInterfaceType() {
        return classOrInterfaceType;
    }

    public void setClassOrInterfaceType(ClassOrInterfaceType classOrInterfaceType) {
        this.classOrInterfaceType = classOrInterfaceType;
        addChild(classOrInterfaceType);
    }

    @Override
    public String toString() {
        if (typeIdentifier != null) {
            StringBuilder sb = new StringBuilder();
            if (packageName != null) {
                sb.append(packageName.toString());
                sb.append(".");
            } else if (classOrInterfaceType != null) {
                sb.append(classOrInterfaceType.toString());
                sb.append(".");
            }
            for (Annotation annotation : annotationList) {
                sb.append(annotation.toString());
            }
            sb.append(typeIdentifier.toString());
            if (typeArguments != null) {
                sb.append(typeArguments.toString());
            }
            return sb.toString();
        }else {
            return StringConstant.UNDEFINED;
        }
    }
}
