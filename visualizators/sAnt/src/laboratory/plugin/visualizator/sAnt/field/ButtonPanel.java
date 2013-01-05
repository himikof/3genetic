package laboratory.plugin.visualizator.sAnt.field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import laboratory.plugin.visualizator.sAnt.automaton.DocumentPanel;
import laboratory.plugin.task.ant.Ant;

/**
 * @author Dmitry Sokolov
 */
public class ButtonPanel extends JPanel{

    private MPanel[] field;

    private DocumentPanel[] graph;

    public ButtonPanel(MPanel[] f, DocumentPanel[] g, FieldLoader l){
        field = f;
        graph = g;
        setLayout(new BorderLayout());
        JButton a = new JButton(l.getProperty("button.step"));
        a.addActionListener(new Step());
        add(a, BorderLayout.WEST);
        a = new JButton(l.getProperty("button.go"));
        a.addActionListener(new Go());
        add(a, BorderLayout.EAST);
    }

    private class Step implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(MPanel i : field){
                i.move();
            }
            for(DocumentPanel i : graph){
                i.repaint();
            }
        }
    }

    private class Go implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0;i < Ant.NUMBER_STEPS;i++){
                for(MPanel j : field){
                    j.move();
                }
            }
            for(DocumentPanel i : graph){
                i.repaint();
            }
        }
    }
}
