package laboratory.core.gui.dialog;

import laboratory.common.loader.VisualizatorLoader;
import laboratory.core.InterfaceConfig;
import laboratory.util.gui.config.OkCancelPanel;

import javax.swing.*;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VisualizatorsChooser extends JDialog {

    private int selectedIndex;

    public VisualizatorsChooser(final List<VisualizatorLoader> visualizators, final Object[] attributes,
                                JDialog owner) {
        super(owner, InterfaceConfig.VISUALIZATORS_CHOOSER_PROPERTIES.getString("title"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ButtonGroup group = new ButtonGroup();
        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        int i = 0;
        for (VisualizatorLoader v: visualizators) {
            JRadioButton button = new JRadioButton(v.getName());
            final int j = i;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedIndex = j;
                }
            });
            group.add(button);
            radioPanel.add(button);
            i++;            
        }
        getContentPane().add(radioPanel, BorderLayout.CENTER);
        getContentPane().add(new OkCancelPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final JFrame visualizator = visualizators.get(selectedIndex).loadVisualizator(attributes);
                visualizator.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
                visualizator.setVisible(true);
                dispose();
            }
        }, this), BorderLayout.SOUTH);
    }

}
