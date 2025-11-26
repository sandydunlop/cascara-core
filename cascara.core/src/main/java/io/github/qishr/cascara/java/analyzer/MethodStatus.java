package io.github.qishr.cascara.java.analyzer;

public class MethodStatus {
    private String name = null;
    private String chapter = null;
    private boolean implemented = false;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getChapter() {
        return chapter;
    }
    public void setChapter(String chapter) {
        this.chapter = chapter;
    }
    public boolean isImplemented() {
        return implemented;
    }
    public void setImplemented(boolean implemented) {
        this.implemented = implemented;
    }


}
