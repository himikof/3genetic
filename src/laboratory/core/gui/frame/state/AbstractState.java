package laboratory.core.gui.frame.state;

import laboratory.core.InterfaceConfig;

import java.net.URL;
import java.io.IOException;

import static laboratory.core.gui.Util.getURL;

public abstract class AbstractState implements State {

    protected abstract String name();
    
    @Override
    public String getManualTile() {
        return InterfaceConfig.STATE_PROPERTIES.getString(name() + "-manual-title");
    }

    @Override
    public URL getManualURL() {
        try {
            return getURL(InterfaceConfig.STATE_PROPERTIES.getString(name() + "-manual-url"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
