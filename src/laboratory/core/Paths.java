package laboratory.core;

import java.io.File;

public class Paths {

    public static File RESOURCES = new File("conf");
    public static File DOCS = new File(RESOURCES, "docs");

    private static File PLUGINS = new File("plugins");

    public static File TASKS = new File(PLUGINS, "tasks");
    public static File INDIVIDUALS = new File(PLUGINS, "individuals");
    public static File ALGORITHMS = new File(PLUGINS, "algorithms");
    public static File VISUALIZATORS = new File(PLUGINS, "visualizators");
    public static File FUNCTORS = new File(PLUGINS, "functors");
    public static File RESULTS = new File("results");
    
}