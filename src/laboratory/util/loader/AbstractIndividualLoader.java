package laboratory.util.loader;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Fitness;
import laboratory.common.loader.IndividualLoader;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;
import static laboratory.util.loader.PluginReader.*;
import laboratory.util.gui.config.OperatorChooserDialog;
import laboratory.util.Description;

import javax.swing.*;
import java.io.*;
import java.util.jar.JarFile;
import java.util.List;
import java.util.Collections;
import java.util.LinkedList;


public abstract class AbstractIndividualLoader<I extends Individual> extends JarReader implements IndividualLoader<I> {

    private static final int INDEX_FACTORY = 0;
    private static final int INDEX_CROSSOVER = 1;
    private static final int INDEX_MUTATION = 2;
    private static final int INDEX_FITNESS = 3;

    private final static String[] DIR_NAME = new String[]{
            "factories",
            "operators" + System.getProperty("file.separator") + "crossovers",
            "operators" + System.getProperty("file.separator") + "mutations",
            "fitness-functions"
    };

    private final static String[] TITLE = new String[]{
            "Factories",
            "Crossovers",
            "Mutatioins",
            "Fitness-functions"
    };

    private final boolean[][] index = new boolean[4][];
    private final List<OperatorLoader>[] loaders = new List[4];
    private final List<String> titles = new LinkedList<String>();
    private final List<Integer> exists = new LinkedList<Integer>();

    public AbstractIndividualLoader(JarFile jar, File dir) {
        super(jar, "individual.properties");
        for (int i = 0; i < 4; i++) {
            File ldir = new File(dir, DIR_NAME[i]);
            if (ldir.exists()) {
                loaders[i] = Util.map(getJars(ldir), new LoaderFromJar<OperatorLoader>());
                index[i] = new boolean[loaders[i].size()];
                if (index[i].length > 0) {
                    index[i][0] = true;
                }
                names.add(Util.map(loaders[i], new Functor1<OperatorLoader, String>() {
                    public String apply(OperatorLoader nameable) {
                        return nameable.getName();
                    }
                }));
                titles.add(TITLE[i]);
                exists.add(i);
            }
        }
    }

    private <Operator> List getList(int i) {
        if (index[i] == null) {
            return Collections.emptyList();
        } else {
            List<OperatorLoader<Operator>> res = new LinkedList<OperatorLoader<Operator>>();
            for (int j = 0; j < index[i].length; j++) {
                if (index[i][j]) {
                    res.add(loaders[i].get(j));
                }
            }
            return Util.map(res, new Functor1<OperatorLoader<Operator>, Operator>() {
                @Override
                public Operator apply(OperatorLoader<Operator> loader) {
                    return loader.loadOperator();
                }
            });
        }
    }

    @Override
    public List<IndividualFactory<I>> loadFactories() {
        return (List<IndividualFactory<I>>) getList(INDEX_FACTORY);
    }

    @Override
    public List<Crossover<I>> loadCrossovers() {
        return (List<Crossover<I>>) getList(INDEX_CROSSOVER);
    }

    @Override
    public List<Mutation<I>> loadMutations() {
        return (List<Mutation<I>>) getList(INDEX_MUTATION);
    }

    @Override
    public List<Fitness<I>> loadFunctions() {
        return (List<Fitness<I>>) getList(INDEX_FITNESS);
    }

    private final List<List<String>> names = new LinkedList<List<String>>();

    public JDialog getOperatorChooser(JFrame owner, String title) {
        return new OperatorChooserDialog(owner, title, index, names, titles, exists);
    }

    public JDialog getOperatorChooser(JDialog owner, String title) {
        return new OperatorChooserDialog(owner, title, index, names, titles, exists);
    }

    @Override
    public String getName() {
        return getProperty("name");
    }

    @Override
    public String getTaskName() {
        return getProperty("task.name");
    }

    @Override
    public String getDescription() {
        try {
            return Description.getDecription(getJar().getInputStream(getJar().getEntry(getProperty("description.file"))));
        } catch (IOException e) {
            e.printStackTrace();
            return "Can't read description";
        }
    }

    @Override
    public Writer getConfigWriter(File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(getName());
        getProperties(getJar(), "individual.properties").list(writer);
        for (int i = 0; i < TITLE.length; i++) {
            if (index[i] != null) {
                writer.println("-- " + TITLE[i] + " --");
                for (int j = 0; j < index[i].length; j++) {
                    if (index[i][j]) {
                        writer.println(loaders[i].get(j).getName());
                    }
                }
            }
        }
        return writer;
    }
}

