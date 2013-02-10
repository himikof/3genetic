package laboratory.plugin.individual.robot.moore.factory;

import laboratory.plugin.individual.robot.moore.MooreAutomaton;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.individual.Automaton;
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
    @Override
    public MooreAutomaton cloneIndividual(MooreAutomaton source) {
        int ns = source.getNumberStates();
        MooreAutomaton.Transition[][] tr = new MooreAutomaton.Transition[ns][2];
        Automaton.Transition[][] str = source.getTransition();
        for(int i = 0;i < ns; i++ ) {
            for (int j = 0; j <= 1; ++j) {
                tr[i][j] = new MooreAutomaton.Transition(str[i][j].getEndState());
            }
        }
        char[] actions = new char[ns];
        for (int i = 0; i < ns; ++i) {
            actions[i] = source.getAction(i);
        }
        return new MooreAutomaton(source.getInitialState(), tr, actions);
    }

}