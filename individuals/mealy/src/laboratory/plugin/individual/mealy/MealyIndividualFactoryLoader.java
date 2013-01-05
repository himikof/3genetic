package laboratory.plugin.individual.mealy;

import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Fitness;
import laboratory.plugin.individual.mealy.factory.MealyAutomatonFactory;
import laboratory.plugin.individual.mealy.gui.ConfigDialog;
import laboratory.util.StandardFitness;
import laboratory.util.loader.AbstractIndividualLoader;
import laboratory.util.loader.JarReader;

import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarFile;

public class MealyIndividualFactoryLoader extends AbstractIndividualLoader<MealyAutomaton> {

    public MealyIndividualFactoryLoader(JarFile jar, File dir) {
        super(jar, dir);
        Config.getInstance().setEx(Integer.parseInt(getProperty("external")));
        Config.getInstance().setIn(Integer.parseInt(getProperty("internal")));
    }

    @Override
    public List<IndividualFactory<MealyAutomaton>> loadFactories() {
        return Arrays.asList((IndividualFactory<MealyAutomaton>) new MealyAutomatonFactory(Config.getInstance().getEx(),
                Config.getInstance().getIn()));
    }

    @Override
    public List<Fitness<MealyAutomaton>> loadFunctions() {
        List<Fitness<MealyAutomaton>> res = new ArrayList<Fitness<MealyAutomaton>>();
        res.add(new StandardFitness<MealyAutomaton>());
        return res;
    }

    @Override
    public JDialog getConfigDialog(JFrame owner) {
        return new ConfigDialog(owner, JarReader.getProperties(getJar(), "frame.config.properties"), this);
    }

}
