package laboratory.plugin.algorithm.selection.roulette;

import laboratory.common.genetic.FitIndividual;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.operator.Selection;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor0;

import java.util.List;
import java.util.Random;

public class RouletteSelection<I extends Individual> implements Selection<I> {

    private final Random random = new Random();

    @Override
    public List<FitIndividual<I>> apply(final List<FitIndividual<I>> population, int m) {
        final int n = population.size();
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
        }, m);
    }
}