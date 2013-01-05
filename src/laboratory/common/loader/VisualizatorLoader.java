package laboratory.common.loader;

import javax.swing.*;
import java.util.Set;

public interface VisualizatorLoader extends Nameable {

    public JFrame loadVisualizator(Object[] args);

    public Set<String> getIndividualNames();
    
}
