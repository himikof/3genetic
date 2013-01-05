package laboratory.plugin.task.ant.individual;

import laboratory.plugin.task.ant.Mover;
import laboratory.plugin.task.ant.Ant;
import laboratory.plugin.task.ant.SimpleAnt;

import java.util.jar.JarFile;


public class SimpleMover implements Mover{

    private Automaton automaton;

    private SimpleAnt ant;

    private Mover nestedMover;

    private int current;

    public SimpleMover(Automaton a){
        this(a, null);

    }

    @Override
    public void restart(Ant ant){
        if(ant == null){
            this.ant = new SimpleAnt();
        } else{
            this.ant = (SimpleAnt)ant;
        }
        Automaton na = automaton.getNestedAutomaton();
        if(na != null){
            nestedMover = new SimpleMover(na, this.ant);
        } else{
            nestedMover = null;
        }
        current = automaton.getInitialState();
    }

    private SimpleMover(Automaton a, SimpleAnt ant){
        automaton = a;
        restart(ant);
    }

    public boolean move(){
        int f = ant.F()[0] ? 1 : 0;
        Automaton.Transition t = automaton.getTransition(current, f);
        int ends = t.getEndState();
        if(ends != -1){
            char action = t.getAction();
            current = ends;
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

    public void reset(){
        this.ant = new SimpleAnt();
        Automaton na = automaton.getNestedAutomaton();
        if(na != null){
            nestedMover = new SimpleMover(na, this.ant);
        } else{
            nestedMover = null;
        }
        current = automaton.getInitialState();

    }
}
