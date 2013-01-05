package laboratory.util.gui.config;

import laboratory.util.Parser;

import javax.swing.*;
import java.awt.*;

public class Util {

    public static void setSize(JDialog dialog, Parser p) {
        setSize(dialog, new Dimension(p.getInt("width"), p.getInt("height")));
    }

    public static void setSize(JDialog dialog, Dimension d) {
        dialog.setSize(d);
        dialog.setPreferredSize(d);
        dialog.setMinimumSize(d);
        dialog.setMaximumSize(d);
        dialog.pack();
    }

    public static void showModal(JDialog dialog) {
        dialog.setModal(true);
        dialog.setLocationByPlatform(true);
        dialog.setVisible(true);
    }
}
