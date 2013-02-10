package laboratory.plugin.individual.robot.moore;

import java.util.Arrays;

import laboratory.plugin.task.robot.StandardFitness;
import laboratory.plugin.task.robot.individual.AbstractAutomaton;
import laboratory.plugin.task.robot.individual.Automaton;

public class MooreAutomaton extends AbstractAutomaton {

    private final char[] actions;
    
    public MooreAutomaton(int is, Automaton.Transition[][] tr, 
            char[] actions) {
        super(is, tr);
        for (Transition[] tx : (Transition[][])tr) {
            for (Transition t : tx) {
                t.setOwner(this);
            }
        }
        this.actions = actions;
    }

    public String getStateString(int i) {
        return (i + 1) + "";
    }

    public Automaton setInitialState(int newIS) {
        return new MooreAutomaton(newIS, getTransition(), actions);
    }

    public Automaton setTransitions(Automaton.Transition[][] transitions) {
        return new MooreAutomaton(getInitialState(), transitions, actions);
    }
    
    public Automaton setActions(char[] actions) {
        return new MooreAutomaton(getInitialState(), getTransition(), actions);
    }

    public char getAction(int state) {
        return actions[state];
    }
    
    public void setActionM(int state, char action) {
        actions[state] = action;
    }
    
    public Transition[][] getTransition(){
        Automaton.Transition[][] trs = getTransitionData();
        Transition[][] tr = new Transition[trs.length][];
        for(int i = 0;i < tr.length;i++){
            tr[i] = Arrays.copyOf(trs[i], trs[i].length, Transition[].class);
        }
        return tr;
    }

    public static class Transition extends AbstractAutomaton.Transition {
        
        public Transition(int endState) {
            super(endState);
        }
       
        private MooreAutomaton owner;
        
        void setOwner(MooreAutomaton owner) {
            this.owner = owner;
        }
        
        public Automaton.Transition setEndState(int newEnd) {
            return new Transition(newEnd);
        }

        public String toString() {
            return "<MooreAutomaton.Transition>";
        }

        public char getAction() {
            if (owner == null) {
                throw new IllegalStateException();
            }
            return owner.getAction(getEndState());
        }
    }

    @Override
    protected double calcStandartFitness() {
        return StandardFitness.getInstance().calc(new MooreMover(this));
    }

}
