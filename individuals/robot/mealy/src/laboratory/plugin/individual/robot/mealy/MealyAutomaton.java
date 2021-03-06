package laboratory.plugin.individual.robot.mealy;

import laboratory.plugin.task.robot.StandardFitness;
import laboratory.plugin.task.robot.individual.AbstractAutomaton;
import laboratory.plugin.task.robot.individual.Automaton;

public class MealyAutomaton extends AbstractAutomaton {

    public MealyAutomaton(int is, Automaton.Transition[][] tr) {
        super(is, tr);
    }

    public String getStateString(int i) {
        return (i + 1) + "";
    }

    public Automaton setInitialState(int newIS) {
        return new MealyAutomaton(newIS, getTransition());
    }

    public Automaton setTransitions(Automaton.Transition[][] transitions) {
        return new MealyAutomaton(getInitialState(), transitions);
    }

    public Automaton setNestedAutomaton(Automaton a) {
        return new MealyAutomaton(getInitialState(), getTransition());
    }

    public static class Transition extends AbstractAutomaton.Transition {

        private char action;

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
        
        public void setActionM(char action) {
            this.action = action;
        }

        public String toString() {
            return "" + action;
        }
    }

    @Override
    public char getAction(int state) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected double calcStandartFitness() {
        return StandardFitness.getInstance().calc(new MealyMover(this));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("states=").append(getNumberStates()).append('\n');
        sb.append("initial=").append(getInitialState()).append('\n');
        String[] events = new String[] {"S", "W"};
        for (int i = 0; i < getNumberStates(); ++i) {
            for (int j = 0; j < 2; ++j) {
                Transition t = (Transition) getTransition(i, j);
                sb.append("tr.").append(i).append(".").append(events[j]);
                sb.append('=').append(t.getEndState()).append(',');
                sb.append(t.action).append('\n');
            }
        }
        return sb.toString();
    }
}
