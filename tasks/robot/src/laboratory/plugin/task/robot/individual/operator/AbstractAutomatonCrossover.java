package laboratory.plugin.task.robot.individual.operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import laboratory.common.genetic.IndividualFactory;
import laboratory.common.genetic.operator.Crossover;
import laboratory.plugin.task.robot.individual.AbstractAutomaton;
import laboratory.plugin.task.robot.individual.Automaton;

public class AbstractAutomatonCrossover<I extends AbstractAutomaton> implements Crossover<I>{

    private Random r;

    public AbstractAutomatonCrossover(Random r){
        this.r = r;
    }

    public List<I> apply(List<I> parents, IndividualFactory<I> factory){
        I p = parents.get(0);
        I p1 = parents.get(1);
        I s = factory.cloneIndividual(p);
        I s1 = factory.cloneIndividual(p1);
        if(r.nextBoolean()){
            s.setInitialStateM(p1.getInitialState());
            s1.setInitialStateM(p.getInitialState());
        }

        Automaton.Transition[][] tr = s.getTransition();
        Automaton.Transition[][] tr1 = s1.getTransition();

        for(int i = 0;i < tr.length;i++){
            int flag = r.nextInt(4);
            switch(flag){
                case 0:
                    tr[i][0] = p1.getTransition(i, 0);
                    tr[i][1] = p.getTransition(i, 1);
                    tr1[i][0] = p.getTransition(i, 0);
                    tr1[i][1] = p1.getTransition(i, 1);
                    break;
                case 1:
                    tr[i][0] = p.getTransition(i, 0);
                    tr[i][1] = p1.getTransition(i, 1);
                    tr1[i][0] = p1.getTransition(i, 0);
                    tr1[i][1] = p.getTransition(i, 1);
                    break;
                case 2:
                    tr[i][0] = p1.getTransition(i, 0);
                    tr[i][1] = p1.getTransition(i, 1);
                    tr1[i][0] = p.getTransition(i, 0);
                    tr1[i][1] = p.getTransition(i, 1);
                    break;
                case 3:
                    tr[i][0] = p.getTransition(i, 0);
                    tr[i][1] = p.getTransition(i, 1);
                    tr1[i][0] = p1.getTransition(i, 0);
                    tr1[i][1] = p1.getTransition(i, 1);
                    break;
            }
        }

        //s = (I)s.setTransitions(tr);
        //s1 = (I)s1.setTransitions(tr1);

        ArrayList<I> res = new ArrayList<I>();
        res.add(s);
        res.add(s1);
        return res;
    }

}
