package laboratory.plugin.algorithm.custom;

import laboratory.plugin.algorithm.custom.gui.ConfigDialog;
import laboratory.util.loader.AbstractAlgorithmLoader;
import laboratory.util.loader.JarReader;
import laboratory.util.gui.config.Util;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.Algorithm;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Selection;
import laboratory.common.genetic.operator.Fitness;

import javax.swing.*;
import java.util.jar.JarFile;
import java.util.List;
import java.io.File;
import java.awt.*;

public class CustomGALoader<I extends Individual> extends AbstractAlgorithmLoader<I> {
    
    private Config prototype = new Config();
    
    public CustomGALoader(JarFile file, File dir) {
        super(file, dir);
        prototype.setJar(file);
    }

    @Override
    public String getMessage() {
        if (getSelections().isEmpty()) {
            return "Please, select only one selection strategy!";
        } else {
            return "OK";
        }
    }

    public Algorithm<I> loadAlgorithm(List<IndividualFactory<I>> individualFactories, List<Crossover<I>> crossovers,
                                      List<Mutation<I>> mutations, List<Selection<I>> sel, List<Fitness<I>> functions) {
        Config config = prototype.clone();
        //ToDo: This section ought to be fixed. (Selection operator)
        return new CustomGA<I>(config, 
                individualFactories.get(0), mutations.get(0), 
                crossovers.get(0), getSelections().get(0), functions.get(0));
    }

    @Override
    public JDialog getConfigDialog(JFrame owner) {
        //final JDialog dialog = getSelectionChooser(owner, "Custom Genetic Algortihm. Choose selection straregies");
        final JDialog dialog = new ConfigDialog(owner, 
                JarReader.getProperties(getJar(), "frame.config.properties"),
                prototype);
        Util.setSize(dialog, new Dimension(300, 250));
        //Util.showModal(dialog);
        return dialog;
    }

}
