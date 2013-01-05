package laboratory.plugin.individual.robot.moore.operator.crossover.standard;

import laboratory.plugin.individual.robot.moore.MooreAutomaton;
import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonCrossover;

import java.util.Random;

public class StandardCrossover extends AbstractAutomatonCrossover<MooreAutomaton>{

    private static final Random random = new Random();

    public StandardCrossover(){
        super(random);
    }
}