package laboratory.plugin.task.robot.individual.factory;

import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.common.genetic.IndividualFactory;

import java.util.Random;


public abstract class AutomatonFactory<A extends Automaton> implements IndividualFactory<A>{

    private final int numberStates;

    protected final Random r;

    public AutomatonFactory(int ns){
        numberStates = ns;
        r = new Random();
    }

    protected abstract A fullRandomAutomaton(int ns);

    public A getIndividual(){
        return fullRandomAutomaton(numberStates);
    }
}
