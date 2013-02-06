package laboratory.plugin.individual.robot.mealy.gui;

import laboratory.util.gui.config.TextFieldsPanel;
import laboratory.util.gui.config.OkCancelPanel;
import laboratory.util.gui.config.Util;
import laboratory.util.loader.AbstractIndividualLoader;
import laboratory.plugin.individual.robot.mealy.Config;
import laboratory.plugin.individual.robot.mealy.MealyAutomaton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Properties;

/**
 * @author Dmitry Sokolov.
 */
public class ConfigDialog extends JDialog {

    public ConfigDialog(JFrame owner, final Properties p, final AbstractIndividualLoader<MealyAutomaton> l) {
        super(owner, p.getProperty("title"));

        final TextFieldsPanel fieldsPanel = new TextFieldsPanel(new String[]{
                p.getProperty("label.external"), p.getProperty("label.internal")},
                new String[]{p.getProperty("external"), p.getProperty("internal")},
                new String[]{Integer.toString(Config.getInstance().getEx()),
                        Integer.toString(Config.getInstance().getIn())},
                new int[]{3, 3}, 2);

        getContentPane().add(fieldsPanel, BorderLayout.CENTER);

        getContentPane().add(new OkCancelPanel(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] value = fieldsPanel.getValue();
                Config.getInstance().setEx(Integer.parseInt(value[0]));
                Config.getInstance().setIn(Integer.parseInt(value[1]));
                showOperatorChooserDialog(p, l);
                dispose();
            }
        }, this), BorderLayout.SOUTH);
        Util.setSize(this, new Dimension(Integer.parseInt(p.getProperty("width")), Integer.parseInt(p.getProperty("height"))));
    }

    private void showOperatorChooserDialog(Properties p, AbstractIndividualLoader<MealyAutomaton> l) {
        final JDialog dialog = l.getOperatorChooser(this, p.getProperty("title.operators"));
        Util.setSize(dialog, new Dimension(Integer.parseInt(p.getProperty("operators.width")), Integer.parseInt(p.getProperty("operators.height"))));
        Util.showModal(dialog);
    }
}
