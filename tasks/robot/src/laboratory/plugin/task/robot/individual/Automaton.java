package laboratory.plugin.task.robot.individual;

import laboratory.common.Visualizable;

public interface Automaton extends Visualizable{

    public int getInitialState();

    public Transition getTransition(int index, int condition);

    public int getNumberStates();

    public String getStateString(int i);

    public Automaton setInitialState(int newIS);

    public void setInitialStateM(int newIS);

    public Automaton setTransitions(Transition[][] transitions);

    public char getAction(int state);

    public static interface Transition{

        public int getEndState();

        public Transition setEndState(int newEnd);
        public void setEndStateM(int newEnd);

        public char getAction();

        public String toString();
    }

}
