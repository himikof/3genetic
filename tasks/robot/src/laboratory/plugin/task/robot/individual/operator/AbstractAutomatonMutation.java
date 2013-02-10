package laboratory.plugin.task.robot.individual.operator;

import java.util.Random;

import laboratory.common.genetic.operator.Mutation;
import laboratory.plugin.task.robot.individual.AbstractAutomaton;

public abstract class AbstractAutomatonMutation<I extends AbstractAutomaton> implements Mutation<I>{

    public AbstractAutomatonMutation(Random r){
    }

}