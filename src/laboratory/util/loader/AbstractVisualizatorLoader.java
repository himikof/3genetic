package laboratory.util.loader;

import laboratory.common.loader.VisualizatorLoader;

import java.util.*;
import java.util.jar.JarFile;

public abstract class AbstractVisualizatorLoader extends JarReader implements VisualizatorLoader {

    private Set<String> names;

    public AbstractVisualizatorLoader(JarFile jar) {
        super(jar, "visualizator.properties");
        names = new HashSet<String>();
        StringTokenizer st = new StringTokenizer(getProperty("individual.names"), "|");
        while (st.hasMoreTokens()) {
            names.add(st.nextToken());
        }
    }

    public Set<String> getIndividualNames() {
        return names;
    }
}
