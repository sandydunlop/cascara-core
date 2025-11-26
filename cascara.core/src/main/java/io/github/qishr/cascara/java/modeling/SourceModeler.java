package io.github.qishr.cascara.java.modeling;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.qishr.cascara.common.diagnostic.Reporter;
import io.github.qishr.cascara.java.analyzer.MethodStatus;
import io.github.qishr.cascara.java.ast.structures.CompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ModularCompilationUnit;
import io.github.qishr.cascara.java.ast.structures.ModuleDeclaration;
import io.github.qishr.cascara.java.model.ModuleNode;
import io.github.qishr.cascara.java.model.PackageNode;
import io.github.qishr.cascara.java.model.PackageReference;
import io.github.qishr.cascara.java.model.SemanticModel;
import io.github.qishr.cascara.java.parser.Parser;
import io.github.qishr.cascara.java.parser.SyntaxException;

public class SourceModeler {
    private Reporter reporter;
    private SemanticModel model;
    private SemanticModeler semanticModeler;

    public SourceModeler(Reporter reporter, SemanticModel model) {
        this.reporter = reporter;
        this.model = model;
        semanticModeler = new SemanticModeler();
    }

    public SemanticModel getModel() {
        return model;
    }

    public boolean modelSourceCode(String sourceCode) {
        createModelIfNeeded();
        try{
            Parser parser = new Parser(reporter);
            CompilationUnit cu = parser.parse(sourceCode);
            semanticModeler.model(cu, null, model);
            return true;
        } catch (Exception e) {
            reporter.reportError(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean modelSourceFile(File file) {
        createModelIfNeeded();
        try{
            reporter.reportDebug("Parsing %s", file.getAbsolutePath());
            String sourceCode = readFile(file, Charset.defaultCharset());
            Parser parser = new Parser(reporter);
            parser.setPath(file.getAbsolutePath());
            CompilationUnit cu = parser.parse(sourceCode);
            semanticModeler.model(cu, file.toPath().toString(), model);
            return true;
        } catch (Exception e) {
            reporter.reportError(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean modelSourceDirectory(File directory) {
        boolean completeSuccess = true;
        createModelIfNeeded();
        List<File> javaFiles = new ArrayList<>();
        addDirectoryRecursively(directory, javaFiles);
        List<String> moduleDirectories = findModuleDirectories(javaFiles);
        Map<String,String> moduleNameMap = mapModuleNames(moduleDirectories);

        for (File javaFile : javaFiles) {
            String moduleDirectory = findContainingModuleDirectory(javaFile, moduleDirectories);
            String moduleName = moduleNameMap.get(moduleDirectory);
            semanticModeler.setModuleName(moduleName);
            completeSuccess &= modelSourceFile(javaFile);
        }
        addPackagesToModules();
        return completeSuccess;
    }

    private void addPackagesToModules() {
        for (PackageNode packageNode : model.getPackages()) {
            PackageReference packageReference = new PackageReference(packageNode.getName().fullyQualifiedName());
            ModuleNode moduleNode = model.getModuleNode(packageNode.getModuleName());
            if (moduleNode != null) {
                moduleNode.addPackage(packageReference);
            } else {
                model.getUnnamedModuleNode().addPackage(packageReference);
            }
        }
    }

    private Map<String,String> mapModuleNames(List<String> moduleDirectories) {
        Map<String,String> moduleNameMap = new HashMap<>();
        for (String moduleDirectory : moduleDirectories) {
            Path moduleFilePath = Path.of(moduleDirectory, "module-info.java");
            String moduleName = getModuleName(moduleFilePath.toFile());
            moduleNameMap.put(moduleDirectory, moduleName);
        }
        return moduleNameMap;
    }

    // private String getModuleName(String moduleDirectory) {
    //     Path moduleFilePath = Path.of(moduleDirectory, "module-info.java");
    //     ModuleNode moduleNode = modelModuleFile(moduleFilePath.toFile());
    //     if (moduleNode == null) {
    //         return null;
    //     }
    //     return moduleNode.getName().toString();
    // }

    private String getModuleName(File moduleFile) {
        try {
            String sourceCode = readFile(moduleFile, Charset.defaultCharset());
            Parser parser = new Parser(reporter);
            parser.setPath(moduleFile.getAbsolutePath());
            CompilationUnit cu = parser.parse(sourceCode);
            if (cu == null) {
                return null;
            }
            ModularCompilationUnit modularUnit = cu.getModularCompilationUnit();
            if (modularUnit == null) {
                return null;
            }
            ModuleDeclaration moduleDeclaration = modularUnit.getModuleDeclaration();
            if (moduleDeclaration == null) {
                return null;
            }
            return moduleDeclaration.getNameString();
        } catch (SyntaxException e) {
            reporter.reportError(e.getMessage());
            return null;
        } catch (IOException e) {
            reporter.reportError("Failed to read module file: %s: %s",
                    moduleFile.getAbsolutePath(), e.getMessage());
            return null;
        }
    }

    private String findContainingModuleDirectory(File javaFile, List<String> moduleDirectories) {
        for (String moduleDirectory : moduleDirectories) {
            if (javaFile.getAbsolutePath().startsWith(moduleDirectory)) {
                return moduleDirectory;
            }
        }
        return null;
    }

    private List<String> findModuleDirectories(List<File> javaFiles) {
        final String suffix = "/module-info.java";
        List<String> moduleDirectories = new ArrayList<>();
        for (File file : javaFiles) {
            String pathString = file.getAbsolutePath();
            if (pathString.endsWith(suffix)) {
                moduleDirectories.add(pathString.substring(0,
                        pathString.length() - suffix.length()));
            }
        }
        return moduleDirectories;
    }

    private void createModelIfNeeded() {
        if (model == null) {
            model = new SemanticModel("Source Model");
        }
    }

    private void addDirectoryRecursively(File directory, List<File> javaFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".java")) {
                        javaFiles.add(file);
                    }
                }
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    addDirectoryRecursively(file, javaFiles);
                }
            }
        }
    }

    static String readFile(File file, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(file.toPath());
        return new String(encoded, encoding);
    }
}
