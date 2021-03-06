package laboratory.plugin.individual.robot.moore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarFile;

import javax.swing.JDialog;
import javax.swing.JFrame;

import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Fitness;
import laboratory.plugin.individual.robot.moore.factory.MooreAutomatonFactory;
import laboratory.plugin.individual.robot.moore.gui.ConfigDialog;
import laboratory.util.StandardFitness;
import laboratory.util.loader.AbstractIndividualLoader;
import laboratory.util.loader.JarReader;

public class MooreIndividualFactoryLoader extends AbstractIndividualLoader<MooreAutomaton> {

    public MooreIndividualFactoryLoader(JarFile jar, File dir) {
        super(jar, dir);
        Config.getInstance().setEx(Integer.parseInt(getProperty("external")));
        Config.getInstance().setIn(Integer.parseInt(getProperty("internal")));
    }

    @Override
    public List<IndividualFactory<MooreAutomaton>> loadFactories() {
        return Collections.singletonList((IndividualFactory<MooreAutomaton>) new MooreAutomatonFactory(Config.getInstance().getEx()));
    }

    @Override
    public List<Fitness<MooreAutomaton>> loadFunctions() {
        List<Fitness<MooreAutomaton>> res = new ArrayList<Fitness<MooreAutomaton>>();
        res.add(new StandardFitness<MooreAutomaton>());
        return res;
    }

    @Override
    public JDialog getConfigDialog(JFrame owner) {
        return new ConfigDialog(owner, JarReader.getProperties(getJar(), "frame.config.properties"), this);
    }

}
