package laboratory.core;

import laboratory.common.loader.*;
import laboratory.common.Functor;
import laboratory.common.genetic.Individual;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;

import static laboratory.util.loader.PluginReader.*;

import java.util.*;

public class PluginCollection {
    private static PluginCollection ourInstance = new PluginCollection();

    public static PluginCollection getInstance() {
        return ourInstance;
    }

    private final List<TaskLoader> tasks;
    private final List<IndividualLoader> individuals;
    private final List<AlgorithmLoader> algorithms;
    private final List<VisualizatorLoader> visualizators;
    private final List<Functor> functors;
    private final List<String> functorNames;

    private PluginCollection() {
        tasks = Util.map(getJars(Paths.TASKS), new LoaderFromJar<TaskLoader>());
        individuals = Util.map(getDirs(Paths.INDIVIDUALS), new LoaderFromDir<IndividualLoader>());
        algorithms = Util.map(getDirs(Paths.ALGORITHMS), new LoaderFromDir<AlgorithmLoader>());
        visualizators = Util.map(getJars(Paths.VISUALIZATORS), new LoaderFromJar<VisualizatorLoader>()); 
        List<FunctorLoader> functorList = Util.map(getJars(Paths.FUNCTORS), new LoaderFromJar<FunctorLoader>());

        functors = Util.map(functorList, new Functor1<FunctorLoader, Functor>() {
            public Functor apply(FunctorLoader loader) {
                return loader.loadFunctor();
            }
        });
        functorNames = Util.map(functorList, new Functor1<FunctorLoader, String>() {
            public String apply(FunctorLoader loader) {
                return loader.getName();
            }
        });
    }

    public List<TaskLoader> loadTasks() {
        return tasks;
    }

    public List<IndividualLoader> loadIndividuals(final TaskLoader task) {
        return Util.filter(individuals, new Functor1<IndividualLoader, Boolean>() {
            public Boolean apply(IndividualLoader individualLoader) {
                return individualLoader.getTaskName().equals(task.getName());
            }
        });
    }

    public <I extends Individual> List<AlgorithmLoader<I>> loadAlgorithms() {
        return Util.map(algorithms, new Functor1<AlgorithmLoader, AlgorithmLoader<I>>() {
            @SuppressWarnings("unchecked")
            public AlgorithmLoader<I> apply(AlgorithmLoader algorithmLoader) {
                return (AlgorithmLoader<I>) algorithmLoader;
            }
        });
    }

    public List<VisualizatorLoader> loadVisualizators(final IndividualLoader individual) {
        return Util.filter(visualizators, new Functor1<VisualizatorLoader, Boolean>() {
            public Boolean apply(VisualizatorLoader visualizatorLoader) {
                return visualizatorLoader.getIndividualNames().contains(individual.getName());
            }
        });
    }

    public List<Functor> loadFunctors() {
        return functors;
    }

    public List<String> getFunctorNames() {
        return functorNames;
    }

}