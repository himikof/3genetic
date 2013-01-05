package laboratory.common.loader;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.Algorithm;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Fitness;
import laboratory.common.genetic.operator.Selection;

import java.util.List;

public interface AlgorithmLoader<I extends Individual> extends PluginLoader {

    public Algorithm<I> loadAlgorithm(List<IndividualFactory<I>> factories, List<Crossover<I>> crossovers,
                                      List<Mutation<I>> mutations, List<Selection<I>> sel, List<Fitness<I>> functions);

    public String getMessage();

}