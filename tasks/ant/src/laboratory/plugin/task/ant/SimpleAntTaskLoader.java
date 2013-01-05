package laboratory.plugin.task.ant;


import laboratory.util.loader.AbstractTaskLoader;

import javax.swing.*;
import java.util.jar.JarFile;

public class SimpleAntTaskLoader extends AbstractTaskLoader {

    public SimpleAntTaskLoader(JarFile jr) {
        super(jr);
        setField(jr);
    }

    public JDialog getConfigDialog(JFrame owner) {
        return null;
    }

    private static void setField(JarFile jar) {
        SimpleAnt.setField(SimpleAnt.readField(jar));
    }
}
