package laboratory.plugin.task.ant.individual.operator;

import laboratory.plugin.task.ant.individual.AbstractAutomaton;
import laboratory.plugin.task.ant.individual.Automaton;
import laboratory.common.genetic.operator.Crossover;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class AbstractAutomatonCrossover<I extends AbstractAutomaton> implements Crossover<I>{

    private Random r;

    public AbstractAutomatonCrossover(Random r){
        this.r = r;
    }

    public List<I> apply(List<I> parents){
        I s = parents.get(0);
        I s1 = parents.get(1);
        I p = s;
        I p1 = s1;
        if(r.nextBoolean()){
            s = (I)s.setInitialState(p1.getInitialState());
            s1 = (I)s1.setInitialState(p.getInitialState());
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

        s = (I)s.setTransitions(tr);
        s1 = (I)s1.setTransitions(tr1);

        if(p.getNestedAutomaton() != null){
            switch(r.nextInt(3)){
                case 0:
                    s = (I)s.setNestedAutomaton(p1.getNestedAutomaton());
                    s1 = (I)s1.setNestedAutomaton(p.getNestedAutomaton());
                    break;
                case 2:
                    List<I> list = new ArrayList<I>();
                    list.add((I)p.getNestedAutomaton());
                    list.add((I)p1.getNestedAutomaton());
                    List<I> temp = apply(list);

                    s = (I)s.setNestedAutomaton(temp.get(0));
                    s1 = (I)s1.setNestedAutomaton(temp.get(1));
                    break;
            }
        }
        ArrayList<I> res = new ArrayList<I>();
        res.add(s);
        res.add(s1);
        return res;
    }

}
