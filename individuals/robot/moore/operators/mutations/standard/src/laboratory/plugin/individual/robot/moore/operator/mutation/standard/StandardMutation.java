package laboratory.plugin.individual.robot.moore.operator.mutation.standard;

import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonMutation;
import laboratory.plugin.individual.robot.moore.MooreAutomaton;

import java.util.Random;

public class StandardMutation extends AbstractAutomatonMutation<MooreAutomaton>{

    private static final Random random = new Random();

    public StandardMutation(){
        super(random);
    }

}