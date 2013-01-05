package laboratory.plugin.individual.mealy.factory;

import laboratory.plugin.individual.mealy.MealyAutomaton;
import laboratory.plugin.task.ant.Ant;
import laboratory.plugin.task.ant.individual.factory.AutomatonFactory;


public class MealyAutomatonFactory extends AutomatonFactory<MealyAutomaton>{

    public MealyAutomatonFactory(int ns, int nns){
        super(ns, nns);
    }

    protected MealyAutomaton fullRandomAutomaton(int ns){
        MealyAutomaton.Transition[][] tr = new MealyAutomaton.Transition[ns][2];
        for(int i = 0;i < ns;i++){
            tr[i][1] = new MealyAutomaton.Transition(r.nextInt(ns), Ant.ACTION_VALUES[r.nextInt(3)]);
            tr[i][0] = new MealyAutomaton.Transition(r.nextInt(ns), Ant.ACTION_VALUES[r.nextInt(3)]);
        }
        return new MealyAutomaton(r.nextInt(ns), tr, null);
    }

    protected MealyAutomaton randomAutomatonWN(int ns, MealyAutomaton na){
        MealyAutomaton.Transition[][] tr = new MealyAutomaton.Transition[ns][2];
        for(int i = 0;i < ns;i ++){
            tr[i][1] = new MealyAutomaton.Transition(r.nextInt(ns + 1) - 1, Ant.ACTION_VALUES[r.nextInt(3)]);
            tr[i][0] = new MealyAutomaton.Transition(r.nextInt(ns + 1) - 1, Ant.ACTION_VALUES[r.nextInt(3)]);
        }
        return new MealyAutomaton(r.nextInt(ns), tr, na);
    }
}