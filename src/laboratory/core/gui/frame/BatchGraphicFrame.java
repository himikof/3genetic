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
import laboratory.common.loader.AlgorithmLoader;
import laboratory.common.loader.IndividualLoader;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Fitness;
import laboratory.common.genetic.operator.Mutation;
import laboratory.util.Parser;

import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.*;

public class BatchGraphicFrame<I extends Individual> extends JFrame {

	private static final long serialVersionUID = 1L;

	private static enum State {
        STOPPED, PLAYED, PAUSED
    }

    private State state = State.STOPPED;
    
    private final List<AlgorithmRunner<I>> runners = new ArrayList<AlgorithmRunner<I>>();
    private final ArrayList<ArrayList<Double>> meanStandardFitnessValues;
    private final List<Map<Integer, Double> > incompleteMeans = new ArrayList<Map<Integer,Double>>();
    private final List<Map<Integer, Integer> > incompleteMeansLeft = new ArrayList<Map<Integer,Integer>>();
    
    private void updateMeanValues() {
    	for (int i = 0; i < meanStandardFitnessValues.size(); ++i) {
    		ArrayList<Double> dest = meanStandardFitnessValues.get(i);
    		Map<Integer, Double> incomplete = incompleteMeans.get(i);
    		Map<Integer, Integer> incompleteLeft = incompleteMeansLeft.get(i);
    		dest.clear();
    		for (AlgorithmRunner<I> runner : runners) {
    			ArrayList<Double> source = runner.getStandardFitnessValues().get(i);
    			synchronized (source) {
    				for (int j = dest.size(); j < source.size(); ++j) {
    					Double mean = incomplete.get(j);
    					Integer left = incompleteLeft.get(j);
    					if (mean == null && left == null) {
    						mean = source.get(j);
    						left = runners.size() - 1;
    					} else {
    						assert left != null;
    						assert mean != null;
    						mean += source.get(j);
    						left -= 1;
    					}
    					if (left == 0) {
    						incomplete.remove(j);
    						incompleteLeft.remove(j);
    						dest.add(mean / runners.size());
    					} else {
    						incomplete.put(j, mean);
    						incompleteLeft.put(j, left);
    					}
    				}
    			}
    		}
    	}
    }
    
    public BatchGraphicFrame(String title, int batchSize, AlgorithmLoader<I> loader, 
    		IndividualLoader<I> individualLoader, List<VisualizatorLoader> visualizators) {
        super(title);
        List<IndividualFactory<I>> factories = individualLoader.loadFactories();
        List<Crossover<I>> crossovers = individualLoader.loadCrossovers();
        List<Mutation<I>> mutations = individualLoader.loadMutations();
        List<Fitness<I>> functions = individualLoader.loadFunctions();
        
        List<String> functorNames = PluginCollection.getInstance().getFunctorNames();
        meanStandardFitnessValues = new ArrayList<ArrayList<Double>>();
        for (int i = 0; i < functorNames.size(); ++i) {
        	meanStandardFitnessValues.add(new ArrayList<Double>());
        	incompleteMeans.add(new HashMap<Integer, Double>());
        	incompleteMeansLeft.add(new HashMap<Integer, Integer>());
        }
        for (int i = 0; i < batchSize; ++i) {
        	runners.add(new AlgorithmRunner<I>(loader, factories, crossovers, mutations, functions));
        }
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                closeFrame();
            }
        });

        final Parser properties = InterfaceConfig.GRAPHIC_FRAME_PROPERTIES;

        final JButton start = new JButton(properties.getString("button-start"));
        final JButton pause = new JButton(properties.getString("button-pause"));

        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(start, BorderLayout.WEST);
        buttonPanel.add(pause, BorderLayout.EAST);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        final GraphicPanel standardFitnessGraphic = new GraphicPanel(meanStandardFitnessValues,
                PluginCollection.getInstance().getFunctorNames());
        getContentPane().add(standardFitnessGraphic, BorderLayout.CENTER);

        final Timer timer = new Timer(properties.getInt("timer.delay"), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	updateMeanValues();            	
                standardFitnessGraphic.updateGraphic();
            }
        });

        final JMenu show = new JMenu(InterfaceConfig.MENU_PROPERTIES.getString("show"));
        show.setEnabled(false);
        /*show.add(new JMenuItem(new ShowGenertionAction<I>(runner, visualizators, this)));
        show.add(new JMenuItem(new ShowBestIndividualsAction<I>(runner, visualizators, this)));*/

        final JMenu save = new JMenu(InterfaceConfig.MENU_PROPERTIES.getString("save"));
        save.setEnabled(false);
        /*save.add(new JMenuItem(new SaveStatisticsAction<I>(index, this, PluginCollection.getInstance().getFunctorNames())));
        save.add(new JMenuItem(new SaveGraphicsAction<I>(index, this, standardFitnessGraphic)));*/

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
                        for (AlgorithmRunner<I> runner : runners) {
                        	runner.restart();
                        }
                        standardFitnessGraphic.updateGraphic();
                        show.setEnabled(false);
                        save.setEnabled(false);
                        break;
                    case PLAYED:
                        state = State.STOPPED;
                        start.setText(properties.getString("button-start"));
                        pause.setEnabled(false);
                        timer.stop();
                        for (AlgorithmRunner<I> runner : runners) {
                        	runner.stop();
                        }
                        show.setEnabled(true);
                        save.setEnabled(true);
                        break;
                    case PAUSED:
                        state = State.STOPPED;
                        start.setText(properties.getString("button-start"));
                        pause.setEnabled(false);
                        pause.setText(properties.getString("button-pause"));
                        timer.stop();
                        for (AlgorithmRunner<I> runner : runners) {
                        	runner.stop();
                        }
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
                        for (AlgorithmRunner<I> runner : runners) {
                        	runner.pause();
                        }
                        show.setEnabled(true);
                        save.setEnabled(true);
                        break;
                    case PAUSED:
                        state = State.PLAYED;
                        pause.setText(properties.getString("button-pause"));
                        timer.start();
                        for (AlgorithmRunner<I> runner : runners) {
                        	runner.resume();
                        }
                        show.setEnabled(false);
                        save.setEnabled(false);
                        break;
                }
            }
        });

        pack();
    }

    private void closeFrame() {
    	for (AlgorithmRunner<I> runner : runners) {
    		runner.kill();
    	}
        this.dispose();
    }
}
