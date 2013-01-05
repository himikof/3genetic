package laboratory.core.gui.action;

import laboratory.common.genetic.Individual;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.core.AlgorithmRunner;
import laboratory.core.InterfaceConfig;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ShowBestIndividualsAction<I extends Individual> extends ShowIndividualsAction<I> {

    public ShowBestIndividualsAction(AlgorithmRunner<I> runner, List<VisualizatorLoader> visualizators, JFrame owner) {
        super(runner, InterfaceConfig.MENU_PROPERTIES.getString("show.best.individuals.name"),
                InterfaceConfig.MENU_PROPERTIES.getString("show.best.individuals.description"), visualizators, owner);
    }

    @Override
    protected List<Integer> getIndeces() {
        int n = runner.getBestIndividuals().size();
        List<Integer> indeces = new ArrayList<Integer>(n);
        for (int i = 0; i < n; i++) {
            indeces.add(i);
        }
        return indeces;
    }

    @Override
    protected List<Double> getFitnessValues() {
        return runner.getBestFitnessValues();
    }

    @Override
    protected List<I> getIndividuals() {
        return runner.getBestIndividuals();
    }
}
