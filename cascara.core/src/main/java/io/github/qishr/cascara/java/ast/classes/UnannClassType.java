package io.github.qishr.cascara.java.ast.classes;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.TypeIdentifier;
import io.github.qishr.cascara.java.ast.names.PackageName;
import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.ast.types.TypeArguments;

/// UnannClassType:
/// TypeIdentifier [TypeArguments]
/// PackageName . {Annotation} TypeIdentifier [TypeArguments]
/// UnannClassOrInterfaceType . {Annotation} TypeIdentifier [TypeArguments]
public class UnannClassType extends ASTNode {
    private UnannClassOrInterfaceType unannClassOrInterfaceType = null;
    private PackageName packageName = null;
    private List<Annotation> annotationList = new ArrayList<>();
    private TypeIdentifier typeIdentifier = null;
    private TypeArguments typeArguments = null;

    public UnannClassOrInterfaceType getUnannClassOrInterfaceType() {
        return unannClassOrInterfaceType;
    }

    public void setUnannClassOrInterfaceType(UnannClassOrInterfaceType unannClassOrInterfaceType) {
        this.unannClassOrInterfaceType = unannClassOrInterfaceType;
        addChild(unannClassOrInterfaceType);
    }

    public PackageName getPackageName() {
        return packageName;
    }

    public void setPackageName(PackageName packageName) {
        this.packageName = packageName;
        addChild(packageName);
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public TypeIdentifier getTypeIdentifier() {
        return typeIdentifier;
    }

    public void setTypeIdentifier(TypeIdentifier typeIdentifier) {
        this.typeIdentifier = typeIdentifier;
        addChild(typeIdentifier);
    }

    public TypeArguments getTypeArguments() {
        return typeArguments;
    }

    public void setTypeArguments(TypeArguments typeArguments) {
        this.typeArguments = typeArguments;
        addChild(typeArguments);
    }

    @Override
    public String toString() {
        if (typeIdentifier != null) {
            StringBuilder sb = new StringBuilder();
            if (packageName != null) {
                sb.append(packageName.toString());
                sb.append(".");
                for (Annotation annotation : annotationList) {
                    sb.append(annotation.toString());
                }
            } else if (unannClassOrInterfaceType != null) {
                sb.append(unannClassOrInterfaceType.toString());
                sb.append(".");
                for (Annotation annotation : annotationList) {
                    sb.append(annotation.toString());
                }
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
