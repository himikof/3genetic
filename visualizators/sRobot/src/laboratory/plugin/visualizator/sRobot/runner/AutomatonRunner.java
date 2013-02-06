package laboratory.plugin.visualizator.sRobot.runner;

import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.plugin.task.robot.SimpleRobot;
import laboratory.plugin.task.robot.Robot;

public class AutomatonRunner{

    private Automaton automaton;

    private int currentState;

    private AutomatonRunner nestedMover;

    private SimpleRobot robot;

    public AutomatonRunner(Automaton a){
        this(a, new SimpleRobot());
    }

    private AutomatonRunner(Automaton a, SimpleRobot robot){
        this.automaton = a;
        this.robot = robot;
        reset();
        //nestedMover = (a.getNestedAutomaton() == null) ? null : new AutomatonRunner(a.getNestedAutomaton(), robot);
        nestedMover = null;
    }
    
    public void reset() {
    	robot.reset();
    	currentState = automaton.getInitialState();
    }

    public boolean move() {
        int w = robot.W() ? 1 : 0;
        Automaton.Transition t = automaton.getTransition(currentState, w);
        int ends = t.getEndState();
        if(ends != -1){
            char action = t.getAction();
            currentState = ends;
            switch(action){
                case 'L':
                    robot.L();
                    break;
                case 'R':
                    robot.R();
                    break;
                case 'M':
                    if (w == 0)
                        robot.M();
                    break;
            }
            return false;
        } else{
            throw new RuntimeException("fuflo!!!");
        }
    }

    public Robot.Cell getCell(){
        return robot.getCurrent();
    }

    public Robot.Direction getDirection(){
        return robot.getDirection();
    }

    public Robot.Cell getStart() {
        return robot.getStart();
    }
    
    public Robot.Cell getTarget() {
        return robot.getTarget();
    }
    
    public boolean[][] getField(){
        return robot.getField();
    }

    public int getState(){
        return currentState;
    }

    public AutomatonRunner getNestedMover(){
        return nestedMover;
    }
}
