module cascara.core {
    requires jdk.compiler;
    requires transitive jdk.javadoc;
    requires transitive java.compiler;

    requires cascara.common;

    exports io.github.qishr.cascara.java.analyzer;
    exports io.github.qishr.cascara.jreutil;
    exports io.github.qishr.cascara.java.model;
    exports io.github.qishr.cascara.java.modeling;
    exports io.github.qishr.cascara.java.parser;

    exports io.github.qishr.cascara.java.ast;
    exports io.github.qishr.cascara.java.ast.arrays;
    exports io.github.qishr.cascara.java.ast.statements;
    exports io.github.qishr.cascara.java.ast.classes;
    exports io.github.qishr.cascara.java.ast.expressions;
    exports io.github.qishr.cascara.java.ast.interfaces;
    exports io.github.qishr.cascara.java.ast.lexical;
    exports io.github.qishr.cascara.java.ast.names;
    exports io.github.qishr.cascara.java.ast.semantic;
    exports io.github.qishr.cascara.java.ast.structures;
    exports io.github.qishr.cascara.java.ast.types;

    opens io.github.qishr.cascara.jreutil;
    opens io.github.qishr.cascara.java.modeling;
    opens io.github.qishr.cascara.java.parser;
    opens io.github.qishr.cascara.java.model;
}
