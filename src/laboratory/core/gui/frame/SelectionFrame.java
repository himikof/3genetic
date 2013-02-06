package laboratory.core.gui.frame;

import laboratory.core.InterfaceConfig;
import laboratory.core.gui.frame.state.State;
import laboratory.core.gui.frame.state.TaskState;
import laboratory.util.Parser;
import laboratory.common.loader.PluginLoader;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import static java.awt.BorderLayout.*;

import java.util.List;

public class SelectionFrame extends JFrame {

    private final JLabel status = new JLabel();
    private String stat = "";
    private final JButton next = new JButton();
    private final JButton back = new JButton();
    private final JButton config = new JButton();
    private final JPanel buttonPanel = new JPanel();
    private final JEditorPane description = new JEditorPane("text/html; charset=utf8", "");
    private final JList plugins = new JList();

    private State state = TaskState.getInstance(this);

    private List<PluginLoader> loaders;

    public void setNextButtonText(String text) {
        next.setText(text);
    }

    public void setLoaders(final List<PluginLoader> loaders) {
        this.loaders = loaders;
        plugins.setModel(new AbstractListModel() {
            @Override
            public int getSize() {
                return loaders.size();
            }

            @Override
            public Object getElementAt(int i) {
                return loaders.get(i).getName();
            }
        });
        plugins.setSelectedIndex(0);
        plugins.revalidate();
    }

    public void setStatus(String stat) {
        this.stat = stat;
    }

    public void setState(State state) {
        this.state = state;
    }

    public SelectionFrame() {
        super(InterfaceConfig.SELECTION_FRAME_PROPERTIES.getString("title"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        initSize();
        initButtonPanel();
        initLayout();
        initPlugins();

        description.setEditable(false);

        pack();
    }

    private void initSize() {
        Parser p = InterfaceConfig.SELECTION_FRAME_PROPERTIES;
        Dimension d = new Dimension(p.getInt("width"), p.getInt("height"));

        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }

    private void initButtonPanel() {
        final Parser p = InterfaceConfig.SELECTION_FRAME_PROPERTIES;
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                state.back();
            }
        });
        back.setText(p.getString("back-button"));
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                state.next();
            }
        });
        next.setText(p.getString("next-button-next"));
        config.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JDialog d = loaders.get(plugins.getSelectedIndex()).getConfigDialog(SelectionFrame.this);
                d.setLocationByPlatform(true);
                d.setModal(true);
                d.setVisible(true);
            }
        });
        JButton manual = new JButton(p.getString("manual-button"));
        manual.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                MainFrame.showHelp(state.getManualTile(),
                        p.getInt("manual-width"), p.getInt("manual-height"), p.getInt("manual-deltaX"), p.getInt("manual-deltaY"),
                        state.getManualURL(), SelectionFrame.this);
            }
        });
        config.setText(p.getString("config-button"));
        buttonPanel.add(back);
        buttonPanel.add(manual);
        buttonPanel.add(config);
        buttonPanel.add(next);
        buttonPanel.revalidate();
    }

    private void initLayout() {
        setLayout(new BorderLayout());
        getContentPane().add(status, NORTH);
        getContentPane().add(buttonPanel, SOUTH);
        JSplitPane splitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(plugins), new JScrollPane(description));
        splitPanel.setDividerLocation(InterfaceConfig.SELECTION_FRAME_PROPERTIES.getInt("divider-location"));
        getContentPane().add(splitPanel, CENTER);
    }

    public PluginLoader getCurrentLoader() {
        return loaders.get(plugins.getSelectedIndex());
    }

    private void initPlugins() {
        plugins.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        plugins.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (plugins.getSelectedIndex() != -1) {
                    PluginLoader loader = getCurrentLoader();
                    description.setText(loader.getDescription());
                    description.revalidate();
                    config.setEnabled(loader.getConfigDialog(SelectionFrame.this) != null);
                    StringBuilder sb = new StringBuilder();
                    if (stat.isEmpty()) {
                        sb = sb.append(loader.getName());
                    } else {
                        sb = sb.append(stat).append(" > ").append(loader.getName());
                    }
                    for (int i = 0; i < InterfaceConfig.SELECTION_FRAME_PROPERTIES.getInt("tabs-count"); i++) {
                        sb = sb.insert(0, "    ");
                    }
                    status.setText(sb.toString());                    
                }
            }
        });
        setLoaders(TaskState.getLoaders());
    }
}