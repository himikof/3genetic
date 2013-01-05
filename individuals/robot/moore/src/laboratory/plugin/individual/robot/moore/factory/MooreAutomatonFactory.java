package laboratory.plugin.individual.robot.moore.factory;

import laboratory.plugin.individual.robot.moore.MooreAutomaton;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.individual.factory.AutomatonFactory;


public class MooreAutomatonFactory extends AutomatonFactory<MooreAutomaton>{

    public MooreAutomatonFactory(int ns){
        super(ns);
    }

    protected MooreAutomaton fullRandomAutomaton(int ns){
        MooreAutomaton.Transition[][] tr = new MooreAutomaton.Transition[ns][2];
        char[] actions = new char[ns];
        for(int i = 0;i < ns;i++){
            actions[i] = Robot.ACTION_VALUES[r.nextInt(3)];
            tr[i][1] = new MooreAutomaton.Transition(r.nextInt(ns));
            tr[i][0] = new MooreAutomaton.Transition(r.nextInt(ns));
        }
        return new MooreAutomaton(r.nextInt(ns), tr, actions);
    }

    /*protected MooreAutomaton randomAutomatonWN(int ns, MooreAutomaton na){
        MooreAutomaton.Transition[][] tr = new MooreAutomaton.Transition[ns][2];
        for(int i = 0;i < ns;i ++){
            tr[i][1] = new MooreAutomaton.Transition(r.nextInt(ns + 1) - 1, Robot.ACTION_VALUES[r.nextInt(3)]);
            tr[i][0] = new MooreAutomaton.Transition(r.nextInt(ns + 1) - 1, Robot.ACTION_VALUES[r.nextInt(3)]);
        }
        return new MooreAutomaton(r.nextInt(ns), tr, na);
    }*/
}