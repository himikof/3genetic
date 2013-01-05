package laboratory.util;

import java.util.Properties;
import java.util.StringTokenizer;

/**
 * @author Andrey Davydov
 */
public class Parser {

    private final Properties properties;

    public Parser(Properties p) {
        properties = p;
    }

    public double getDouble(String name) {
        return Double.parseDouble(new StringTokenizer(properties.getProperty(name)).nextToken());
    }

    public int getInt(String name) {
        return Integer.parseInt(new StringTokenizer(properties.getProperty(name)).nextToken());
    }

    public String getString(String name) {
        return properties.getProperty(name);
    }

    public Properties getProperties() {
        return properties;
    }
    
}
