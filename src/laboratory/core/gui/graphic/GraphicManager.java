package laboratory.core.gui.graphic;

import laboratory.core.Paths;

import java.util.ArrayList;
import java.io.File;

public class GraphicManager {

    private final ArrayList<GraphicInfo> graphics = new ArrayList<GraphicInfo>();
    private final ArrayList<File> directories = new ArrayList<File>();

    public int addGraphic(GraphicInfo info) {
        graphics.add(info);
        directories.add(null);
        return graphics.size() - 1;
    }

    public GraphicInfo getInfo(int i) {
        return graphics.get(i);
    }

    public boolean resultDirExists(int i) {
        return directories.get(i) != null;
    }

    public boolean addResultDir(int i, String name) {
        if (!Paths.RESULTS.exists()) {
            Paths.RESULTS.mkdir();
        }
        File res = new File(Paths.RESULTS, name);
        if (res.exists()) {
            return false;
        } else {
            res.mkdir();
            directories.set(i, res);
            return true;
        }
    }

    public File getResultDir(int i) {
        if (!resultDirExists(i)) {
            throw new RuntimeException("There is no result dir for graphic" + i);
        }
        return directories.get(i);
    }

    private GraphicManager() {
    }

    private static GraphicManager ourInstance = new GraphicManager();

    public static GraphicManager getInstance() {
        return ourInstance;
    }

}