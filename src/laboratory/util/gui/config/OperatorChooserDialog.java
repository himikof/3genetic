package laboratory.util.gui.config;

import javax.swing.*;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import static laboratory.util.functional.Util.getIntArray;

public class OperatorChooserDialog extends JDialog {

    public OperatorChooserDialog(JDialog owner, String title, final boolean[][] index, List<List<String>> names, final List<String> titles,
                                 final List<Integer> exists) {
        super(owner, title);
        init(index, names, titles, exists);
    }

    public OperatorChooserDialog(JFrame owner, String title, final boolean[][] index, List<List<String>> names, final List<String> titles,
                                 final List<Integer> exists) {
        super(owner, title);
        init(index, names, titles, exists);
    }

    private void init(final boolean[][] index, List<List<String>> names, final List<String> titles,
                      final List<Integer> exists) {
        final JList[] list = new JList[names.size()];
        final JPanel contents = new JPanel(new GridLayout(1, names.size()));
        for (int i = 0; i < names.size(); i++) {
            list[i] = new JList(names.get(i).toArray());
            list[i].setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            boolean[] ind = index[exists.get(i)];
            List<Integer> indexes = new LinkedList<Integer>();
            for (int j = 0; j < ind.length; j++) {
                if (ind[j]) {
                    indexes.add(j);
                }
            }
            list[i].setSelectedIndices(getIntArray(indexes));
            JPanel pane = new JPanel(new BorderLayout());
            pane.add(new JScrollPane(list[i]), BorderLayout.CENTER);
            pane.add(new JLabel(titles.get(i)), BorderLayout.NORTH);
            contents.add(pane);
        }

        getContentPane().add(contents, BorderLayout.CENTER);
        getContentPane().add(new OkCancelPanel(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < titles.size(); i++) {
                    boolean[] ind = index[exists.get(i)];
                    int[] selected = list[i].getSelectedIndices();
                    Arrays.fill(ind, false);
                    for (int j : selected) {
                        ind[j] = true;
                    }
                }
                dispose();
            }
        }, this), BorderLayout.SOUTH);
    }

}
