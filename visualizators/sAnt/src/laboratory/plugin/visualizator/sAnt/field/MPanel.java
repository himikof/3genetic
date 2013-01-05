package laboratory.plugin.visualizator.sAnt.field;


import javax.swing.*;
import java.awt.*;

import laboratory.plugin.visualizator.sAnt.runner.AutomatonRunner;
import laboratory.plugin.task.ant.Ant;


public class MPanel extends JPanel{

    private AutomatonRunner mover;

    private int fieldSize;

    public MPanel(AutomatonRunner m, FieldLoader l, int width, int height){
        mover = m;
        setLayout(null);
        fieldSize = Integer.parseInt(l.getProperty("field.size"));
        setPreferredSize(new Dimension(width, height));
    }

    public void paint(Graphics g){
        g.clearRect(0, 0, getWidth(), getHeight());
        for(int i = 0;i < fieldSize + 1;i++){
            g.drawLine(0, i * (getHeight() - 1) / fieldSize, getWidth(), i * (getHeight() - 1) / fieldSize);
        }
        for(int i = 0;i < fieldSize + 1;i++){
            g.drawLine(i * (getWidth() - 1) / fieldSize, 0, i * (getWidth() - 1) / fieldSize, getHeight());
        }
        boolean[][] food = mover.getField();
        Color temp = g.getColor();
        g.setColor(Color.BLACK);
        for(int i = 0;i < fieldSize;i++){
            for(int j = 0;j < fieldSize;j++){
                if(food[i][j]){
                    g.fillRect(j * getWidth() / fieldSize, i * getHeight() / fieldSize, (getWidth() + fieldSize - 1) / fieldSize, (getHeight() + fieldSize - 1) / fieldSize);
                }
            }
        }
        g.setColor(temp);
        Ant.Cell cell = mover.getCell();
        Ant.Direction dir = mover.getDirection();
        switch(dir){
            case LEFT:
                paintLeft(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize, getHeight() / fieldSize);
                break;
            case RIGHT:
                paintRight(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize, getHeight() / fieldSize);
                break;
            case TOP:
                paintTop(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize, getHeight() / fieldSize);
                break;
            case BOTTOM:
                paintBottom(g, cell.y * getWidth() / fieldSize, cell.x * getHeight() / fieldSize, getWidth() / fieldSize, getHeight() / fieldSize);
                break;
        }
    }

    public void paintRight(Graphics g, int x, int y, int width, int height){
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y, x + width, y + height / 2);
        g.drawLine(x + width, y + height / 2, x, y + height);
        g.setColor(temp);
    }

    public void paintLeft(Graphics g, int x, int y, int width, int height){
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x + width, y, x, y + height / 2);
        g.drawLine(x, y + height / 2, x + width, y + height);
        g.setColor(temp);
    }

    public void paintTop(Graphics g, int x, int y, int width, int height){
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y + height / 2, x + width / 2, y);
        g.drawLine(x + width / 2, y, x + width, y + height / 2);
        g.setColor(temp);
    }

    public void paintBottom(Graphics g, int x, int y, int width, int height){
        Color temp = g.getColor();
        g.setColor(Color.RED);
        g.drawLine(x, y + height / 2, x + width / 2, y + height);
        g.drawLine(x + width / 2, y + height, x + width, y + height / 2);
        g.setColor(temp);
    }

    public void move(){
        mover.move();
        repaint();
    }
}
