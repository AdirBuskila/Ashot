import java.util.Date;
import java.util.List;

public class Worker {

    private String name;
    private Date entryDate; // day of staring in ashot
    private boolean[][] workSchedule;
    private int workingShifts = 0;

    public int getWorkingShifts() {
        return this.workingShifts;
    }

    public void setWorkingShifts(int workingShifts) {
        this.workingShifts = workingShifts;
    }

    public Worker(String name, Date entryDate, boolean[][] workSchedule) {
        this.name = name;
        this.entryDate = entryDate;
        this.workSchedule = workSchedule;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getEntryDate() {
        return this.entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public boolean[][] getWorkSchedule() {
        return this.workSchedule;
    }

    public void setWorkSchedule(boolean[][] workSchedule) {
        this.workSchedule = workSchedule;
    }

    public boolean equals(Worker worker) {
        return worker.getName().equals(getName());
    }

    public void enterShifts(List<String> shifts) {
        for (int i = 0; i < shifts.size(); i++) {
            String curShift = shifts.get(i);
            int day = Integer.parseInt(curShift.split("")[0]);
            int shift = Integer.parseInt(curShift.split("")[2]);
            workSchedule[day][shift] = true;
        }
    }

    public boolean canWorkShift(int day, int shift) {
        return workSchedule[day][shift];
    }

    public void printShifts() {
        System.out.print("working: ");
        for (int i = 0; i < workSchedule.length; i++) {
            for (int j = 0; j < workSchedule[0].length; j++) {
                if (workSchedule[i][j]) {
                    System.out.print(i + "," + j + " ");
                }
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Name: " + getName() + ", working: " + getWorkingShifts();
    }

}
