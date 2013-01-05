package laboratory.plugin.task.robot;


import laboratory.util.loader.AbstractTaskLoader;

import javax.swing.*;
import java.util.jar.JarFile;

public class SimpleRobotTaskLoader extends AbstractTaskLoader {

    public SimpleRobotTaskLoader(JarFile jr) {
        super(jr);
        setField(jr);
    }

    public JDialog getConfigDialog(JFrame owner) {
        return null;
    }

    private static void setField(JarFile jar) {
        SimpleRobot.readField(jar);
    }
}
