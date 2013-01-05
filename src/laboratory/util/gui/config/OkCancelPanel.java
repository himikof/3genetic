package laboratory.util.gui.config;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.*;

public class OkCancelPanel extends JPanel {

    public OkCancelPanel(final ActionListener okListener, final JDialog dialog) {
        super(new FlowLayout());

        JButton ok = new JButton("OK");
        ok.addActionListener(okListener);
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        add(ok);
        add(cancel);
        revalidate();        
    }
    
}
