package laboratory.plugin.task.ant;

public class StandardFitness {

    public double calk(Mover solution) {
        solution.restart(new SimpleAnt());
        int count = 0;
        int lem = 0;
        for (int i = 0; i < Ant.NUMBER_STEPS; i++) {
            if (solution.move()) {
                count++;
                lem = i;
            }
            if (count == Ant.NUMBER_FOOD) {
                break;
            }
        }
        return (count - lem * 1.0 / Ant.NUMBER_STEPS);
    }

    private static StandardFitness ourInstance = new StandardFitness();

    public static StandardFitness getInstance() {
        return ourInstance;
    }

    private StandardFitness() {
    }
}
