package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.FitIndividual;

import java.util.List;

public interface Selection<I extends Individual> {

    public List<FitIndividual<I>> apply(List<FitIndividual<I>> population, int n);

}