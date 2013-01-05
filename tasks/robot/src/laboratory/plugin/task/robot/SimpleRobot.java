package laboratory.plugin.task.robot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.jar.JarFile;

public class SimpleRobot extends AbstractRobot{

    private static boolean[][] FIELD = null;
    private static Cell START = null;
    private static Cell TARGET = null;

    public static void readField(JarFile jar){
        FIELD = new boolean[32][32];
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(jar.getInputStream(jar.getEntry("field.in"))));
            for(int i = 0; i < 32; i++) {
                String s = in.readLine();
                for(int j = 0; j < 32; j++) {
                    char c = s.charAt(j);
                    if (c == 's')
                        START = new Cell(i, j);
                    if (c == 't')
                        TARGET = new Cell(i, j);
                    FIELD[i][j] = s.charAt(j) == '*';
                }
            }
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public SimpleRobot(){
        super(START, TARGET);
    }

    public boolean W(){
        Cell nextCell = getCurrent().next(getDirection());
        return FIELD[nextCell.x][nextCell.y];
    }

    public void M(){
        Cell current = getCurrent();
        current = current.next(getDirection());
        setCurrent(current);
    }

    public boolean[][] getField(){
        return FIELD;
    }

}