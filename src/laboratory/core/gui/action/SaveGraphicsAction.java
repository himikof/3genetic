package laboratory.core.gui.action;

import laboratory.common.genetic.Individual;
import laboratory.core.gui.frame.GraphicFrame;
import laboratory.core.InterfaceConfig;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SaveGraphicsAction<I extends Individual> extends AbstractSaveAction<I> {

    private final JPanel graphic;

    public SaveGraphicsAction(int index, GraphicFrame<I> frame, JPanel graphic) {
        super(InterfaceConfig.MENU_PROPERTIES.getString("save-graphics-name"),
                InterfaceConfig.MENU_PROPERTIES.getString("save-graphics-short-description"), index, frame);
        this.graphic = graphic;
    }

    @Override
    public void saveInformation(File dir) throws IOException {
        BufferedImage res = new BufferedImage(graphic.getWidth(), graphic.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = res.createGraphics();
        graphic.paint(g);
        g.dispose();
        ImageIO.write(res, "png", new File(dir, "graphics.png"));
    }
}
