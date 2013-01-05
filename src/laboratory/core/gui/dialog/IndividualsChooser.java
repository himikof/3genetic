package laboratory.core.gui.dialog;

import laboratory.common.genetic.Individual;
import laboratory.common.loader.VisualizatorLoader;
import laboratory.common.Visualizable;
import laboratory.core.InterfaceConfig;
import laboratory.util.Parser;
import laboratory.util.gui.config.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class IndividualsChooser<I extends Individual> extends JDialog {

    private int selectedIndex = -1;

    public IndividualsChooser(final List<I> individuals, List<Double> fitnessValues, List<Integer> indeces,
                              final List<VisualizatorLoader> visualizators, JFrame owner) {
        super(owner, InterfaceConfig.INDIVIDUAL_CHOOSER_PROPERTIES.getString("title"));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        final Parser p = InterfaceConfig.INDIVIDUAL_CHOOSER_PROPERTIES;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - p.getInt("width")) / 2, (screenSize.height - p.getInt("height")) / 2);

        IndividualsTableModel tm = new IndividualsTableModel(fitnessValues, indeces);
        JTable tb = new JTable(tm);
        tb.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tb.getColumnModel().getColumn(0).setMinWidth(p.getInt("count.column.min"));
        tb.getColumnModel().getColumn(0).setMaxWidth(p.getInt("count.column.max"));
        tb.setPreferredScrollableViewportSize(new Dimension(p.getInt("width"),
                Math.min(individuals.size() * tb.getRowHeight(), p.getInt("table.height"))));
        ListSelectionModel sM = tb.getSelectionModel();
        sM.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int i = ((ListSelectionModel) e.getSource()).getMinSelectionIndex();
                if ((i >= 0) && (i < individuals.size())) {
                    selectedIndex = i;
                }
            }
        });
        JScrollPane scr = new JScrollPane(tb);

        final JButton ok = new JButton("OK");
        ok.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex != -1) {
                    showVisualizatorsDialog(visualizators, individuals.get(selectedIndex));
                }
                dispose();
            }
        });

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scr, BorderLayout.NORTH);
        getContentPane().add(ok, BorderLayout.SOUTH);

        Util.setSize(this, p);

    }

    private void showVisualizatorsDialog(List<VisualizatorLoader> visualizators, I individual) {
        if (visualizators.isEmpty()) {
            JOptionPane.showMessageDialog(this, InterfaceConfig.INDIVIDUAL_CHOOSER_PROPERTIES.getString("warning.no.visualizators"));
        } else {
            JDialog dialog = new VisualizatorsChooser(visualizators,
                    ((Visualizable) individual).getAttributes(), this);
            Util.setSize(dialog, InterfaceConfig.VISUALIZATORS_CHOOSER_PROPERTIES);
            Util.showModal(dialog);
        }
    }

    private static class IndividualsTableModel extends AbstractTableModel {

        private static final String[] COLNAME = new String[4];

        static {
            Parser p = InterfaceConfig.INDIVIDUAL_CHOOSER_PROPERTIES;
            for (int i = 0; i < 4; i++) {
                COLNAME[i] = p.getString("colname." + (i + 1));
            }
        }

        private int selected = -1;
        private final List<Double> fitnessValues;
        private final List<Integer> indeces;

        public IndividualsTableModel(List<Double> fitnessValues, List<Integer> indeces) {
            this.fitnessValues = fitnessValues;
            this.indeces = indeces;
        }

        @Override
        public int getColumnCount() {
            return 4;
        }

        @Override
        public String getColumnName(int i) {
            return COLNAME[i];
        }

        @Override
        public int getRowCount() {
            return indeces.size();
        }

        @Override
        public boolean isCellEditable(int row, int column) {
            return column == 0;
        }

        @Override
        public Class<?> getColumnClass(int column) {
            switch (column) {
                case 0:
                    return Boolean.class;
                case 1:
                    return Integer.class;
                case 2:
                    return Double.class;
                case 3:
                    return Integer.class;
                default:
                    return Object.class;
            }
        }

        @Override
        public void setValueAt(Object value, int row, int col) {
            if (col == 0) {
                selected = row;
                fireTableDataChanged();
            }
        }

        @Override
        public Object getValueAt(int row, int col) {
            switch (col) {
                case 0:
                    return selected == row;
                case 1:
                    return row + 1;
                case 2:
                    return fitnessValues.get(row);
                case 3:
                    return indeces.get(row);
                default:
                    return new Object();
            }
        }

    }

}
