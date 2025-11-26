package io.github.qishr.cascara.jreutil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.module.ModuleDescriptor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import io.github.qishr.cascara.java.ast.semantic.StringConstant;
import io.github.qishr.cascara.java.model.SemanticModel;

public class Jar {
    Set<String> classNames = new java.util.HashSet<>();
    File file = null;
    String moduleName = null;
    JarFile jarFile = null;

    public Set<String> getClassNames() {
        return classNames;
    }

    public File getFile() {
        return file;
    }

    public String getModuleName() {
        return moduleName;
    }

    private Jar() {

    }

    // static Jar load(JarFile jarFile) throws IOException{
    static Jar load(File file) throws IOException{
        JarFile jarFile = null;
        Jar jar = new Jar();
        try {
            jarFile = new JarFile(file.toString());
        } catch (IOException e) {
            // ctx.reportError("Error reading JAR file: " + file);
            return null;
        }
        jar.file = file;
        jar.jarFile = jarFile;
        // Iterate over all entries in the JAR
        for (Enumeration<JarEntry> entries = jarFile.entries(); entries.hasMoreElements();) {
            JarEntry entry = entries.nextElement();
            String entryName = entry.getName();

            // Process module-info.class
            if (entryName.equals("module-info.class")) {
                InputStream is = jarFile.getInputStream(entry);
                ModuleDescriptor descriptor = ModuleDescriptor.read(is);
                jar.moduleName = descriptor.name();
            }

            // Process other class files
            else if (entryName.endsWith(".class")) {
                if (checkClassFile(jarFile, entry)) {
                }

                String className = entryName
                    .replace("/", ".")
                    .replace("\\", ".")
                    .substring(0, entryName.length() - 6); // Remove DOT_CLASS
                jar.classNames.add(className);
            }
        }
        return jar;
    }


    public void inspectJarFile(){
        URLClassLoader child;
        for (String className : classNames){
            Exception exception = null;
            Class<?> classToLoad = null;
            try {
                System.out.println("ENTRY: " + className);
                child = new URLClassLoader(
                        new URL[] {file.toURI().toURL()},
                        this.getClass().getClassLoader()
                );
                classToLoad = Class.forName(className, true, child);
            } catch (MalformedURLException e) {
                exception = e;
                System.out.println("Error opening JAR file: " + e.getMessage());
                // e.printStackTrace();
            }catch (ClassNotFoundException e) {
                exception = e;
                System.out.println("Error loading class from JAR file: " + e.getMessage());
                // e.printStackTrace();
            } catch (IOException e) {
                exception = e;
                System.err.println(e.getMessage());
                // e.printStackTrace();
            } catch (java.lang.UnsatisfiedLinkError e) {
                exception = new Exception(e.getMessage());
                System.err.println(e.getMessage());
                // e.printStackTrace();
            } catch (java.lang.RuntimeException e) {
                exception = e;
                System.err.println(e.getMessage());
                // e.printStackTrace();
            } catch (java.lang.ExceptionInInitializerError e) {
                // exception = e;
                exception = new Exception(e.getMessage());
                System.err.println(e.getMessage());
                // e.printStackTrace();
            } catch (java.lang.NoClassDefFoundError e) {
                exception = new Exception(e.getMessage());
                System.err.println(e.getMessage());
                // e.printStackTrace();
            } catch (Exception e) {
                exception = e;
                System.err.println(e.getMessage());
            }
            if (exception == null) {
                processClass(className, jarFile.toString(), exception);
            } else {
                if (classToLoad!=null){
                    processClass(className, jarFile.toString(), exception);
                }
            }
        }
    }

    private void processClass(String className, String jarPAth, Exception ex) {
        if (ex == null) {
            System.out.println("CLASS " + className);
        } else {
            System.out.println("CLASS " + className + " FAILED: " + ex.getClass().getSimpleName() +": " + ex.getMessage());
        }
    }

    private static boolean checkClassFile(JarFile jarFile, JarEntry jarEntry) {
        String arch = System.getProperty("os.arch");
        System.out.println("JVM Architecture: " + arch);
        try {
            InputStream is = jarFile.getInputStream(jarEntry);
            System.out.println();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}
