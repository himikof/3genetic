package laboratory.plugin.task.robot.individual;


import java.util.Arrays;

public abstract class AbstractAutomaton implements Automaton{

    private int initialState;
    private final Automaton.Transition[][] transition;

    private double fitness;

    protected AbstractAutomaton(int is, Automaton.Transition[][] transition){
        initialState = is;
        this.transition = transition;
        fitness = Double.NEGATIVE_INFINITY;
    }

    public int getNumberStates(){
        return transition.length;
    }

    public int getInitialState(){
        return initialState;
    }
  
    @Override
    public void setInitialStateM(int newIS) {
        initialState = newIS;
    }
    public Automaton.Transition getTransition(int i, int c){
        return transition[i][c];
    }

    public void setTransition(int i, int c, Automaton.Transition t){
        transition[i][c] = t;
    }
    
    protected abstract double calcStandartFitness();

    public double standardFitness(){
        //System.err.println("standartFitness: " + this.getClass());
        if(fitness == Double.NEGATIVE_INFINITY){
            //fitness = StandardFitness.getInstance().calc(new SimpleMover(this));
            fitness = calcStandartFitness();
        }
        return fitness;
    }

    @Override
    public Object[] getAttributes(){
        return new Object[]{this};
    }

    protected Automaton.Transition[][] getTransitionData() {
        return transition;
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
        
        public void setEndStateM(int endState) {
            this.endState = endState;
        }

        public int getEndState(){
            return endState;
        }
    }
}