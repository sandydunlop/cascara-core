package io.github.qishr.cascara.jreutil;

import java.io.File;
import java.io.IOException;
import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.jar.JarFile;

import io.github.qishr.cascara.java.model.Dependency;
import io.github.qishr.cascara.java.model.JlsName;
import io.github.qishr.cascara.java.model.MethodNode;
import io.github.qishr.cascara.java.model.ModuleNode;
import io.github.qishr.cascara.java.model.NameUtil;
import io.github.qishr.cascara.java.modeling.StandardModeler;

public class JreUtil {

    protected JreUtil() {
        // Nothing to see here
    }

    public static Jar loadJarFile(String fileName) throws IOException {
        File path = Path.of(fileName).toFile();
        return Jar.load(path);
    }

    public static Module loadUnnamedModule() {
        ClassLoader cl = JreUtil.class.getClassLoader().getSystemClassLoader();
        return cl.getUnnamedModule();
    }

    public static String getModulePath() {
        return System.getProperty("jdk.module.path");
    }

    public static Module loadModule(String moduleName) {
        for (Module module : ModuleLayer.boot().modules()) {
            if (module.getName().equals(moduleName)) {
                return module;
            }
        }
        return null;
    }

    public static List<Class<?>> loadClassesFromPackage(String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        // Dependency dependency = new Dependency(packageName);
        // StandardModeler standardModeler = new StandardModeler();
        try {
            String packageDir = packageName.replace('.', '/');
            Path packagePath = FileSystems.getFileSystem(URI.create("jrt:/"))
                                     .getPath("/modules/java.base/" + packageDir);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(packagePath)) {
                for (Path path : stream) {
                    String filename = path.getFileName().toString();
                    if (filename.endsWith(".class")) {
                        String binaryClassName = packageName + "." + filename.substring(0, filename.length() - 6);
                        Class<?> jreClass = JreUtil.loadClass(binaryClassName);
                        // TODO: classes inside classes need to be added to the containing class
                        classes.add(jreClass);
                    }
                }
            }
        } catch (IOException _) {
            // Do nothing
        }
        return classes;
    }

    public static Class<?> loadClass(String qualifiedName) {
        Optional<Class<?>> candidate = tryLoadClass(qualifiedName);
        if (candidate.isPresent()) {
            return candidate.get();
        }
        JlsName name = NameUtil.createName(qualifiedName);
        for (int split = name.componentCount() - 1; split>0; split--) {
            name.setPackageComponentCount(split);
            qualifiedName = name.fullyQualifiedBinaryName();
            candidate = tryLoadClass(qualifiedName);
            if (candidate.isPresent()) {
                return candidate.get();
            }
        }
        return null;
    }

    private static Optional<Class<?>> tryLoadClass(String qualifiedName) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try {
            Class<?> loadedClass = classLoader.loadClass(qualifiedName);
            return Optional.of(loadedClass);
        } catch (ClassNotFoundException e) {
            // Ignore it
        }
        return Optional.empty();
    }

    public static MethodNode[] getMethods(Class<?> jreClass) {
        StandardModeler modeller = new StandardModeler();
        Method[] methods = jreClass.getMethods();
        MethodNode[] methodNodes = new MethodNode[methods.length];
        for (int i=0; i<methods.length; i++) {
            methodNodes[i] = modeller.modelMethod(methods[i]);
        }
        return methodNodes;
    }
}
