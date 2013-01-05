package laboratory.plugin.individual.mealy;

public class Config {

    private int ex;
    private int in;

    public void setEx(int ex) {
        this.ex = ex;
    }

    public void setIn(int in) {
        this.in = in;
    }

    public int getEx() {
        return ex;
    }

    public int getIn() {
        return in;
    }

    private static Config ourInstance = new Config();

    public static Config getInstance() {
        return ourInstance;
    }

    private Config() {
    }
}
