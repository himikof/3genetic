package laboratory.plugin.algorithm.custom;

import laboratory.util.loader.JarReader;
import laboratory.util.Parser;

import java.util.jar.JarFile;

public class Config{
    private static Config ourInstance = new Config();

    public static Config getInstance(){
        return ourInstance;
    }

    private Config(){
    }

    public double getMutationProbability(){
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability){
        this.mutationProbability = mutationProbability;
    }

    private double mutationProbability;

    public int getGenerationSize(){
        return generationSize;
    }

    public void setGenerationSize(int generationSize){
        this.generationSize = generationSize;
    }

    private int generationSize;

    public int getBigMutationSteps(){
        return bigMutationSteps;
    }

    public void setBigMutationSteps(int bigMutationSteps){
        this.bigMutationSteps = bigMutationSteps;
    }

    private int bigMutationSteps;
    
    public boolean useStabilizingBigMutation() {
        return useStabilizingBigMutation;
    }

    public void setUseStabilizingBigMutation(boolean useStabilizingBigMutation) {
        this.useStabilizingBigMutation = useStabilizingBigMutation;
    }

    private boolean useStabilizingBigMutation;

    public double getElitismSize() {
        return elitismSize;
    }

    public void setElitismSize(double elitismSize) {
        this.elitismSize = elitismSize;
    }

    private double elitismSize;
    
    public void setJar(JarFile jar){
        Parser p = new Parser(JarReader.getProperties(jar, "algorithm.properties"));
        setGenerationSize(p.getInt("size.generation"));
        setElitismSize(p.getDouble("size.elitism"));
        setMutationProbability(p.getDouble("probability.mutation"));
        setBigMutationSteps(p.getInt("bigmutation.stepsize"));
        setUseStabilizingBigMutation(p.getInt("bigmutation.usestabilizing") != 0);
    }
}