package laboratory.plugin.task.robot.individual;

import laboratory.plugin.task.robot.Mover;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.SimpleRobot;


public class SimpleMover implements Mover{

    private Automaton automaton;

    private SimpleRobot robot;

    private int current;

    public SimpleMover(Automaton a){
        this(a, null);

    }

    @Override
    public void restart(Robot robot){
        if(robot == null){
            this.robot = new SimpleRobot();
        } else{
            this.robot = (SimpleRobot)robot;
        }
        current = automaton.getInitialState();
    }

    private SimpleMover(Automaton a, SimpleRobot ant){
        automaton = a;
        restart(ant);
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
