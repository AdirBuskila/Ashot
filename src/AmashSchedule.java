import java.util.Date;

public class AmashSchedule {

    public static final int DAY_MIN = 0;
    public static final int DAY_MAX = 6;
    public static final int SHIFT_MIN = 0;
    public static final int SHIFT_MAX = 2;

    private static boolean[][] workSchedule = {
            { true, false, false }, // sunday
            { false, true, false }, // monday
            { true, false, false }, // thursday
            { false, true, false }, // wednesday
            { true, false, false }, // thursday
            { false, true, false }, // friday
            { true, false, false } // saturday
    };
    private static boolean[][] workSchedule1 = {
            { false, true, true }, // sunday
            { true, false, true }, // monday
            { false, true, true }, // thursday
            { true, false, true }, // wednesday
            { false, true, true }, // thursday
            { true, false, true }, // friday
            { false, true, true } // saturday

    };
    public static Shift[][] amashSchedule = {
            { new Shift(0, 0), new Shift(0, 1), new Shift(0, 2), },
            { new Shift(1, 0), new Shift(1, 1), new Shift(1, 2), },
            { new Shift(2, 0), new Shift(2, 1), new Shift(2, 2), },
            { new Shift(3, 0), new Shift(3, 1), new Shift(3, 2), },
            { new Shift(4, 0), new Shift(4, 1), new Shift(4, 2), },
            { new Shift(5, 0), new Shift(5, 1), new Shift(5, 2), },
            { new Shift(6, 0), new Shift(6, 1), new Shift(6, 2), },
    };

    public static Amash daniel = new Amash("Daniel Mashid", new Date(), workSchedule1);
    public static Amash nir = new Amash("Nir Rosh", new Date(), workSchedule);
    public static Amash shani = new Amash("Shani Sampson", new Date(), workSchedule1);
    public static Amash roie = new Amash("Roie Hori", new Date(), workSchedule);
    public static Amash adir = new Amash("Adir Buskila", new Date(), workSchedule1);

    public static Amash[] amashim = { daniel, nir, shani, roie, adir };
    public static final int amashimMaxIndex = amashim.length - 1;
    public static final int amashimAverageShifts = 6;

    public boolean canWorkDay(Amash amash, int day, int shift) {
        // checking if the amash marked that he can work that shift
        if (amash.getWorkSchedule()[day][shift]) {
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
        return false;
    }

    // there is a case when an amash worked a night shift
    // and cant work the morning after
    public boolean noOverLap(Amash amash, int day, int shiftType) {
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

    public void printSchedule() {
        for (int i = 0; i < amashSchedule.length; i++) {
            System.out.println("--- DAY " + i + " ---");
            for (int j = 0; j < amashSchedule[0].length; j++) {
                System.out.println("--- SHIFT " + j + " ---");
                Shift curShift = amashSchedule[i][j];
                System.out.println(curShift);
            }
            System.out.println();
        }
    }

    public boolean solve(int day, int shift, int workerIndex, Shift[][] amashSchedule) {
        System.out.println("---new stack---");
        System.out.println("day = " + day);
        System.out.println("shift = " + shift);
        // base-case: all the shifts are filled
        if (day == 6 && shift == 3)
            return true;
        // shift out of bounds: set day++, shift 0
        if (shift == 3) {
            day++;
            shift = 0;
        }
        // worker index out of bounds: set to starting index (may be random)
        if (workerIndex > amashimMaxIndex)
            workerIndex = 0;
        // no one can work: return false
        if (noOneCanWorkShift(day, shift))
            return false;

        Amash curAmash = amashim[workerIndex];
        // checking if amash can work that shift
        if (canWork(curAmash, day, shift)) {
            Shift curShift = amashSchedule[day][shift];
            curShift.setAmash(curAmash); // setting curAmash to curShift
            curAmash.setWorkingShifts(curAmash.getWorkingShifts() + 1);
            // solve the problem for the next shift
            if (solve(day, ++shift, ++workerIndex, amashSchedule)) {
                return true;
            }
        } else {
            // that amash cant work that shift: try next amash
            return solve(day, shift, workerIndex + 1, amashSchedule);
        }
        return false;
    }

    public boolean canWork(Amash amash, int day, int shift) {
        return (canWorkDay(amash, day, shift) && noOverLap(amash, day, shift)
                && amash.getWorkingShifts() <= amashimAverageShifts);
    }

    // there is a case where no one marked that he can work a specific shift
    // problem can't be solved
    public boolean noOneCanWorkShift(int day, int shift) {
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
        if (solve(0, 0, 0, amashSchedule)) {
            printSchedule();
            printAmashim();
        } else {
            System.out.println("There is no solution");
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
