package laboratory.plugin.task.ant;

public abstract class AbstractAnt implements Ant{

    private Direction direction;

    private Cell current;

    public AbstractAnt(){
        direction = Direction.RIGHT;
        current = new Cell(0, 0);
    }

    public Direction getDirection(){
        return direction;
    }

    public Cell getCurrent(){
        return current;
    }

    public void L(){
        direction = direction.left();
    }

    public void R(){
        direction = direction.right();
    }

    protected void setCurrent(Cell nCur){
        current = nCur;
    }
}