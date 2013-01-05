package laboratory.plugin.algorithm.selection.roulette;

import laboratory.util.loader.OperatorLoader;
import laboratory.common.genetic.operator.Selection;
import laboratory.common.genetic.Individual;

import javax.swing.*;
import java.util.jar.JarFile;

public class RouletteLoader<I extends Individual> implements OperatorLoader<Selection<I>> {
    @Override
    public Selection<I> loadOperator() {
        return new RouletteSelection<I>();
    }

    @Override
    public JDialog getConfigDialog(JDialog owner) {
        return null;
    }

    @Override
    public String getName() {
        return "Roulette";
    }

    public RouletteLoader(JarFile jar) {}
}
