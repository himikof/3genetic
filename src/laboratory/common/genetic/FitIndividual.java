package laboratory.common.genetic;

public class FitIndividual<I extends Individual> implements Comparable<FitIndividual<I>> {

    public final I ind;
    public final double fitness;

    public FitIndividual(I ind, double fitness) {
        this.ind = ind;
        this.fitness = fitness;
    }

    public int compareTo(FitIndividual<I> that) {
        return Double.compare(that.fitness, this.fitness);
    }
    
    @Override
    public String toString() {
    	return "FI<" + ind + ", " + fitness + ">";
    }
}
