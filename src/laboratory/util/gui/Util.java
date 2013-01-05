package laboratory.util.gui;

import java.awt.*;

public class Util {

    public static Point weighedPoint(Dimension d, double xw, double yw) {
        return new Point((int) (d.width * xw), (int) (d.height * yw));    
    }

}
