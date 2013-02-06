package laboratory.plugin.individual.robot.mealy.operator.mutation.standard;

import laboratory.util.loader.OperatorLoader;
import laboratory.plugin.individual.robot.mealy.operator.mutation.standard.StandardMutation;

import javax.swing.*;
import java.util.jar.JarFile;
import java.util.Properties;
import java.io.IOException;

public class MutationLoader implements OperatorLoader<StandardMutation> {

    private Properties p;

    public MutationLoader(JarFile jar) {
        p = new Properties();
        try {
            p.load(jar.getInputStream(jar.getEntry("mutation.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public StandardMutation loadOperator() {
        return new StandardMutation();
    }

    public String getName() {
        return p.getProperty("name");
    }

    public JDialog getConfigDialog(JDialog owner) {
        return null;
    }


}