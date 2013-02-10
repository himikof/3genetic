package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;

import java.util.List;

public interface Crossover<I extends Individual> {

    public List<I> apply(List<I> parents, IndividualFactory<I> factory);

}
