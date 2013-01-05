package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;

public interface Fitness<I extends Individual> {

    public double apply(I individual);

}
