package laboratory.plugin.visualizator.sAnt.runner;

import laboratory.plugin.task.ant.individual.Automaton;
import laboratory.plugin.task.ant.SimpleAnt;
import laboratory.plugin.task.ant.Ant;

public class AutomatonRunner{

    private Automaton a;

    private int currentState;

    private AutomatonRunner nestedMover;

    private SimpleAnt ant;

    public AutomatonRunner(Automaton a){
        this(a, new SimpleAnt());
    }

    private AutomatonRunner(Automaton a, SimpleAnt ant){
        this.a = a;
        this.ant = ant;
        currentState = a.getInitialState();
        nestedMover = (a.getNestedAutomaton() == null) ? null : new AutomatonRunner(a.getNestedAutomaton(), ant);
        System.out.println(nestedMover);
    }

    public boolean move(){
        int f = ant.F()[0] ? 1 : 0;
        Automaton.Transition t = a.getTransition(currentState, f);
        int ends = t.getEndState();
        if(ends != -1){
            char action = t.getAction();
            currentState = ends;
            switch(action){
                case 'L':
                    ant.L();
                    break;
                case 'R':
                    ant.R();
                    break;
                case 'M':
                    ant.M();
                    break;
            }
            return ((f == 1) && (action == 'M'));
        } else{
            if(nestedMover != null){
                return nestedMover.move();
            } else{
                throw new RuntimeException("fuflo!!!");
            }
        }
    }

    public Ant.Cell getCell(){
        return ant.getCurrent();
    }

    public Ant.Direction getDirection(){
        return ant.getDirection();
    }

    public boolean[][] getField(){
        return ant.getField();
    }

    public int getState(){
        return currentState;
    }

    public AutomatonRunner getNestedMover(){
        return nestedMover;
    }
}
