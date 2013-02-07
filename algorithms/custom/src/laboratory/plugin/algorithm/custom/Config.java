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
    
    public int getChildrenCount() {
		return childrenCount;
	}

	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

	private int childrenCount;
    
    public void setJar(JarFile jar){
        Parser p = new Parser(JarReader.getProperties(jar, "algorithm.properties"));
        setGenerationSize(p.getInt("size.generation"));
        setMutationProbability(p.getDouble("probability.mutation"));
        setChildrenCount(p.getInt("size.children"));
    }
}