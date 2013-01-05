package laboratory.plugin.individual.mealy.operator.crossover.standard;

import laboratory.util.loader.OperatorLoader;

import javax.swing.*;
import java.util.jar.JarFile;
import java.util.Properties;
import java.io.IOException;

public class CrossoverLoader implements OperatorLoader<StandardCrossover>{

    private Properties p;
    
    public CrossoverLoader(JarFile jar){
        p = new Properties();
        try{
            p.load(jar.getInputStream(jar.getEntry("crossover.properties")));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public StandardCrossover loadOperator(){
        return new StandardCrossover();
    }


    public String getName(){
        return p.getProperty("name");
    }

    public JDialog getConfigDialog(JDialog owner){
        return null;
    }

}