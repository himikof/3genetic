package laboratory.common.genetic;

public interface IndividualFactory<I extends Individual> {

    public I getIndividual();
    public I cloneIndividual(I source);
    
}
