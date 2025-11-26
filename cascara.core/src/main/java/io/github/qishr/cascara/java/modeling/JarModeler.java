package io.github.qishr.cascara.java.modeling;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.model.SemanticModel;
import io.github.qishr.cascara.jreutil.Jar;

public class JarModeler {
    Reporter reporter;
    SemanticModel semanticModel;

    public JarModeler(Reporter reporter, SemanticModel semanticModel) {
        this.reporter = reporter;
        this.semanticModel = semanticModel;
    }

    public void model(Jar jar) {
        //TODO:
    }
}
