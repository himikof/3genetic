package laboratory.plugin.algorithm.selection.custom;

import java.util.List;

import laboratory.common.genetic.FitIndividual;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.operator.Selection;

public class CustomSelection<I extends Individual> implements Selection<I> {

    //private final Random random = new Random();

    @Override
    public List<FitIndividual<I>> apply(final List<FitIndividual<I>> population, int m) {
    	return population.subList(0, m);
    	
        /*final int n = population.size();
        final double[] weight = new double[n];
        weight[0] = population.get(0).fitness;
        for (int i = 1; i < n; i++) {
            weight[i] = weight[i - 1] + population.get(i).fitness;
        }
        return Util.listFromFunctor(new Functor0<FitIndividual<I>>() {
            public FitIndividual<I> apply() {
                double p = weight[n - 1] * random.nextDouble();
                int i = 0;
                while (p > weight[i] && i < n - 1) {
                    i++;
                }
                return population.get(i);
            }
        }, m);*/
    }
}