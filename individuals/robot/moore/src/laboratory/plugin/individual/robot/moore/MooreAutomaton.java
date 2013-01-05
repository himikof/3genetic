package laboratory.plugin.individual.robot.moore;

import laboratory.plugin.task.robot.individual.AbstractAutomaton;
import laboratory.plugin.task.robot.individual.Automaton;

public class MooreAutomaton extends AbstractAutomaton {

    private final char[] actions;
    
    public MooreAutomaton(int is, Automaton.Transition[][] tr, 
            char[] actions) {
        super(is, tr);
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
    
    public static class Transition extends AbstractAutomaton.Transition {
        
        public Transition(int endState) {
            super(endState);
        }
        
        public Automaton.Transition setEndState(int newEnd) {
            return new Transition(newEnd);
        }

        public String toString() {
            return "";
        }
    }

}
