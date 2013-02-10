package laboratory.plugin.task.ant.individual;

import laboratory.common.Visualizable;

public interface Automaton extends Visualizable{

    public int getInitialState();

    public Transition getTransition(int index, int condition);

    public int getNumberStates();

    public Automaton getNestedAutomaton();

    public String getStateString(int i);

    public Automaton setInitialState(int newIS);

    public Automaton setTransitions(Transition[][] transitions);

    public Automaton setNestedAutomaton(Automaton a);

    public static interface Transition{

        public int getEndState();

        public Transition setEndState(int newEnd);
        public void setEndStateM(int newEnd);

        public char getAction();

        public String toString();
    }

}
