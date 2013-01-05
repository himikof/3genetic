package laboratory.plugin.individual.mealy;

import laboratory.plugin.task.ant.individual.AbstractAutomaton;
import laboratory.plugin.task.ant.individual.Automaton;

public class MealyAutomaton extends AbstractAutomaton {

    public MealyAutomaton(int is, Automaton.Transition[][] tr, MealyAutomaton na) {
        super(is, na, tr);
    }

    public String getStateString(int i) {
        return (i + 1) + "";
    }

    public Automaton setInitialState(int newIS) {
        return new MealyAutomaton(newIS, getTransition(), (MealyAutomaton) getNestedAutomaton());
    }

    public Automaton setTransitions(Automaton.Transition[][] transitions) {
        return new MealyAutomaton(getInitialState(), transitions, (MealyAutomaton) getNestedAutomaton());
    }

    public Automaton setNestedAutomaton(Automaton a) {
        return new MealyAutomaton(getInitialState(), getTransition(), (MealyAutomaton) a);
    }

    public static class Transition extends AbstractAutomaton.Transition {

        private final char action;

        public Transition(int endState, char action) {
            super(endState);
            this.action = action;
        }

        public Automaton.Transition setEndState(int newEnd) {
            return new Transition(newEnd, action);
        }

        public char getAction() {
            return action;
        }

        public String toString() {
            return "" + action;
        }
    }

}
