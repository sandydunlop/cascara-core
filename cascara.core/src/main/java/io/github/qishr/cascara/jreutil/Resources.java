package io.github.qishr.cascara.jreutil;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class Resources {
    private static String buildPath = null;

    public static String readFile(Path path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(path);
        return new String(encoded, encoding);
    }

    public static String getTextResource(String name) throws IOException, URISyntaxException {
        URL resourceUrl = getResource(name);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Null: That is all, I cbf right now");
        }
        Path path = Path.of(resourceUrl.toURI());
        return readFile(path, Charset.defaultCharset());
    }

    public static URL getResource(String name) {
        ClassLoader classLoader = Resources.class.getClassLoader();
        URL url = classLoader.getResource(name);
        if (url == null) {
            URI uri = URI.create(getBuildPath());
            String uriPath = uri.getPath();
            Path path = Path.of(uriPath, "resources", "main", name);
            try {
                url = path.toUri().toURL();
            } catch (MalformedURLException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return url;
    }

    public static String getBuildPath() {
        if (buildPath == null) {
            URL url = Resources.class.getResource("");
            if (url != null) {
                String location = url.toExternalForm();
                // System.out.println("location = " + location);
                int remove = Resources.class.getPackageName().length();
                if (location.endsWith("/") || location.endsWith("\\")) {
                    remove++;
                }
                String packageRoot = location.substring(0,
                        location.length() - remove);
                // System.out.println("base = " + packageRoot);
                int classes = packageRoot.lastIndexOf("/classes/java/main");
                if (classes != -1) {
                    buildPath = packageRoot.substring(0, classes);
                    // System.out.println("BUILD: " + buildDirPath);
                }
            }
        }
        return buildPath;
    }
}
