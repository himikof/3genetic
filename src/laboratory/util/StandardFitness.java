package laboratory.util;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.operator.Fitness;

public class StandardFitness<I extends Individual> implements Fitness<I>{


    public double apply(I individual){
        return individual.standardFitness();
    }
}
