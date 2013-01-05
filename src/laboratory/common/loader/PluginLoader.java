package laboratory.common.loader;

import java.io.Writer;
import java.io.File;
import java.io.FileNotFoundException;

public interface PluginLoader extends Nameable, Descriptable, Configurable {

    public Writer getConfigWriter(File file) throws FileNotFoundException;
    
}