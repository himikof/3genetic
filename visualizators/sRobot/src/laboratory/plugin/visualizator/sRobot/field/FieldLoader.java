package laboratory.plugin.visualizator.sRobot.field;

import laboratory.util.loader.AbstractVisualizatorLoader;
import laboratory.plugin.task.robot.individual.Automaton;

import javax.swing.*;
import java.util.jar.JarFile;

/**
 * @author Dmitry Sokolov
 */
public class FieldLoader extends AbstractVisualizatorLoader {


    public FieldLoader(JarFile file) {
        super(file);
    }

    public JFrame loadVisualizator(Object[] args) {
        return new Field(getProperty("title"), (Automaton) args[0], this);
    }

    @Override
    public String getName() {
        return "Chebotareva's visualizator";
    }
}
