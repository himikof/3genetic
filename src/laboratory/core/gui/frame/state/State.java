package laboratory.core.gui.frame.state;

import java.net.URL;

public interface State {

    public void next();
    public void back();

    public String getManualTile();
    public URL getManualURL();
    
}
