package laboratory.core.gui.action;

import laboratory.common.genetic.Individual;
import laboratory.core.InterfaceConfig;
import laboratory.core.AlgorithmRunner;
import laboratory.core.gui.frame.GraphicFrame;
import laboratory.core.gui.graphic.GraphicManager;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveStatisticsAction<I extends Individual> extends AbstractSaveAction<I> {

    private final ArrayList<ArrayList<Double>> fitnessValues;
    private final List<I> bestIndividuals;
    private final ArrayList<Long> time;
    private final List<String> functors;

    public SaveStatisticsAction(int index, GraphicFrame<I> frame, List<String> functors) {
        super(InterfaceConfig.MENU_PROPERTIES.getString("save-statistics-name"),
                InterfaceConfig.MENU_PROPERTIES.getString("save-statistics-short-description"), index, frame);
        AlgorithmRunner<I> runner = GraphicManager.getInstance().getInfo(index).runner;
        fitnessValues = runner.getStandardFitnessValues();
        bestIndividuals = runner.getBestIndividuals();
        time = runner.getTime();
        this.functors = functors;
    }

    @Override
    public void saveInformation(File dir) throws IOException {
        for (int i = 0; i < time.size(); i++) {
            File gen = new File(dir, "gen" + i);
            if (!gen.exists()) {
                gen.mkdir();
                print(gen, "individual", bestIndividuals.get(i).toString());
                print(gen, "time", time.get(i).toString());
                for (int j = 0; j < functors.size(); j++) {
                    print(gen, functors.get(j), fitnessValues.get(j).get(i).toString());
                }
            }
        }
    }

    private void print(File gen, String filename, String value) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(new File(gen, filename)));
        writer.print(value);
        writer.close();
    }
}
