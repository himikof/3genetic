package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;

import java.util.List;

public interface Crossover<I extends Individual> {

    public List<I> apply(List<I> parents);

}
