package laboratory.common.genetic;

import java.util.List;

public interface Algorithm<I extends Individual> {

    public void nextGeneration();

    public List<I> getGeneration();

    public void stop();
    
}
