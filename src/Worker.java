import java.util.Date;

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

    @Override
    public String toString() {
        return "Name: " + getName() + ", working: " + getWorkingShifts();
    }

}
