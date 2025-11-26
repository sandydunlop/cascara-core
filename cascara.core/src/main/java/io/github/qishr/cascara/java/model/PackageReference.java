package io.github.qishr.cascara.java.model;

public class PackageReference extends SemanticNode {
    private String nameString;
    private Link link;

    public PackageReference(String n) {
        this.nameString = n;
        link = Link.to(NameUtil.createReference(n));
    }

    public void setName(String n) {
        this.nameString = n;
    }

    public String getNameString() {
        return this.nameString;
    }

    // public String getName() {
    //     return name;
    // }

    public void setLink(Link l) {
        link = l;
    }

    public Link getLink() {
        return link;
    }
}
