package laboratory.plugin.individual.mealy.operator.mutation.standard;

import laboratory.plugin.task.ant.individual.operator.AbstractAutomatonMutation;
import laboratory.plugin.individual.mealy.MealyAutomaton;

import java.util.Random;

public class StandardMutation extends AbstractAutomatonMutation<MealyAutomaton>{

    private static final Random random = new Random();

    public StandardMutation(){
        super(random);
    }

}