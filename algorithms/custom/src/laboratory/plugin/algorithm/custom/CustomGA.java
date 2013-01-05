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

    private final double probabilityMutation;
    private final int bigMutationStepSize;
    private final double elitismSize;
    private final boolean useStabilizingBigMutation;

    private final IndividualFactory<I> factory;

    private final Random r;

    private final Mutation<I> mut;
    private final Crossover<I> cross;
    private final Selection<I> sel;
    private final Fitness<I> fitness;

    private final int fixedPart;

    public CustomGA(final List<I> generation, final IndividualFactory<I> factory,
                    final Mutation<I> mut, final Crossover<I> cross, final Selection<I> sel, final Fitness<I> fitness){

        this.probabilityMutation = Config.getInstance().getMutationProbability();
        this.elitismSize = Config.getInstance().getElitismSize();
        this.bigMutationStepSize = Config.getInstance().getBigMutationSteps();
        this.useStabilizingBigMutation = Config.getInstance().useStabilizingBigMutation();

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

    public void nextGeneration(){
        int size = generation.size();
        List<FitIndividual<I>> newGeneration = new ArrayList<FitIndividual<I>>(size);
        newGeneration.addAll(sel.apply(generation, fixedPart));
        while(newGeneration.size() + 1 <= generation.size()){
            List<I> s = cross.apply(Arrays.asList(winner(randomI(), randomI()), winner(randomI(), randomI())));
            for(I ind : s){
                newGeneration.add(cons(ind));
            }
        }
        if(newGeneration.size() < size){
            newGeneration.add(cons(mut.apply(randomI().ind)));
        }
        for(int i = 0;i < size;i ++){
            if(r.nextDouble() < probabilityMutation){
                newGeneration.set(i, cons(mut.apply(newGeneration.get(i).ind)));
            }
        }
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


    public void bigMutation(){
        for(int i = 0;i < generation.size();i++){
            final I ind = factory.getIndividual();
            generation.set(i, new FitIndividual<I>(ind, fitness.apply(ind)));
        }
        Collections.sort(generation);
    }

}
