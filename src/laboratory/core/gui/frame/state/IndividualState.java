package laboratory.core.gui.frame.state;

import laboratory.common.loader.TaskLoader;
import laboratory.common.loader.IndividualLoader;
import laboratory.common.loader.PluginLoader;
import laboratory.common.loader.AlgorithmLoader;
import laboratory.common.genetic.Individual;
import laboratory.core.gui.frame.SelectionFrame;
import laboratory.core.PluginCollection;
import laboratory.core.InterfaceConfig;
import laboratory.util.functional.Util;
import laboratory.util.functional.Functor1;

import javax.swing.*;
import java.util.List;

public class IndividualState extends AbstractState {

    private final SelectionFrame frame;
    private final TaskLoader taskLoader;
    private final List<PluginLoader> loaders;

    public IndividualState(SelectionFrame frame, TaskLoader taskLoader, List<PluginLoader> loaders) {
        this.frame = frame;
        this.taskLoader = taskLoader;
        this.loaders = loaders;
    }

    public List<PluginLoader> getLoaders() {
        return loaders;
    }

    public String getStatus() {
        return taskLoader.getName();
    }

    @Override
    public void next() {
        IndividualLoader loader = (IndividualLoader) frame.getCurrentLoader();
        List<PluginLoader> lds = convert(PluginCollection.getInstance().loadAlgorithms());
        if (!(showWarning(loader.getName(), "factories", loader.loadFactories()) ||
                showWarning(loader.getName(), "crossovers", loader.loadCrossovers()) ||
                showWarning(loader.getName(), "mutations", loader.loadMutations()) ||
                showWarning(loader.getName(), "fitness-functions", loader.loadFunctions()))) {
            frame.setState(new AlgorithmState(frame, loader, this, taskLoader));
            frame.setStatus(taskLoader.getName() + " > " + loader.getName());
            frame.setLoaders(lds);
            frame.setNextButtonText(InterfaceConfig.SELECTION_FRAME_PROPERTIES.getString("next-button-load"));            
        }
    }

    @Override
    public void back() {
        frame.setState(TaskState.getInstance(frame));
        frame.setStatus("");
        frame.setLoaders(TaskState.getLoaders());
    }

    private <Plugin> boolean showWarning(String individual, String pluginName, List<Plugin> plugins) {
            if (plugins.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "There are no " + pluginName + " for individual '" + individual + "'!");
                return true;
            } else {
                return false;
            }
        }

        private <I extends Individual> List<PluginLoader> convert(List<AlgorithmLoader<I>> loaders) {
            return Util.map(loaders, new Functor1<AlgorithmLoader<I>, PluginLoader>() {
                @Override
                public AlgorithmLoader<I> apply(AlgorithmLoader<I> loader) {
                    return loader;
                }
            });
        }


    @Override
    protected String name() {
        return "individual";
    }
}
