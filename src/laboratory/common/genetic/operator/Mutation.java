package laboratory.common.genetic.operator;

import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;

public interface Mutation<I extends Individual> {

    public I apply(I individual, IndividualFactory<I> factory);

}