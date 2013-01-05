package laboratory.util.gui.config;

import javax.swing.*;
import java.util.StringTokenizer;
import java.awt.*;

public class TextFieldsPanel extends JPanel {

    public final JTextField[] field;
    
    public TextFieldsPanel(String[] name, String[] tip, String[] defaultValue, int[] columns, int n) {
        super(new GridLayout(n, 1));
        field = new JTextField[n];
        for (int i = 0; i < n; i++) {
            JPanel panel = new JPanel(new FlowLayout());
            field[i] = new JTextField(defaultValue[i], columns[i]);
            panel.add(new JLabel(name[i]));
            panel.add(field[i]);
            panel.setToolTipText(tip[i]);
            add(panel);
        }
        revalidate();
    }

    public String[] getValue() {
        final String[] value = new String[field.length];
        for (int i = 0; i < value.length; i++) {
            value[i] = new StringTokenizer(field[i].getText()).nextToken();
        }
        return value;
    }
}
