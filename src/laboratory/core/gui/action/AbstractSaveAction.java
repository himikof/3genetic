package laboratory.core.gui.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import laboratory.common.genetic.Individual;
import laboratory.core.InterfaceConfig;
import laboratory.core.gui.graphic.GraphicInfo;
import laboratory.core.gui.graphic.GraphicManager;
import laboratory.util.Parser;

public abstract class AbstractSaveAction<I extends Individual> extends AbstractAction {

    private final JFrame frame;
    private final int index;

    public AbstractSaveAction(String name, String shortDescription, int index, JFrame frame) {
        this.index = index;
        this.frame = frame;
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, shortDescription);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        GraphicManager<I> manager = GraphicManager.getInstance();
        try {
            if (!manager.resultDirExists(index)) {
                Parser p = InterfaceConfig.MENU_PROPERTIES;
                String name = JOptionPane.showInputDialog(frame, p.getString("save-message-input-result-dir"));
                while (!manager.addResultDir(index, name)) {
                    name = JOptionPane.showInputDialog(frame, p.getString("save-message-warning-result-dir"));
                }
                GraphicInfo<I> info = manager.getInfo(index);
                if (info != null) {
                    File dir = manager.getResultDir(index);
                    info.task.getConfigWriter(new File(dir, p.getString("save-task-file"))).close();
                    info.individual.getConfigWriter(new File(dir, p.getString("save-individual-file"))).close();
                    info.algorithm.getConfigWriter(new File(dir, p.getString("save-algorithm-file"))).close();
                }
            }
            saveInformation(manager.getResultDir(index));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void saveInformation(File dir) throws IOException ;

}