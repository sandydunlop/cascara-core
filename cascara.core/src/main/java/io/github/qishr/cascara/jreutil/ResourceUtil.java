package io.github.qishr.cascara.jreutil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;

/**
 * A utility class to handle resource loading in a resilient way.
 * It attempts to load resources using the standard ClassLoader, but falls back
 * to a more direct file system lookup for development environments (like VS Code's debugger).
 */
public final class ResourceUtil {

    private ResourceUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Attempts to find a resource and returns a URL to it.
     * It first tries to find the resource using the standard ClassLoader.
     * If that fails (which can happen in certain IDE/debugger setups), it attempts
     * to locate the resource on the file system by traversing up from the
     * current package path to the root of the project's source folders.
     *
     * @param resourcePath The path to the resource, e.g., "views/main.fxml"
     * @return The URL to the resource, or null if not found.
     */
    public static URL getResource(String resourcePath) {
        // 1. First, try the standard ClassLoader approach. This is the correct
        //    way for packaged applications and often works in IDEs.
        URL url = ResourceUtil.class.getResource("/" + resourcePath);

        // 2. If the ClassLoader fails, fall back to the file system lookup.
        //    This is designed to work in development environments like VS Code,
        //    where the classes and resources are in separate directories.
        if (url == null) {
            try {
                String packagePath = ResourceUtil.class.getPackage().getName().replace('.', File.separatorChar);
                String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

                File resourceFile = findResourceInProject(currentDir, packagePath, resourcePath);
                if (resourceFile != null) {
                    url = resourceFile.toURI().toURL();
                }
            } catch (Exception e) {
                // Log the exception, but continue to return null if the fallback fails
                System.err.println("Fallback resource lookup failed (URL): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return url;
    }

    // --- NEW METHOD ADDED ---

    /**
     * Attempts to find a resource and returns an InputStream for reading.
     * It uses the same resilient lookup logic as getResource().
     *
     * @param resourcePath The path to the resource, e.g., "views/main.fxml"
     * @return An InputStream for the resource, or null if not found.
     */
    public static InputStream getResourceAsStream(String resourcePath) {
        // 1. First, try the standard ClassLoader approach.
        InputStream is = ResourceUtil.class.getResourceAsStream("/" + resourcePath);

        // 2. If the ClassLoader fails, fall back to the file system lookup.
        if (is == null) {
            try {
                String packagePath = ResourceUtil.class.getPackage().getName().replace('.', File.separatorChar);
                String currentDir = Paths.get(".").toAbsolutePath().normalize().toString();

                File resourceFile = findResourceInProject(currentDir, packagePath, resourcePath);
                if (resourceFile != null) {
                    // Create an InputStream from the found File
                    is = new FileInputStream(resourceFile);
                }
            } catch (Exception e) {
                // Log the exception, but continue to return null if the fallback fails
                System.err.println("Fallback resource lookup failed (Stream): " + e.getMessage());
                e.printStackTrace();
            }
        }
        return is;
    }

    // ------------------------

    /**
     * Recursively searches for the resource file within the project structure.
     * It works its way up the directory tree until it finds the 'classes/java/main' or 'src'
     * directory and then looks for the resource from there.
     *
     * @param currentPath The current directory to start searching from.
     * @param packagePath The package path of the current class.
     * @param resourcePath The path to the resource relative to the project root.
     * @return The File object if the resource is found, or null otherwise.
     */
    private static File findResourceInProject(String currentPath, String packagePath, String resourcePath) {
        // Note: The original logic in findResourceInProject is a recursive search
        // up the directory tree, which can be inefficient. For a simpler project setup,
        // a search that checks common parent directories first is usually sufficient.
        // However, keeping the current recursive logic for consistency.

        File dir = new File(currentPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Check if we've found the root of the compiled classes
                    if (file.getName().equals("classes")) {
                        File mainClassesDir = new File(file, "java" + File.separatorChar + "main");
                        if (mainClassesDir.exists()) {
                            File resourceCandidate = new File(mainClassesDir, resourcePath);
                            if (resourceCandidate.exists() && resourceCandidate.isFile()) {
                                return resourceCandidate;
                            }
                        }
                    }

                    // Check for standard Maven/Gradle source directory structure
                    if (file.getName().equals("src")) {
                        File resourceCandidate = new File(file, "main" + File.separatorChar + "resources" + File.separatorChar + resourcePath);
                        if (resourceCandidate.exists() && resourceCandidate.isFile()) {
                            return resourceCandidate;
                        }
                    }

                    // Recursively search in subdirectories
                    File result = findResourceInProject(file.getAbsolutePath(), packagePath, resourcePath);
                    if (result != null) {
                        return result;
                    }
                }
            }
        }
        return null;
    }
}
