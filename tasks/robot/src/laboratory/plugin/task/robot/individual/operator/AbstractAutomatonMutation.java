package laboratory.plugin.task.robot.individual.operator;

import laboratory.plugin.task.robot.individual.AbstractAutomaton;
import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.common.genetic.operator.Mutation;

import java.util.Random;

public abstract class AbstractAutomatonMutation<I extends AbstractAutomaton> implements Mutation<I>{

    private Random r;

    public AbstractAutomatonMutation(Random r){
        this.r = r;
    }

    public I apply(I individual){
        I res = individual;
        if(r.nextDouble() < 0.05) {
            res = (I)res.setInitialState(r.nextInt(individual.getNumberStates()));
        }
        
        Automaton.Transition[][] tr = res.getTransition();
        int temp = r.nextInt(tr.length);
        if(tr[temp][r.nextInt(tr[temp].length)] != null){
            tr[temp][r.nextInt(tr[temp].length)].setEndState(r.nextInt(tr.length));
        }
        res = (I)res.setTransitions(tr);
        return res;
    }
}