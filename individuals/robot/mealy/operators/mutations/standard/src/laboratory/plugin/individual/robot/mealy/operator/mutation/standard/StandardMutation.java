package laboratory.plugin.individual.robot.mealy.operator.mutation.standard;

import laboratory.common.genetic.IndividualFactory;
import laboratory.plugin.task.robot.Robot;
import laboratory.plugin.task.robot.individual.Automaton;
import laboratory.plugin.task.robot.individual.operator.AbstractAutomatonMutation;
import laboratory.plugin.individual.robot.mealy.MealyAutomaton;

import java.util.Random;

public class StandardMutation extends AbstractAutomatonMutation<MealyAutomaton>{

    private static final Random r = new Random();

    public StandardMutation(){
        super(r);
    }

    public MealyAutomaton apply(MealyAutomaton individual, IndividualFactory<MealyAutomaton> factory){
        MealyAutomaton res = factory.cloneIndividual(individual);
        if(r.nextDouble() < 0.05) {
            res.setInitialStateM(r.nextInt(individual.getNumberStates()));
        }
        
        Automaton.Transition[][] tr = res.getTransition();
        //int temp = r.nextInt(tr.length);
        for (int i = 0; i < tr.length; ++i) {
            for (int j = 0; j <= 1; ++j) {
                MealyAutomaton.Transition t = (MealyAutomaton.Transition) tr[i][j];
                if (r.nextDouble() < 0.05) {
                    t.setEndStateM(r.nextInt(tr.length));
                    t.setActionM(Robot.ACTION_VALUES[r.nextInt(3)]);
                }
            }
        }
        return res;
    }

}