package laboratory.plugin.individual.robot.mealy.factory;

import laboratory.plugin.individual.robot.mealy.MealyAutomaton;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.plugin.task.robot.individual.factory.AutomatonFactory;


public class MealyAutomatonFactory extends AutomatonFactory<MealyAutomaton>{

    public MealyAutomatonFactory(int ns, int nns){
        super(ns);
    }

    protected MealyAutomaton fullRandomAutomaton(int ns){
        MealyAutomaton.Transition[][] tr = new MealyAutomaton.Transition[ns][2];
        for(int i = 0;i < ns;i++){
            tr[i][1] = new MealyAutomaton.Transition(r.nextInt(ns), Robot.ACTION_VALUES[r.nextInt(3)]);
            tr[i][0] = new MealyAutomaton.Transition(r.nextInt(ns), Robot.ACTION_VALUES[r.nextInt(3)]);
        }
        return new MealyAutomaton(r.nextInt(ns), tr);
    }

    /*protected MealyAutomaton randomAutomatonWN(int ns, MealyAutomaton na){
        MealyAutomaton.Transition[][] tr = new MealyAutomaton.Transition[ns][2];
        for(int i = 0;i < ns;i ++){
            tr[i][1] = new MealyAutomaton.Transition(r.nextInt(ns + 1) - 1, Ant.ACTION_VALUES[r.nextInt(3)]);
            tr[i][0] = new MealyAutomaton.Transition(r.nextInt(ns + 1) - 1, Ant.ACTION_VALUES[r.nextInt(3)]);
        }
        return new MealyAutomaton(r.nextInt(ns), tr, na);
    }*/
    
    @Override
    public MealyAutomaton cloneIndividual(MealyAutomaton source) {
        int ns = source.getNumberStates();
        MealyAutomaton.Transition[][] tr = new MealyAutomaton.Transition[ns][2];
        Automaton.Transition[][] str = source.getTransition();
        for(int i = 0;i < ns; i++ ) {
            for (int j = 0; j <= 1; ++j) {
                tr[i][j] = new MealyAutomaton.Transition(str[i][j].getEndState(),
                        str[i][j].getAction());
            }
        }
        return new MealyAutomaton(source.getInitialState(), tr);
    }
    
}