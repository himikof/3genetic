package laboratory.plugin.individual.robot.mealy.operator.mutation.standard;

import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonMutation;
import laboratory.plugin.individual.robot.mealy.MealyAutomaton;

import java.util.Random;

public class StandardMutation extends AbstractAutomatonMutation<MealyAutomaton>{

    private static final Random random = new Random();

    public StandardMutation(){
        super(random);
    }

}