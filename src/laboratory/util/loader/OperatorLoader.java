package laboratory.util.loader;

import laboratory.common.loader.Nameable;

import javax.swing.*;

public interface OperatorLoader<Operator> extends Nameable {

    public Operator loadOperator();

    public JDialog getConfigDialog(JDialog owner);
    
}
