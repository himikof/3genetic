package laboratory.util.loader;

import java.util.jar.JarFile;
import java.util.Properties;
import java.io.IOException;

/**
 * @author Dmirtry Sokolov
 */
public class JarReader {

    private final JarFile jar;
    private final Properties p;

    public JarReader(JarFile jar, String entry) {
        this.jar = jar;
        p = getProperties(jar, entry);
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }

    public JarFile getJar() {
        return jar;
    }

    public static Properties getProperties(JarFile jar, String name) {
        Properties p = new Properties();
        try {
            p.load(jar.getInputStream(jar.getEntry(name)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return p;
    }
}
