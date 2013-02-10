package laboratory.plugin.algorithm.custom.gui;

import laboratory.plugin.algorithm.custom.Config;
import laboratory.util.gui.config.TextFieldsPanel;
import laboratory.util.gui.config.OkCancelPanel;
import laboratory.util.gui.config.Util;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Properties;

/**
 * @author Nikita Ofitserov.
 */
public class ConfigDialog extends JDialog {

    private static final long serialVersionUID = 1L;

    public ConfigDialog(JFrame owner, final Properties p, final Config config) {
        super(owner, p.getProperty("title"));

        final TextFieldsPanel fieldsPanel = new TextFieldsPanel(
                new String[]{
                        p.getProperty("label.generation"),
                        p.getProperty("label.children"),
                        p.getProperty("label.useCrossover")
                    },
                new String[]{
                        p.getProperty("tooltip.generation"),
                        p.getProperty("tooltip.children"),
                        p.getProperty("tooltip.useCrossover")
                    },
                new String[]{
                        Integer.toString(config.getGenerationSize()),
                        Integer.toString(config.getChildrenCount()),
                        Integer.toString(config.isUsingCrossover() ? 1 : 0)
                    },
                new int[]{3, 3, 3},
                3);

        getContentPane().add(fieldsPanel, BorderLayout.CENTER);

        getContentPane().add(new OkCancelPanel(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String[] value = fieldsPanel.getValue();
                config.setGenerationSize(Integer.parseInt(value[0]));
                config.setChildrenCount(Integer.parseInt(value[1]));
                config.setUsingCrossover(Integer.parseInt(value[2]) != 0);
                dispose();
            }
        }, this), BorderLayout.SOUTH);
        Util.setSize(this, new Dimension(Integer.parseInt(p.getProperty("width")),
                Integer.parseInt(p.getProperty("height"))));
    }
}
