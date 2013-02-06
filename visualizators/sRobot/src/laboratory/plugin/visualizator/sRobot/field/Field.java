package laboratory.plugin.visualizator.sRobot.field;

import javax.swing.*;
import java.awt.*;

import laboratory.plugin.visualizator.sRobot.automaton.DocumentPanel;
import laboratory.plugin.visualizator.sRobot.runner.AutomatonRunner;
import laboratory.plugin.task.robot.individual.Automaton;


public class Field extends JFrame{

    private MPanel main;
    private JPanel p;
    private DocumentPanel graph;
    //private DocumentPanel nestedGraph;
    private ButtonPanel button;

    private double tableSize;
    private int eleven;


    public Field(String name, Automaton a, FieldLoader l){

        super(name);
        setLayout(new BorderLayout());

        int width = Integer.parseInt(l.getProperty("width"));
        int height = Integer.parseInt(l.getProperty("height"));
        tableSize = Double.parseDouble(l.getProperty("table.size"));
        eleven = Integer.parseInt(l.getProperty("shift"));

        setSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        setMaximumSize(new Dimension(width, height));

        AutomatonRunner m = new AutomatonRunner(a);

        main = new MPanel(m, l, (int)(width - width * tableSize), height - 2 * eleven);
        getContentPane().add(main, BorderLayout.WEST);

        p = new JPanel();
        p.setLayout(new BorderLayout());

        graph = new DocumentPanel(true, a, m, (int)(width * tableSize) - eleven, height / 2 - 3 * eleven);
        //nestedGraph = new DocumentPanel(true, a.getNestedAutomaton(), m.getNestedMover(), (int)(width * tableSize) - eleven, height / 2 - 3 * eleven);

        p.add(graph, BorderLayout.NORTH);
        //p.add(nestedGraph, BorderLayout.SOUTH);

        getContentPane().add(p, BorderLayout.EAST);

        MPanel[] temp = {main};
        DocumentPanel[] tmp = {graph/*, nestedGraph*/};

        button = new ButtonPanel(temp, tmp, l);

        button.setPreferredSize(new Dimension(getWidth(), 30));

        getContentPane().add(button, BorderLayout.SOUTH);
    }

    public void paint(Graphics g){
        button.setPreferredSize(new Dimension(getWidth(), 30));
        button.setSize(new Dimension(getWidth(), 30));
        main.setPreferredSize(new Dimension((int)(getWidth() * tableSize - eleven + 3), getHeight() - button.getHeight() - eleven));
        //main.setSize(new Dimension((int)(getWidth() * TABLE_SIZE), getHeight() - button.getHeight()));
        p.setPreferredSize((new Dimension((int)(getWidth() * (1 - tableSize) - eleven), getHeight() - button.getHeight() - eleven)));
        //p.setSize((new Dimension((int)(getWidth() * (1 - TABLE_SIZE)), getHeight() - button.getHeight())));
        graph.setPreferredSize(new Dimension((int)p.getPreferredSize().getWidth() - eleven, (int)p.getPreferredSize().getHeight() / 2 - eleven));
        //graph.setSize(new Dimension((int)p.getPreferredSize().getWidth(), (int)p.getPreferredSize().getHeight() / 2));
        //nestedGraph.setPreferredSize(graph.getPreferredSize());
        //nestedGraph.setSize(graph.getPreferredSize());
        super.paint(g);
    }
}
