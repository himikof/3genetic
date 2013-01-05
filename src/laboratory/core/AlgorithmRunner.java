package laboratory.core;

import laboratory.common.genetic.Algorithm;
import laboratory.common.genetic.Individual;
import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.common.genetic.operator.Mutation;
import laboratory.common.genetic.operator.Fitness;
import laboratory.common.Functor;
import laboratory.common.loader.AlgorithmLoader;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;
import laboratory.util.functional.Functor0;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmRunner<I extends Individual> implements Runnable {

    private final AlgorithmLoader<I> loader;
    private final List<IndividualFactory<I>> factories;
    private final List<Crossover<I>> crossovers;
    private final List<Mutation<I>> mutations;
    private final List<Fitness<I>> functions;

    private final Object monitor = new Object();

    private boolean kill = false;
    private boolean pause = true;
    private boolean stop = true;

    private Algorithm<I> algorithm;

    private final List<Functor> functors = PluginCollection.getInstance().loadFunctors();
    private final ArrayList<Long> time = new ArrayList<Long>();
    private final ArrayList<ArrayList<Double>> standardFitnessValues;
    private final List<I> bestIndividuals = new ArrayList<I>();
    private final List<Double> bestFitnessValues = new ArrayList<Double>();
    private List<Double> currentFitnessValues;


    public void run() {
        while (!kill) {
            if (pause) {
                synchronized (monitor) {
                    while (pause) {
                        try {
                            monitor.wait(10000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (kill) {
                        break;
                    }
                }
            }
            long st = System.nanoTime();
            algorithm.nextGeneration();
            time.add(System.nanoTime() - st);
            List<I> generation = algorithm.getGeneration();
            currentFitnessValues = Util.map(generation, new Functor1<I, Double>() {
                public Double apply(I i) {
                    return i.standardFitness();
                }
            });
            bestIndividuals.add(generation.get(0));
            bestFitnessValues.add(currentFitnessValues.get(0));
            for (int i = 0; i < functors.size(); i++) {
                final ArrayList<Double> val = standardFitnessValues.get(i);
                synchronized (val) {
                    val.add(functors.get(i).apply(currentFitnessValues));
                }
            }
        }
        algorithm.stop();
    }

    public AlgorithmRunner(AlgorithmLoader<I> loader, List<IndividualFactory<I>> factories,
                           List<Crossover<I>> crossovers, List<Mutation<I>> mutations, List<Fitness<I>> functions) {
        this.loader = loader;
        this.factories = factories;
        this.crossovers = crossovers;
        this.mutations = mutations;
        this.functions = functions;
        standardFitnessValues = Util.listFromFunctor(new Functor0<ArrayList<Double>>() {
            public ArrayList<Double> apply() {
                return new ArrayList<Double>();
            }
        }, functors.size());
    }

    public void pause() {
        pause = true;
    }

    public void resume() {
        if (stop) {
            throw new RuntimeException("Runner is stopped!");
        }
        pause = false;
        synchronized (monitor) {
            monitor.notify();
        }
    }

    public void kill() {
        kill = true;
        pause = false;
        synchronized (monitor) {
            monitor.notify();
        }
    }

    public void restart() {
        stop = false;
        for (List<Double> values : standardFitnessValues) {
            values.clear();
        }
        bestIndividuals.clear();
        bestFitnessValues.clear();
        time.clear();
        //ToDo: This section ought to be fixed. (Selection operator)
        algorithm = loader.loadAlgorithm(factories, crossovers, mutations, null, functions);
        resume();
    }

    public void stop() {
        stop = true;
        algorithm.stop();
        pause();
    }

    public ArrayList<Long> getTime() {
        return time;
    }

    public ArrayList<ArrayList<Double>> getStandardFitnessValues() {
        return standardFitnessValues;
    }

    public List<I> getGeneration() {
        return algorithm.getGeneration();
    }


    public List<I> getBestIndividuals() {
        return bestIndividuals;
    }

    public List<Double> getBestFitnessValues() {
        return bestFitnessValues;
    }

    public List<Double> getCurrentFitnessValues() {
        return currentFitnessValues;
    }

}
