package laboratory.plugin.individual.robot.moore.operator.mutation.standard;

import java.util.Random;

import laboratory.common.genetic.IndividualFactory;
import laboratory.plugin.individual.robot.moore.MooreAutomaton;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonMutation;

public class StandardMutation extends AbstractAutomatonMutation<MooreAutomaton>{

    private static final Random r = new Random();

    public StandardMutation(){
        super(r);
    }

    public MooreAutomaton apply(MooreAutomaton individual, IndividualFactory<MooreAutomaton> factory){
        MooreAutomaton res = factory.cloneIndividual(individual);
        if(r.nextDouble() < 0.05) {
            res.setInitialStateM(r.nextInt(individual.getNumberStates()));
        }
        
        Automaton.Transition[][] tr = res.getTransition();
        //int temp = r.nextInt(tr.length);
        for (int i = 0; i < tr.length; ++i) {
            for (int j = 0; j <= 1; ++j) {
                MooreAutomaton.Transition t = (MooreAutomaton.Transition) tr[i][j];
                if (r.nextDouble() < 0.05) {
                    t.setEndStateM(r.nextInt(tr.length));
                }
            }
            if (r.nextDouble() < 0.05) {
                res.setActionM(i, Robot.ACTION_VALUES[r.nextInt(3)]);
            }
        }
        return res;
    }
    
}