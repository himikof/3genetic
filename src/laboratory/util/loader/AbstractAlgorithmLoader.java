package laboratory.util.loader;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.operator.Selection;
import laboratory.common.loader.AlgorithmLoader;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;

import java.util.jar.JarFile;
import java.util.List;
import java.util.Collections;
import java.util.Arrays;
import java.util.LinkedList;
import java.io.*;

import static laboratory.util.loader.PluginReader.*;
import laboratory.util.gui.config.OperatorChooserDialog;
import laboratory.util.Description;

import javax.swing.*;

/**
 * @author Andrey Davydov
 */
public abstract class AbstractAlgorithmLoader<I extends Individual> extends JarReader implements AlgorithmLoader<I> {

    public AbstractAlgorithmLoader(JarFile jar, File dir) {
        super(jar, "algorithm.properties");
        File selDir = new File(dir, SELECTION_DIR);
        if (selDir.exists()) {
            selections = Util.map(getJars(selDir), new LoaderFromJar<OperatorLoader<Selection<I>>>());
            index = new boolean[selections.size()];
            if (index.length > 0) {
                index[0] = true;
            }
            names = Util.map(selections, new Functor1<OperatorLoader<Selection<I>>, String>() {
                public String apply(OperatorLoader<Selection<I>> loader) {
                    return loader.getName();
                }
            });
        } else {
            selections = Collections.emptyList();
            index = new boolean[]{};
            names = Collections.emptyList();
        }
    }

    private final List<OperatorLoader<Selection<I>>> selections;

    public String getName() {
        return getProperty("name");
    }

    public List<Selection<I>> getSelections() {
        List<Selection<I>> res = new LinkedList<Selection<I>>();
        for (int i = 0; i < selections.size(); i++) {
            if (index[i]) {
                res.add(selections.get(i).loadOperator());
            }
        }
        return res;
    }

    private final static String SELECTION_DIR = "selections";
    private final static String TITLE = "Selections";

    private final List<String> names;
    private final boolean[] index;

    public JDialog getSelectionChooser(JFrame owner, String title) {
        return new OperatorChooserDialog(owner, title, new boolean[][]{index}, 
                Collections.singletonList(names), Collections.singletonList(TITLE),
                Collections.singletonList(0));
    }

    @Override
    public Writer getConfigWriter(File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(getName());
        getProperties(getJar(), "algorithm.properties").list(writer);
        for (int i = 0; i < selections.size(); i++) {
            if (index[i]) {
                writer.println(selections.get(i).getName());
            }
        }
        return writer;
    }


    @Override
    public String getDescription
            () {
        try {
            return Description.getDecription(getJar().getInputStream(getJar().getEntry(getProperty("description.file"))));
        } catch (IOException e) {
            e.printStackTrace();
            return "Can't read description";
        }
    }

}