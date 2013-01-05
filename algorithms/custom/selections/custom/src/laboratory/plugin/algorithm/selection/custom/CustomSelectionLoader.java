package laboratory.plugin.algorithm.selection.custom;

import laboratory.util.loader.OperatorLoader;
import laboratory.common.genetic.operator.Selection;
import laboratory.common.genetic.Individual;

import javax.swing.*;
import java.util.jar.JarFile;

public class CustomSelectionLoader<I extends Individual> implements OperatorLoader<Selection<I>> {
    @Override
    public Selection<I> loadOperator() {
        return new CustomSelection<I>();
    }

    @Override
    public JDialog getConfigDialog(JDialog owner) {
        return null;
    }

    @Override
    public String getName() {
        return "Custom selection";
    }

    public CustomSelectionLoader(JarFile jar) {}
}
