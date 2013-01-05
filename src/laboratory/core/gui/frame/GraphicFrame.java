package laboratory.core.gui.frame;

import laboratory.core.AlgorithmRunner;
import laboratory.core.InterfaceConfig;
import laboratory.core.PluginCollection;
import laboratory.core.gui.panel.GraphicPanel;
import laboratory.core.gui.action.ShowGenertionAction;
import laboratory.core.gui.action.ShowBestIndividualsAction;
import laboratory.core.gui.action.SaveStatisticsAction;
import laboratory.core.gui.action.SaveGraphicsAction;
import laboratory.core.gui.graphic.GraphicManager;
import laboratory.core.gui.graphic.GraphicInfo;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.common.genetic.Individual;
import laboratory.util.Parser;

import javax.swing.*;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class GraphicFrame<I extends Individual> extends JFrame {

    private static enum State {
        STOPPED, PLAYED, PAUSED
    }

    private State state = State.STOPPED;

    public GraphicFrame(String title, int index, List<VisualizatorLoader> visualizators) {
        super(title);
        final AlgorithmRunner<I> runner = GraphicManager.getInstance().getInfo(index).runner;
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeFrame(runner);
            }
        });

        final Parser properties = InterfaceConfig.GRAPHIC_FRAME_PROPERTIES;

        final JButton start = new JButton(properties.getString("button-start"));
        final JButton pause = new JButton(properties.getString("button-pause"));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(start, BorderLayout.WEST);
        buttonPanel.add(pause, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        final GraphicPanel standardFitnessGraphic = new GraphicPanel(runner.getStandardFitnessValues(),
                PluginCollection.getInstance().getFunctorNames());
        getContentPane().add(standardFitnessGraphic, BorderLayout.CENTER);

        final Timer timer = new Timer(properties.getInt("timer.delay"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                standardFitnessGraphic.updateGraphic();
            }
        });

        final JMenu show = new JMenu(InterfaceConfig.MENU_PROPERTIES.getString("show"));
        show.setEnabled(false);
        show.add(new JMenuItem(new ShowGenertionAction<I>(runner, visualizators, this)));
        show.add(new JMenuItem(new ShowBestIndividualsAction<I>(runner, visualizators, this)));

        final JMenu save = new JMenu(InterfaceConfig.MENU_PROPERTIES.getString("save"));
        save.setEnabled(false);
        save.add(new JMenuItem(new SaveStatisticsAction<I>(index, this, PluginCollection.getInstance().getFunctorNames())));
        save.add(new JMenuItem(new SaveGraphicsAction<I>(index, this, standardFitnessGraphic)));

        final JMenuBar menubar = new JMenuBar();
        menubar.add(show);
        menubar.add(save);
        this.setJMenuBar(menubar);

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (state) {
                    case STOPPED:
                        state = State.PLAYED;
                        start.setText(properties.getString("button-stop"));
                        pause.setEnabled(true);
                        timer.restart();
                        runner.restart();
                        standardFitnessGraphic.updateGraphic();
                        show.setEnabled(false);
                        save.setEnabled(false);
                        break;
                    case PLAYED:
                        state = State.STOPPED;
                        start.setText(properties.getString("button-start"));
                        pause.setEnabled(false);
                        timer.stop();
                        runner.stop();
                        show.setEnabled(true);
                        save.setEnabled(true);
                        break;
                    case PAUSED:
                        state = State.STOPPED;
                        start.setText(properties.getString("button-start"));
                        pause.setEnabled(false);
                        pause.setText(properties.getString("button-pause"));
                        timer.stop();
                        runner.stop();
                        break;
                }
            }
        });
        pause.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (state) {
                    case PLAYED:
                        state = State.PAUSED;
                        pause.setText(properties.getString("button-resume"));
                        timer.stop();
                        runner.pause();
                        show.setEnabled(true);
                        save.setEnabled(true);
                        break;
                    case PAUSED:
                        state = State.PLAYED;
                        pause.setText(properties.getString("button-pause"));
                        timer.start();
                        runner.resume();
                        show.setEnabled(false);
                        save.setEnabled(false);
                        break;
                }
            }
        });

        pack();
    }

    private void closeFrame(AlgorithmRunner<I> runner) {
        runner.kill();
        this.dispose();
    }
}
