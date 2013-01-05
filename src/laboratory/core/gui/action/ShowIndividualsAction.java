package laboratory.core.gui.action;

import laboratory.common.genetic.Individual;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.core.AlgorithmRunner;
import laboratory.core.gui.dialog.IndividualsChooser;
import laboratory.util.gui.config.Util;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

public abstract class ShowIndividualsAction<I extends Individual> extends AbstractAction {

    protected final AlgorithmRunner<I> runner;
    private final List<VisualizatorLoader> visualizators;
    private final JFrame owner;

    public ShowIndividualsAction(AlgorithmRunner<I> runner, String name, String shortDescription,
                                 List<VisualizatorLoader> visualizators, JFrame owner) {
        putValue(NAME, name);
        putValue(SHORT_DESCRIPTION, shortDescription);
        this.runner = runner;
        this.visualizators = visualizators;
        this.owner = owner;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        final JDialog dialog = new IndividualsChooser<I>(getIndividuals(), getFitnessValues(), getIndeces(), visualizators, owner);
        dialog.setLocationRelativeTo(owner);
        Util.showModal(dialog);
    }

    protected abstract List<Integer> getIndeces();

    protected abstract List<Double> getFitnessValues();

    protected abstract List<I> getIndividuals();

}