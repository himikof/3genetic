package laboratory.plugin.task.ant.individual.operator;

import laboratory.plugin.task.ant.individual.AbstractAutomaton;
import laboratory.plugin.task.ant.individual.Automaton;
import laboratory.common.genetic.operator.Mutation;

import java.util.Random;

public abstract class AbstractAutomatonMutation<I extends AbstractAutomaton> implements Mutation<I>{

    private Random r;

    public AbstractAutomatonMutation(Random r){
        this.r = r;
    }

    public I apply(I individual){
        I res = individual;
        if(r.nextBoolean()){
            res = (I)res.setInitialState(r.nextInt(individual.getNumberStates()));
        }
        Automaton.Transition[][] tr = res.getTransition();
        int temp = r.nextInt(tr.length);
        if(tr[temp][r.nextInt(tr[temp].length)] != null){
            tr[temp][r.nextInt(tr[temp].length)].setEndState(r.nextInt(tr.length));
        }
        res = (I)res.setTransitions(tr);
        if((res.getNestedAutomaton() != null) && (r.nextBoolean())){
            res = (I)res.setNestedAutomaton(apply((I)res.getNestedAutomaton()));
        }
        return res;
    }
}