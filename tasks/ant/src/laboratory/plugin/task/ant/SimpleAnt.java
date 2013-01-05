package laboratory.plugin.task.ant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.jar.JarFile;
import java.util.Arrays;

public class SimpleAnt extends AbstractAnt{

    private static boolean[][] FIELD = null;

    private boolean[] ret;

    public static boolean[][] readField(JarFile jar){
        boolean[][] field = new boolean[32][32];
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(jar.getInputStream(jar.getEntry("field.in"))));
            for(int i = 0;i < 32;i ++){
                String s = in.readLine();
                for(int j = 0;j < 32;j ++){
                    field[i][j] = s.charAt(j) == '*';
                }
            }
        } catch(IOException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return field;
    }

    public static void setField(boolean[][] field){
        FIELD = new boolean[field.length][];
        for(int i = 0;i < field.length;i ++){
            FIELD[i] = Arrays.copyOf(field[i], field[i].length);
        }
    }

    private boolean[][] field;

    public SimpleAnt(){
        field = new boolean[FIELD.length][];
        for(int i = 0;i < field.length;i++){
            field[i] = new boolean[FIELD[i].length];
            System.arraycopy(FIELD[i], 0, field[i], 0, field[i].length);
        }
        ret = new boolean[1];
    }

    public boolean[] F(){
        Cell nextCell = getCurrent().next(getDirection());
        ret[0] = field[nextCell.x][nextCell.y];
        return ret;
    }

    public void M(){
        Cell current = getCurrent();
        current = current.next(getDirection());
        setCurrent(current);
        field[current.x][current.y] = false;
    }

    public boolean[][] getField(){
        return field;
    }

}