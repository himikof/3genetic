package laboratory.plugin.functor.max;

import laboratory.common.Functor;

import java.util.List;


public class MaxGAFunctor implements Functor {

    public double apply(List<Double> values) {
        return values.get(0);
    }
    
}
