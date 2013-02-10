package laboratory.plugin.task.ant.individual;


import laboratory.plugin.task.ant.StandardFitness;
import java.util.Arrays;

public abstract class AbstractAutomaton implements Automaton{

    private final int initialState;
    private final Automaton nestedAutomaton;
    private final Automaton.Transition[][] transition;

    private double fitness;

    protected AbstractAutomaton(int is, Automaton na, Automaton.Transition[][] transition){
        initialState = is;
        this.transition = transition;
        nestedAutomaton = na;
        fitness = Double.NEGATIVE_INFINITY;
    }

    public int getNumberStates(){
        return transition.length;
    }

    public int getInitialState(){
        return initialState;
    }

    public Automaton.Transition getTransition(int i, int c){
        return transition[i][c];
    }

    public void setTransition(int i, int c, Automaton.Transition t){
        transition[i][c] = t;
    }

    public Automaton getNestedAutomaton(){
        return nestedAutomaton;
    }

    public double standardFitness(){
        if(fitness == Double.NEGATIVE_INFINITY){
            fitness = StandardFitness.getInstance().calk(new SimpleMover(this));
        }
        return fitness;
    }

    @Override
    public Object[] getAttributes(){
        return new Object[]{this};
    }

    public Automaton.Transition[][] getTransition(){
        Automaton.Transition[][] tr = new Transition[transition.length][];
        for(int i = 0;i < tr.length;i++){
            tr[i] = Arrays.copyOf(transition[i], transition[i].length);
        }
        return tr;
    }

    protected static abstract class Transition implements Automaton.Transition{

        private int endState;

        public Transition(int endState){
            this.endState = endState;
        }
        
        @Override
        public void setEndStateM(int newEnd) {
            this.endState = endState;
        }

        public int getEndState(){
            return endState;
        }
    }
}