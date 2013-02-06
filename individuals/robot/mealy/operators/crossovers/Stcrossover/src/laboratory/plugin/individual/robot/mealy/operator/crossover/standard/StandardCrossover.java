package laboratory.plugin.individual.robot.mealy.operator.crossover.standard;

import laboratory.plugin.individual.robot.mealy.MealyAutomaton;
import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonCrossover;

import java.util.Random;

public class StandardCrossover extends AbstractAutomatonCrossover<MealyAutomaton>{

    private static final Random random = new Random();

    public StandardCrossover(){
        super(random);
    }
}