package laboratory.core.gui.frame.state;

import laboratory.core.InterfaceConfig;
import laboratory.core.AlgorithmRunner;
import laboratory.core.PluginCollection;
import laboratory.core.gui.frame.SelectionFrame;
import laboratory.core.gui.frame.GraphicFrame;
import laboratory.core.gui.graphic.GraphicManager;
import laboratory.core.gui.graphic.GraphicInfo;
import laboratory.common.loader.IndividualLoader;
import laboratory.common.loader.AlgorithmLoader;
import laboratory.common.loader.TaskLoader;
import laboratory.common.genetic.Individual;
import laboratory.util.Parser;

import javax.swing.*;
import java.awt.*;

public class AlgorithmState<I extends Individual> extends AbstractState {

    private final SelectionFrame frame;
    private final IndividualLoader<I> individualLoader;
    private final TaskLoader taskLoader;
    private final IndividualState previousState;

    public AlgorithmState(SelectionFrame frame, IndividualLoader<I> individualLoader, IndividualState previousState, TaskLoader taskLoader) {
        this.frame = frame;
        this.individualLoader = individualLoader;
        this.previousState = previousState;
        this.taskLoader = taskLoader;
    }

    @Override
    public void next() {
        final Parser gfp = InterfaceConfig.GRAPHIC_FRAME_PROPERTIES;
        final AlgorithmLoader<I> loader = (AlgorithmLoader<I>) frame.getCurrentLoader();
        final String message = loader.getMessage();
        if (message.equals("OK")) {
            final AlgorithmRunner<I> runner = new AlgorithmRunner<I>(loader, individualLoader.loadFactories(),
                    individualLoader.loadCrossovers(), individualLoader.loadMutations(), individualLoader.loadFunctions());
            new Thread(runner).start();
            String title = gfp.getString("title") + individualLoader.getTaskName() + ", Individual - " + individualLoader.getName() +
                    ", Genetic Algortihm - " + loader.getName();
            if (runner.getTitle() != null) {
                title = runner.getTitle();
            }
            final GraphicManager<I> gm = GraphicManager.getInstance();
            final GraphicFrame<I> gf = new GraphicFrame<I>(title, gm.addGraphic(
                    new GraphicInfo<I>(taskLoader, individualLoader, loader, runner)),
                    PluginCollection.getInstance().loadVisualizators(individualLoader));
            gf.setSize(gfp.getInt("width"), gfp.getInt("height"));
            gf.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
            gf.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(frame, message);
        }
    }

    @Override
    public void back() {
        frame.setNextButtonText(InterfaceConfig.SELECTION_FRAME_PROPERTIES.getString("next-button-next"));
        frame.setStatus(previousState.getStatus());
        frame.setLoaders(previousState.getLoaders());
        frame.setState(previousState);
    }

    @Override
    protected String name() {
        return "algorithm";
    }
}