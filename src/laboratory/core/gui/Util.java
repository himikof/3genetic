package laboratory.core.gui;

import laboratory.util.Parser;
import laboratory.core.InterfaceConfig;
import laboratory.core.Paths;
import laboratory.core.gui.frame.MainFrame;

import java.io.IOException;
import java.io.File;
import java.net.URL;

/**
 * @author Dmitry Sokolov
 */
public class Util {

    public static void showMainFrame() {
        try {
            Parser p = InterfaceConfig.MAIN_FRAME_PROPERTIES;
            MainFrame frame = new MainFrame(InterfaceConfig.SELECTION_FRAME_PROPERTIES.getString("title"),
                    getURL(p.getString("logo.url")), getURL(p.getString("description.url")),
                    getURL(p.getString("plan.url")));
            frame.setSize(p.getInt("width"), p.getInt("height"));
            frame.setVisible(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static URL getURL(String filename) throws IOException {
        return new File(Paths.DOCS, filename).toURI().toURL();
    }
}
