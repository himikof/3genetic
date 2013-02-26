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

    private Random r;

    private final Mutation<I> mut;
    private final Crossover<I> cross;
    private final Selection<I> sel;
    private final Fitness<I> fitness;

    public CustomGA(final Config config, final IndividualFactory<I> factory,
                    final Mutation<I> mut, final Crossover<I> cross, final Selection<I> sel, final Fitness<I> fitness){

    	this.config = config;

        this.factory = factory;

        this.mut = mut;
        this.cross = cross;
        this.sel = sel;
        this.fitness = fitness;
        
        reset();
    }
    
    public void reset() {
        this.generation = Util.listFromFunctor(new Functor0<FitIndividual<I>>(){
            public FitIndividual<I> apply() {
                return cons(factory.getIndividual());
            }
        }, config.getGenerationSize());
        Collections.sort(this.generation);

        r = new Random();
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
            I child;
            if (config.isUsingCrossover()) {
                List<I> parents = new ArrayList<I>();
                parents.add(randomI().ind);
                parents.add(randomI().ind);
                List<I> s = cross.apply(parents, factory);
                //assert s.size() == 1;
                child = s.get(0);
            } else {
                child = randomI().ind;
            }
            child = mut.apply(child, factory);
            
            children.add(cons(child));
        }
        Collections.sort(children);
        
        List<FitIndividual<I>> newGeneration = new ArrayList<FitIndividual<I>>();
        //newGeneration.addAll(children.subList(0, mu));
        int elSize = 0;
        /*if (r.nextDouble() < 1) {
            elSize = Math.max((int)(mu * 0.02), 1);
            newGeneration.addAll(sel.apply(generation, elSize));
        }*/
        newGeneration.addAll(sel.apply(children, mu - elSize));
        generation = newGeneration;
        if (elSize > 0) {
            Collections.sort(generation);
        }
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

    @Override
    public String getTitle() {
        StringBuilder sb = new StringBuilder();
        sb.append("CustomGA: ").append(config.toString());
        return sb.toString();
    }
}
