package laboratory.plugin.task.robot;


public abstract class AbstractRobot implements Robot {

    private Direction direction;

    private Cell current;
    private final Cell start;
    private final Cell target;

    public AbstractRobot(Cell start, Cell target){
        this.start = start;
        this.target = target;
        reset();
    }

    public void reset() {
        direction = Direction.RIGHT;
        current = start;    	
    }

    public Direction getDirection(){
        return direction;
    }

    public Cell getCurrent(){
        return current;
    }
    
    public Cell getStart() {
        return start;
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