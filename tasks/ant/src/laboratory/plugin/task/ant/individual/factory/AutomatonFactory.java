package laboratory.plugin.task.ant.individual.factory;

import laboratory.plugin.task.ant.individual.Automaton;
import laboratory.common.genetic.IndividualFactory;

import java.util.Random;


public abstract class AutomatonFactory<A extends Automaton> implements IndividualFactory<A>{

    private final int numberStates;
    private final int numberNestedStates;

    protected final Random r;

    public AutomatonFactory(int ns, int nns){
        numberStates = ns;
        numberNestedStates = nns;
        r = new Random();
    }

    protected abstract A fullRandomAutomaton(int ns);

    protected abstract A randomAutomatonWN(int ns, A na);

    public A getIndividual(){
        if(numberNestedStates != 0){
            return randomAutomatonWN(numberStates, fullRandomAutomaton(numberNestedStates));
        } else{
            return fullRandomAutomaton(numberStates);
        }
    }
}
