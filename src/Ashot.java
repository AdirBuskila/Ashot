public class Ashot {
    private AmashSchedule amashSchedule;
    private GuardSchedule guarSchedule;
    private Shift[][] ashotSchedule = new Shift[7][3];
    private Constants constants = new Constants();

    public Ashot() {
        initShifts();
        this.amashSchedule = new AmashSchedule();
        this.guarSchedule = new GuardSchedule();
        amashSchedule.solveProblem();
        guarSchedule.solveProblem();
        mergeSchedule();
        printSchedule();
    }

    private void initShifts() {
        for (int i = 0; i < ashotSchedule.length; i++) {
            for (int j = 0; j < ashotSchedule[0].length; j++) {
                ashotSchedule[i][j] = new Shift(i, j);
            }
        }
    }

    private void mergeSchedule() {
        Shift[][] amash = AmashSchedule.amashSchedule;
        Shift[][] guard = GuardSchedule.guardSchedule;

        for (int i = 0; i < guard.length; i++) {
            for (int j = 0; j < guard[0].length; j++) {
                ashotSchedule[i][j].setAmash(amash[i][j].getAmash());
                ashotSchedule[i][j].setGuard(guard[i][j].getGuard());
            }
        }
    }

    public void printSchedule() {

        for (int i = 0; i < ashotSchedule.length; i++) {
            System.out.println("--- Day: " + Constants.days.get(i) + " ---");
            for (int j = 0; j < ashotSchedule[0].length; j++) {
                System.out.println("--- " + Constants.shiftType.get(j) + " Shift ---");
                Shift curShift = ashotSchedule[i][j];
                System.out.println(curShift);
            }
            System.out.println();
        }
    }
}
