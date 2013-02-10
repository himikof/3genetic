package laboratory.plugin.task.robot;

public class StandardFitness {

    public double calc(Mover solution) {
        Robot r = new SimpleRobot();
        solution.restart(r);
        int steps = 200;
        int startDistance = Math.abs(r.getCurrent().x - r.getTarget().x) +
                Math.abs(r.getCurrent().y - r.getTarget().y);
        for (int i = 0; i < Robot.NUMBER_STEPS; i++) {
            solution.move();
            if (r.getCurrent().equals(r.getTarget())) {
                steps = i;
                break;
            }
        }
        int distance = Math.abs(r.getCurrent().x - r.getTarget().x) +
                Math.abs(r.getCurrent().y - r.getTarget().y);
        return -distance - (steps - 200);
    }

    private static StandardFitness ourInstance = new StandardFitness();

    public static StandardFitness getInstance() {
        return ourInstance;
    }

    private StandardFitness() {
    }
}
