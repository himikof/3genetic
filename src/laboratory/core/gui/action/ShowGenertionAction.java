package laboratory.core.gui.action;

import laboratory.common.genetic.Individual;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.core.AlgorithmRunner;
import laboratory.core.InterfaceConfig;

import javax.swing.*;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;

public class ShowGenertionAction<I extends Individual> extends ShowIndividualsAction<I> {

    public ShowGenertionAction(AlgorithmRunner<I> runner, List<VisualizatorLoader> visualizators, JFrame owner) {
        super(runner, InterfaceConfig.MENU_PROPERTIES.getString("show.generation.name"),
                InterfaceConfig.MENU_PROPERTIES.getString("show.generation.description"), visualizators, owner);

    }

    @Override
    protected List<Integer> getIndeces() {
        return Collections.nCopies(runner.getCurrentFitnessValues().size(), runner.getStandardFitnessValues().get(0).size());
    }

    @Override
    protected List<Double> getFitnessValues() {        
        return runner.getCurrentFitnessValues();
    }

    @Override
    protected List<I> getIndividuals() {
        return runner.getGeneration();
    }
}
