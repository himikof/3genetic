package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;

public interface Mutation<I extends Individual> {

    public I apply(I individual);

}