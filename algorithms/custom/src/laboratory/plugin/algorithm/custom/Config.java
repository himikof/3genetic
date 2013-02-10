package laboratory.plugin.algorithm.custom;

import laboratory.util.loader.JarReader;
import laboratory.util.Parser;

import java.util.jar.JarFile;

public class Config implements Cloneable {
    public Config() {
    }

    @Override
    public Config clone() {
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new IllegalStateException(e);
        }
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        this.mutationProbability = mutationProbability;
    }

    private double mutationProbability;

    public int getGenerationSize() {
        return generationSize;
    }

    public void setGenerationSize(int generationSize) {
        this.generationSize = generationSize;
    }

    private int generationSize;

    public int getChildrenCount() {
        return childrenCount;
    }

    public void setChildrenCount(int childrenCount) {
        this.childrenCount = childrenCount;
    }

    private int childrenCount;
    
    
    public boolean isUsingCrossover() {
        return usingCrossover;
    }

    public void setUsingCrossover(boolean useCrossover) {
        this.usingCrossover = useCrossover;
    }

    private boolean usingCrossover;

    public void setJar(JarFile jar) {
        Parser p = new Parser(JarReader.getProperties(jar,
                "algorithm.properties"));
        setGenerationSize(p.getInt("size.generation"));
        //setMutationProbability(p.getDouble("probability.mutation"));
        setChildrenCount(p.getInt("size.children"));
        setUsingCrossover(p.getBoolean("use.crossover"));
    }
}