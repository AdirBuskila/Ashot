import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class AmashSchedule {

    private static final int DAY_MIN = 0;

    public static Shift[][] amashSchedule = new Shift[7][3];

    private static Amash daniel = new Amash("Daniel Mashid", new Date());
    private static Amash nir = new Amash("Nir Rosh", new Date());
    private static Amash shani = new Amash("Shani Sampson", new Date());
    private static Amash roie = new Amash("Roie Hori", new Date());
    private static Amash adir = new Amash("Adir Buskila", new Date());

    private static Amash[] amashim = { daniel, nir, roie, adir, shani };
    private static final int amashimAverageShifts = 5;

    public AmashSchedule() {
        initShifts();
        List<String> shifts = Arrays.asList("0,0", "1,1", "2,1", "2,2", "3,0", "4,2", "5,0", "5,2", "6,0", "6,2");
        adir.enterShifts(shifts);
        shifts = Arrays.asList("0,0", "0,1", "0,2", "1,0", "2,1", "2,2", "3,0", "3,1", "3,2", "4,0", "4,1", "4,2");
        daniel.enterShifts(shifts);
        shifts = Arrays.asList("3,0", "3,1", "4,0", "4,1");
        nir.enterShifts(shifts);
        shifts = Arrays.asList("0,1", "1,2", "2,1", "3,0", "3,1", "3,2", "4,1", "5,1", "5,2", "6,0");
        roie.enterShifts(shifts);
        shifts = Arrays.asList("0,1", "0,2", "1,0", "1,1", "1,2", "2,0", "2,1", "2,2", "3,0", "3,1", "5,0", "5,2",
                "6,0", "6,1", "6,2");
        shani.enterShifts(shifts);
    }

    private boolean canWorkDay(Amash amash, int day, int shift) {

        // checking if the amash already working that day
        boolean canWork = false;
        switch (shift) {
            // first shift of that day: can work
            case 0:
                canWork = true;
                break;

            // second shift: need to check if didn't work shift before
            case 1:
                if (!amashSchedule[day][0].getAmash().equals(amash)) {
                    canWork = true;
                }
                break;

            // third shift: need to check two shifts before
            case 2:
                if (!amashSchedule[day][0].getAmash().equals(amash) &&
                        !amashSchedule[day][1].getAmash().equals(amash)) {
                    canWork = true;
                }
                break;
            default:
                break;
        }
        return canWork;
    }

    // there is a case when an amash worked a night shift
    // and cant work the morning after
    private boolean noOverLap(Amash amash, int day, int shiftType) {
        // checking if morning shift
        if (shiftType == 0) {
            int dayBefore = day - 1;
            // checking if out of bounds
            if (dayBefore < DAY_MIN)
                return true;
            Shift prevShift = amashSchedule[dayBefore][2]; // night shift
            // checking if the amash has worked the night shift
            if (prevShift.getAmash() != null && prevShift.getAmash().equals(amash))
                return false;
        }
        return true;
    }

    private boolean solve(int day, int shift, Shift[][] amashSchedule) throws StackOverflowError {
        // base-case: all the shifts are filled
        if (day == 5 && shift == 1)
            return true;
        // shift out of bounds: set day++, shift 0
        if (shift == 3) {
            day++;
            shift = 0;
        }
        // no one can work: return false
        if (noOneCanWorkShift(day, shift)) {
            System.out.println("no one can work :" + day + "," + shift);
            return false;
        }
        // getting candidates that can work current shift
        List<Amash> candidates = getCandidates(day, shift);
        Random r = new Random();
        int workerIndex = r.nextInt(candidates.size());
        // choosing random candidate
        Amash curAmash = candidates.get(workerIndex);
        // checking if amash can work that shift
        if (canWork(curAmash, day, shift)) {
            Shift curShift = amashSchedule[day][shift];
            curShift.setAmash(curAmash); // setting curAmash to curShift
            curAmash.setWorkingShifts(curAmash.getWorkingShifts() + 1);
            // solve the problem for the next shift
            if (solve(day, ++shift, amashSchedule)) {
                return true;
            }
        } else {
            // that amash cant work that shift: try next amash
            return solve(day, shift, amashSchedule);
        }
        return false;
    }

    private List<Amash> getCandidates(int day, int shift) {
        List<Amash> candidates = new ArrayList<>();
        for (int i = 0; i < amashim.length; i++) {
            Amash curAmash = amashim[i];
            if (curAmash.canWorkShift(day, shift)) {
                candidates.add(curAmash);
            }
        }
        return candidates;
    }

    private boolean canWork(Amash amash, int day, int shift) {
        return (canWorkDay(amash, day, shift) && noOverLap(amash, day, shift)
                && amash.getWorkingShifts() <= amashimAverageShifts);
    }

    // there is a case where no one marked that he can work a specific shift
    // problem can't be solved
    private boolean noOneCanWorkShift(int day, int shift) {
        boolean[] canWorkArr = new boolean[amashim.length];
        for (int i = 0; i < amashim.length; i++) {
            Amash curAmash = amashim[i];
            canWorkArr[i] = curAmash.getWorkSchedule()[day][shift];
        }
        for (int i = 0; i < canWorkArr.length; i++) {
            if (canWorkArr[i])
                return false;
        }
        return true;
    }

    public void solveProblem() {
        try {
            if (solve(0, 0, amashSchedule)) {
                // printSchedule();
                // printAmashim();
            } else {
                System.out.println("There is no solution");
            }
        } catch (StackOverflowError e) {
            System.out.println("No available solution");
            initShifts();
            initAmashim();
            solveProblem();
        }
    }

    public void printSchedule() {

        for (int i = 0; i < amashSchedule.length; i++) {
            System.out.println("--- Day: " + Constants.days.get(i) + " ---");
            for (int j = 0; j < amashSchedule[0].length; j++) {
                System.out.println("--- " + Constants.shiftType.get(j) + " Shift ---");
                Shift curShift = amashSchedule[i][j];
                System.out.println(curShift);
            }
            System.out.println();
        }
    }

    private void initAmashim() {
        for (int i = 0; i < amashim.length; i++) {
            amashim[i].setWorkingShifts(0);
        }
    }

    private void initShifts() {
        for (int i = 0; i < amashSchedule.length; i++) {
            for (int j = 0; j < amashSchedule[0].length; j++) {
                amashSchedule[i][j] = new Shift(i, j);
            }
        }
    }

    public void printAmashim() {
        System.out.println("Amashim:");
        for (int i = 0; i < amashim.length; i++) {
            System.out.println(amashim[i]);
        }
    }

    public static void main(String[] args) {
        AmashSchedule as = new AmashSchedule();
        as.solveProblem();
    }
}
