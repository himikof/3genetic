package laboratory.plugin.algorithm.simple;

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

public class SimpleGA<I extends Individual> implements Algorithm<I>{

    private List<FitIndividual<I>> generation;

    private final double probabilityMutation;
    private final int generationSize;

    private final IndividualFactory<I> factory;

    private Random r;

    private final Mutation<I> mut;
    private final Crossover<I> cross;
    private final Selection<I> sel;
    private final Fitness<I> fitness;

    private final int fixedPart;

    public SimpleGA(final int generationSize, final double probabilityMutation, final IndividualFactory<I> factory,
                    final Mutation<I> mut, final Crossover<I> cross, final Selection<I> sel, final Fitness<I> fitness){

        this.probabilityMutation = probabilityMutation;
        this.generationSize = generationSize;

        this.factory = factory;

        this.mut = mut;
        this.cross = cross;
        this.sel = sel;
        this.fitness = fitness;

        //ToDo: Rewrite this!!
        fixedPart = generationSize / 2;

        reset();
    }

    public void reset() {
        this.generation = Util.listFromFunctor(new Functor0<FitIndividual<I>>(){
            public FitIndividual<I> apply(){
                return cons(factory.getIndividual());
            }
        }, generationSize);
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

    public void nextGeneration(){
        int size = generation.size();
        List<FitIndividual<I>> newGeneration = new ArrayList<FitIndividual<I>>(size);
        newGeneration.addAll(sel.apply(generation, fixedPart));
        while(newGeneration.size() + 1 <= generation.size()) {
            List<I> s = cross.apply(Arrays.asList(winner(randomI(), randomI()), winner(randomI(), randomI())),
                    factory);
            for(I ind : s){
                newGeneration.add(cons(ind));
            }
        }
        if(newGeneration.size() < size){
            newGeneration.add(cons(mut.apply(randomI().ind, factory)));
        }
        for(int i = 0;i < size;i ++){
            if(r.nextDouble() < probabilityMutation){
                newGeneration.set(i, cons(mut.apply(newGeneration.get(i).ind, factory)));
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
