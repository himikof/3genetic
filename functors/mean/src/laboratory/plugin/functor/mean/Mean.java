package laboratory.plugin.functor.mean;

import laboratory.common.Functor;

import java.util.List;


public class Mean implements Functor {

    public double apply(List<Double> values) {
        double res = 0.0;
        for (double v: values) {
            res += v;
        }
        return res / values.size();
    }
    
}
