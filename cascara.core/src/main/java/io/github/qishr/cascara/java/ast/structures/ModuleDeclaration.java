package io.github.qishr.cascara.java.ast.structures;

import java.util.ArrayList;
import java.util.List;

import io.github.qishr.cascara.java.ast.ASTNode;
import io.github.qishr.cascara.java.ast.interfaces.Annotation;
import io.github.qishr.cascara.java.ast.lexical.Identifier;
import io.github.qishr.cascara.java.ast.semantic.Identifiable;
import io.github.qishr.cascara.java.parser.Tokenizer.Token;

//TODO: Surely instead of Identifier here it should be ModuleName?

/// {Annotation} [open] module Identifier {. Identifier} { {ModuleDirective} }
public class ModuleDeclaration extends ASTNode implements Identifiable {
    private List<Annotation> annotationList = new ArrayList<>();
    private boolean isOpen = false;
    private List<Identifier> identifierList = new ArrayList<>();
    private List<ModuleDirective> moduleDirectiveList = new ArrayList<>();

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void addAnnotation(Annotation item) {
        annotationList.add(item);
        addChild(item);
    }

    public List<Identifier> getIdentifierList() {
        return identifierList;
    }

    public void addIdentifier(Identifier item) {
        identifierList.add(item);
        addChild(item);
    }

    public List<ModuleDirective> getModuleDirectiveList() {
        return moduleDirectiveList;
    }

    public void addModuleDirective(ModuleDirective item) {
        moduleDirectiveList.add(item);
        addChild(item);
    }

    @Override
    public Token getNameToken() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getNameToken'");
    }

    @Override
    public String getNameString() {
        StringBuilder sb = new StringBuilder();
        for (Identifier identifier : identifierList) {
            if (!sb.isEmpty()) {
                sb.append(".");
            }
            sb.append(identifier.getNameString());
        }
        return sb.toString();
    }

}
