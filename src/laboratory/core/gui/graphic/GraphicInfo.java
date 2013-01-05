package laboratory.core.gui.graphic;

import laboratory.core.AlgorithmRunner;
import laboratory.common.loader.TaskLoader;
import laboratory.common.loader.AlgorithmLoader;
import laboratory.common.loader.IndividualLoader;
import laboratory.common.genetic.Individual;

public class GraphicInfo<I extends Individual> {

    public final AlgorithmRunner<I> runner;

    public final TaskLoader task;
    public final IndividualLoader<I> individual;
    public final AlgorithmLoader<I> algorithm;

    public GraphicInfo(TaskLoader task, IndividualLoader<I> individual, AlgorithmLoader<I> algorithm, AlgorithmRunner<I> runner) {
        this.runner = runner;
        this.task = task;        
        this.individual = individual;
        this.algorithm = algorithm;
    }
    
}