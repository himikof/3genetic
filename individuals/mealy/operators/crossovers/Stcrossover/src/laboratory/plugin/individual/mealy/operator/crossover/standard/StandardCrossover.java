package laboratory.plugin.individual.mealy.operator.crossover.standard;

import laboratory.plugin.individual.mealy.MealyAutomaton;
import laboratory.plugin.task.ant.individual.operator.AbstractAutomatonCrossover;

import java.util.Random;

public class StandardCrossover extends AbstractAutomatonCrossover<MealyAutomaton>{

    private static final Random random = new Random();

    public StandardCrossover(){
        super(random);
    }
}