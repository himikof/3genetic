package laboratory.plugin.individual.robot.mealy;

import laboratory.plugin.task.robot.Mover;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.SimpleRobot;
import laboratory.plugin.task.robot.individual.Automaton;


public class MealyMover implements Mover{

    private MealyAutomaton automaton;

    private Robot robot;

    private int current;

    public MealyMover(MealyAutomaton a){
        this(a, null);

    }

    @Override
    public void restart(Robot robot){
        if(robot == null){
            this.robot = new SimpleRobot();
        } else{
            this.robot = robot;
        }
        current = automaton.getInitialState();
    }

    private MealyMover(MealyAutomaton a, Robot robot){
        automaton = a;
        restart(robot);
    }

    public boolean move(){
        int w = robot.W() ? 1 : 0;
        Automaton.Transition t = automaton.getTransition(current, w);
        int ends = t.getEndState();
        if(ends != -1){
            char action = t.getAction();
            current = ends;
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
}
