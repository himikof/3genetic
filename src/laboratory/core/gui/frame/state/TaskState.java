package laboratory.core.gui.frame.state;

import laboratory.common.loader.TaskLoader;
import laboratory.common.loader.PluginLoader;
import laboratory.common.loader.IndividualLoader;
import laboratory.core.PluginCollection;
import laboratory.core.gui.frame.SelectionFrame;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;

import javax.swing.*;
import java.util.List;

public class TaskState extends AbstractState {

    private final SelectionFrame frame;

    private TaskState(SelectionFrame frame) {
        this.frame = frame;
    }

    private static TaskState ourInstance = null;

    public static TaskState getInstance(SelectionFrame frame) {
        if ((ourInstance == null) || (frame != ourInstance.frame)) {
            ourInstance = new TaskState(frame);
        }
        return ourInstance;
    }

    public static List<PluginLoader> getLoaders() {
        return Util.map(PluginCollection.getInstance().loadTasks(), new Functor1<TaskLoader, PluginLoader>() {
            @Override
            public PluginLoader apply(TaskLoader taskLoader) {
                return taskLoader;
            }
        });
    }

    @Override
    public void next() {
        TaskLoader loader = (TaskLoader) frame.getCurrentLoader();
        List<PluginLoader> loaders = convert(PluginCollection.getInstance().loadIndividuals(loader));
        if (loaders.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "There are no individuals for task '" + loader.getName() + "'!");
        } else {
            frame.setState(new IndividualState(frame, loader, loaders));
            frame.setStatus(loader.getName());
            frame.setLoaders(loaders);
        }
    }

    private List<PluginLoader> convert(List<IndividualLoader> loaders) {
        return Util.map(loaders, new Functor1<IndividualLoader, PluginLoader>() {
            @Override
            public IndividualLoader apply(IndividualLoader loader) {
                return loader;
            }
        });
    }

    @Override
    public void back() {
        frame.dispose();
        laboratory.core.gui.Util.showMainFrame();
    }


    @Override
    protected String name() {
        return "task";
    }
}
