package laboratory.plugin.visualizator.sRobot.field;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import laboratory.plugin.visualizator.sRobot.automaton.DocumentPanel;
import laboratory.plugin.task.robot.Robot;

/**
 * @author Dmitry Sokolov
 */
public class ButtonPanel extends JPanel{

    private MPanel[] field;

    private DocumentPanel[] graph;
    
    private JLabel stepCounter;
    
    private int stepNumber;

    public ButtonPanel(MPanel[] f, DocumentPanel[] g, FieldLoader l){
        field = f;
        graph = g;
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        JButton a = new JButton(l.getProperty("button.step"));
        a.addActionListener(new Step());
        add(a);
        add(Box.createRigidArea(new Dimension(5, 0)));
        stepCounter = new JLabel();
        add(stepCounter);
        stepNumber = 0;
        add(Box.createHorizontalGlue());
        a = new JButton(l.getProperty("button.reset"));
        a.addActionListener(new Reset());
        add(a);
        add(Box.createRigidArea(new Dimension(5, 0)));
        a = new JButton(l.getProperty("button.go"));
        a.addActionListener(new Go());
        add(a);
    }

    private void moveSingle() {
        for(MPanel i : field){
            i.move();
        }
        for(DocumentPanel i : graph){
            i.repaint();
        }
        ++stepNumber;
        stepCounter.setText(String.valueOf(stepNumber));
    }
    
    private class Step implements ActionListener{
        public void actionPerformed(ActionEvent e){
        	moveSingle();
        }
    }

    private class Go implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(int i = 0; i < Robot.NUMBER_STEPS; i++){
                for(MPanel j : field){
                    j.move();
                }
            }
            stepNumber += Robot.NUMBER_STEPS;
            stepCounter.setText(String.valueOf(stepNumber));
            for(DocumentPanel i : graph){
                i.repaint();
            }
        }
    }

    private class Reset implements ActionListener{
        public void actionPerformed(ActionEvent e){
            for(MPanel j : field){
                j.reset();
            }
            stepNumber = 0;
            stepCounter.setText(String.valueOf(stepNumber));
            for(DocumentPanel i : graph){
                i.repaint();
            }
        }
    }
}
