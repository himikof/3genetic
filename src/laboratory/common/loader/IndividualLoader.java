package laboratory.common.loader;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Fitness;

import java.util.List;

public interface IndividualLoader<I extends Individual> extends PluginLoader {

    public List<IndividualFactory<I>> loadFactories();

    public List<Crossover<I>> loadCrossovers();

    public List<Mutation<I>> loadMutations();

    public List<Fitness<I>> loadFunctions();

    public String getTaskName();

}