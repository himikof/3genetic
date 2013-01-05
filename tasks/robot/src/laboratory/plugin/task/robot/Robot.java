package laboratory.plugin.task.robot;

public interface Robot {

    public static final int NUMBER_STEPS = 200;
    //public static final int NUMBER_FOOD = 89;


    public boolean W();

    public void L();

    public void R();

    public void M();

    public Direction getDirection();

    public Cell getCurrent();
    
    public Cell getTarget();

    public enum Action{
        L,
        R,
        M
    }

    public static final char[] ACTION_VALUES = {'L', 'R', 'M'};

    public enum Direction{
        LEFT,
        RIGHT,
        TOP,
        BOTTOM;

        public Direction left(){
            switch(this){
                case LEFT:
                    return BOTTOM;
                case BOTTOM:
                    return RIGHT;
                case RIGHT:
                    return TOP;
                case TOP:
                    return LEFT;
            }
            return null;
        }

        public Direction right(){
            switch(this){
                case LEFT:
                    return TOP;
                case BOTTOM:
                    return LEFT;
                case RIGHT:
                    return BOTTOM;
                case TOP:
                    return RIGHT;
            }
            return null;
        }
    }

    public static class Cell {

        public int x;

        public int y;

        public Cell(int x, int y){
            this.x = x;
            this.y = y;
        }

        public Cell next(Direction d){
            switch(d){
                case LEFT:
                    return new Cell(x, (y + 31) % 32);
                case RIGHT:
                    return new Cell(x, (y + 1) % 32);
                case TOP:
                    return new Cell((x + 31) % 32, y);
                case BOTTOM:
                    return new Cell((x + 1) % 32, y);
            }
            return null;
        }
        
        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Cell))
                return false;
            Cell other = (Cell)obj;
            return this.x == other.x && this.y == other.y;
        }
        
        @Override
        public int hashCode() {
            return x ^ (17 * y);
        }
    }
}