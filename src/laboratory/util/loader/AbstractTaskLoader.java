package laboratory.util.loader;

import laboratory.common.loader.TaskLoader;
import laboratory.util.Description;

import java.util.jar.JarFile;
import java.io.*;

/**
 * @author Dmitry Sokolov
 */
public abstract class AbstractTaskLoader extends JarReader implements TaskLoader {

    public AbstractTaskLoader(JarFile jar) {
        super(jar, "task.properties");
    }

    public String getName() {
        return getProperty("name");
    }

    public String getDescription() {
        try {
            return Description.getDecription(getJar().getInputStream(getJar().getEntry(getProperty("description.file"))));
        } catch (IOException e) {
            e.printStackTrace();
            return "Can't read description";
        }
    }

    @Override
    public Writer getConfigWriter(File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(getName());
        return writer;
    }
}
