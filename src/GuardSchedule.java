import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class GuardSchedule {
    private static final int DAY_MIN = 0;
    public static Shift[][] guardSchedule = new Shift[7][3];

    private static Guard shay = new Guard("Shay Dahan", new Date());
    private static Guard guy = new Guard("Guy Buchbut", new Date());
    private static Guard adi = new Guard("Adi Drori", new Date());
    private static Guard yarin = new Guard("Yarin Avisror", new Date());
    private static Guard tomer = new Guard("Tomer Barshishat", new Date());
    private static Guard itay = new Guard("Itay Shavit", new Date());

    private static Guard[] guards = { shay, guy, adi, yarin, tomer, itay };
    private static final int guardAverageShifts = 5;

    public GuardSchedule() {
        initShifts();
        List<String> shifts = Arrays.asList("0,0", "1,1", "2,1", "2,2", "3,0", "4,2", "5,0", "5,2", "6,0", "6,2");
        shay.enterShifts(shifts);
        shifts = Arrays.asList("0,0", "0,1", "0,2", "1,0", "2,1", "2,2", "3,0", "3,1", "3,2", "4,0", "4,1", "4,2");
        guy.enterShifts(shifts);
        shifts = Arrays.asList("3,0", "3,1", "4,0", "4,1");
        adi.enterShifts(shifts);
        shifts = Arrays.asList("0,1", "1,2", "2,1", "3,0", "3,1", "3,2", "4,1", "5,1", "5,2", "6,0");
        yarin.enterShifts(shifts);
        itay.enterShifts(shifts);
        shifts = Arrays.asList("0,1", "0,2", "1,0", "1,1", "1,2", "2,0", "2,1", "2,2", "3,0", "3,1", "5,0", "5,2",
                "6,0", "6,1", "6,2");
        tomer.enterShifts(shifts);
    }

    private boolean canWorkDay(Guard guard, int day, int shift) {

        // checking if the guard already working that day
        boolean canWork = false;
        switch (shift) {
            // first shift of that day: can work
            case 0:
                canWork = true;
                break;

            // second shift: need to check if didn't work shift before
            case 1:
                if (!guardSchedule[day][0].getGuard().equals(guard)) {
                    canWork = true;
                }
                break;

            // third shift: need to check two shifts before
            case 2:
                if (!guardSchedule[day][0].getGuard().equals(guard) &&
                        !guardSchedule[day][1].getGuard().equals(guard)) {
                    canWork = true;
                }
                break;
            default:
                break;
        }
        return canWork;
    }

    // there is a case when an guard worked a night shift
    // and cant work the morning after
    private boolean noOverLap(Guard guard, int day, int shiftType) {
        // checking if morning shift
        if (shiftType == 0) {
            int dayBefore = day - 1;
            // checking if out of bounds
            if (dayBefore < DAY_MIN)
                return true;
            Shift prevShift = guardSchedule[dayBefore][2]; // night shift
            // checking if the guard has worked the night shift
            if (prevShift.getGuard() != null && prevShift.getGuard().equals(guard))
                return false;
        }
        return true;
    }

    private boolean solve(int day, int shift, Shift[][] guardSchedule) throws StackOverflowError {
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
        List<Guard> candidates = getCandidates(day, shift);
        Random r = new Random();
        int workerIndex = r.nextInt(candidates.size());
        // choosing random candidate
        Guard curGuard = candidates.get(workerIndex);
        // checking if guard can work that shift
        if (canWork(curGuard, day, shift)) {
            Shift curShift = guardSchedule[day][shift];
            curShift.setGuard(curGuard); // setting curGuard to curShift
            curGuard.setWorkingShifts(curGuard.getWorkingShifts() + 1);
            // solve the problem for the next shift
            if (solve(day, ++shift, guardSchedule)) {
                return true;
            }
        } else {
            // that guard cant work that shift: try next guard
            return solve(day, shift, guardSchedule);
        }
        return false;
    }

    private List<Guard> getCandidates(int day, int shift) {
        List<Guard> candidates = new ArrayList<>();
        for (int i = 0; i < guards.length; i++) {
            Guard curGuard = guards[i];
            if (curGuard.canWorkShift(day, shift)) {
                candidates.add(curGuard);
            }
        }
        return candidates;
    }

    private boolean canWork(Guard guard, int day, int shift) {
        return (canWorkDay(guard, day, shift) && noOverLap(guard, day, shift)
                && guard.getWorkingShifts() <= guardAverageShifts);
    }

    // there is a case where no one marked that he can work a specific shift
    // problem can't be solved
    private boolean noOneCanWorkShift(int day, int shift) {
        boolean[] canWorkArr = new boolean[guards.length];
        for (int i = 0; i < guards.length; i++) {
            Guard curGuard = guards[i];
            canWorkArr[i] = curGuard.getWorkSchedule()[day][shift];
        }
        for (int i = 0; i < canWorkArr.length; i++) {
            if (canWorkArr[i])
                return false;
        }
        return true;
    }

    public void solveProblem() {
        try {
            if (solve(0, 0, guardSchedule)) {
                // printSchedule();
                // printGuards();
            } else {
                System.out.println("There is no solution");
            }
        } catch (StackOverflowError e) {
            System.out.println("No available solution");
            initShifts();
            initGuards();
            solveProblem();
        }
    }

    public void printSchedule() {

        for (int i = 0; i < guardSchedule.length; i++) {
            System.out.println("--- Day: " + Constants.days.get(i) + " ---");
            for (int j = 0; j < guardSchedule[0].length; j++) {
                System.out.println("--- " + Constants.shiftType.get(j) + " Shift ---");
                Shift curShift = guardSchedule[i][j];
                System.out.println(curShift);
            }
            System.out.println();
        }
    }

    private void initGuards() {
        for (int i = 0; i < guards.length; i++) {
            guards[i].setWorkingShifts(0);
        }
    }

    private void initShifts() {
        for (int i = 0; i < guardSchedule.length; i++) {
            for (int j = 0; j < guardSchedule[0].length; j++) {
                guardSchedule[i][j] = new Shift(i, j);
            }
        }
    }

    public void printGuards() {
        System.out.println("Amashim:");
        for (int i = 0; i < guards.length; i++) {
            System.out.println(guards[i]);
        }
    }

    public static void main(String[] args) {
        GuardSchedule gs = new GuardSchedule();
        gs.solveProblem();
    }
}
