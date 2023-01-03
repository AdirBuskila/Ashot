public class Ashot {
    private AmashSchedule amashSchedule;
    private AmashSchedule guarSchedule;

    public Ashot() {
        this.amashSchedule = new AmashSchedule();
    }

    public void createSchedule() {
        amashSchedule.solveProblem();
    }

}
