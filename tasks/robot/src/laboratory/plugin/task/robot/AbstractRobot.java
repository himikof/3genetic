package laboratory.plugin.task.robot;

public abstract class AbstractRobot implements Robot {

    private Direction direction;

    private Cell current;
    private final Cell target;

    public AbstractRobot(Cell start, Cell target){
        direction = Direction.RIGHT;
        current = start;
        this.target = target;
    }

    public Direction getDirection(){
        return direction;
    }

    public Cell getCurrent(){
        return current;
    }
    
    public Cell getTarget() {
        return target;
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