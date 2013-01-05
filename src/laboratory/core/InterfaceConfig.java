package laboratory.core;

import laboratory.util.Parser;

import java.util.Properties;
import java.io.IOException;
import java.io.File;
import java.io.FileReader;

/**
 * @author Andrey Davydov
 */
public class InterfaceConfig {

    public static final Parser GRAPHIC_FRAME_PROPERTIES;
    public static final Parser MENU_PROPERTIES;
    public static final Parser INDIVIDUAL_CHOOSER_PROPERTIES;
    public static final Parser VISUALIZATORS_CHOOSER_PROPERTIES;
    public static final Parser MAIN_FRAME_PROPERTIES;
    public static final Parser SELECTION_FRAME_PROPERTIES;
    public static final Parser STATE_PROPERTIES;

    private static Parser loadFrom(String name) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(new File(Paths.RESOURCES, name + ".properties")));
        return new Parser(properties);    
    }

    static {
        try {
            STATE_PROPERTIES = loadFrom("state");
            SELECTION_FRAME_PROPERTIES = loadFrom("selection-frame");
            GRAPHIC_FRAME_PROPERTIES = loadFrom("graphic-frame");
            MENU_PROPERTIES = loadFrom("menu");
            INDIVIDUAL_CHOOSER_PROPERTIES = loadFrom("individuals-chooser");
            VISUALIZATORS_CHOOSER_PROPERTIES = loadFrom("visualizators-chooser");
            MAIN_FRAME_PROPERTIES = loadFrom("main-frame");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}