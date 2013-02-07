package laboratory.plugin.algorithm.custom;

import laboratory.common.genetic.Algorithm;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.FitIndividual;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Selection;
import laboratory.common.genetic.operator.Fitness;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;
import laboratory.util.functional.Functor0;

import java.util.*;

public class CustomGA<I extends Individual> implements Algorithm<I>{

    private List<FitIndividual<I>> generation;
    
    private final Config config;

    private final IndividualFactory<I> factory;

    private final Random r;

    private final Mutation<I> mut;
    private final Crossover<I> cross;
    private final Selection<I> sel;
    private final Fitness<I> fitness;

    private final int fixedPart;

    public CustomGA(final List<I> generation, final IndividualFactory<I> factory,
                    final Mutation<I> mut, final Crossover<I> cross, final Selection<I> sel, final Fitness<I> fitness){

    	this.config = Config.getInstance();

        this.factory = factory;

        this.mut = mut;
        this.cross = cross;
        this.sel = sel;
        this.fitness = fitness;

        this.generation = Util.map(generation, new Functor1<I, FitIndividual<I>>(){
            public FitIndividual<I> apply(I i){
                return cons(i);
            }
        });
        Collections.sort(this.generation);

        //ToDo: Rewrite this!!
        fixedPart = generation.size() / 2;


        r = new Random();
    }

    public CustomGA(final int sizeGeneration, final IndividualFactory<I> factory,
                    final Mutation<I> mut, final Crossover<I> cross, final Selection<I> sel, final Fitness<I> fitness){
        this(Util.listFromFunctor(new Functor0<I>(){
                     public I apply(){
                         return factory.getIndividual();
                     }
                 }, sizeGeneration),
             factory, mut, cross, sel, fitness);
    }

    private I winner(FitIndividual<I> a1, FitIndividual<I> a2){
        return ((a1.compareTo(a2) < 0) ? a1 : a2).ind;
    }

    private FitIndividual<I> cons(I i){
        return new FitIndividual<I>(i, fitness.apply(i));
    }

    private FitIndividual<I> randomI(){
        return generation.get(r.nextInt(generation.size()));
    }

    public void nextGeneration() {
    	int mu = config.getGenerationSize();
    	int lambda = config.getChildrenCount();
        List<FitIndividual<I>> children = new ArrayList<FitIndividual<I>>();
        for (int i = 0; i < lambda; ++i) {
        	List<I> parents = new ArrayList<I>();
        	parents.add(randomI().ind);
        	parents.add(randomI().ind);
            List<I> s = cross.apply(parents);
            assert s.size() == 1;
            I child = s.get(0);
            
            if(r.nextDouble() < config.getMutationProbability()) {
            	child = mut.apply(child);
            }
            
            children.add(cons(child));
        }
        
        List<FitIndividual<I>> newGeneration = new ArrayList<FitIndividual<I>>();
        //newGeneration.addAll(children.subList(0, mu));
        newGeneration.addAll(sel.apply(children, mu));
        generation = newGeneration;
        Collections.sort(generation);
    }


    public List<I> getGeneration(){
        return Util.map(generation, new Functor1<FitIndividual<I>, I>(){
            public I apply(FitIndividual<I> i){
                return i.ind;
            }
        });
    }

    public void stop(){
    }
}
