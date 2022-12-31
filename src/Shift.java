public class Shift {
    private int day;
    private int shiftType;
    private Worker amash;
    private Worker guard;
    private boolean filled = false;

    public Shift() {
        this.day = 0;
        this.shiftType = 0;
        this.amash = null;
        this.guard = null;

    }

    public Shift(int day, int shiftType) {
        this.day = day;
        this.shiftType = shiftType;
        this.amash = new Amash("Amash", null, null);
        this.guard = null;

    }

    public Shift(int day, int shiftType, Amash amash) {
        this.day = day;
        this.shiftType = shiftType;
        this.amash = amash;
        this.guard = null;

    }

    public Shift(int day, int shiftType, Worker amash, Worker guard) {
        this.day = day;
        this.shiftType = shiftType;
        this.amash = amash;
        this.guard = guard;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getShiftType() {
        return this.shiftType;
    }

    public void setShiftType(int shiftType) {
        this.shiftType = shiftType;
    }

    public Worker getAmash() {
        return this.amash;
    }

    public void setAmash(Worker amash) {
        this.amash = amash;
        if (this.guard != null)
            this.filled = true;
    }

    public Worker getGuard() {
        return this.guard;
    }

    public void setGuard(Worker guard) {
        this.guard = guard;
        if (this.amash != null)
            this.filled = true;
    }

    public boolean isFilled() {
        return this.filled;
    }

    public boolean getFilled() {
        return this.filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    @Override
    public String toString() {
        return "Day: " + day + ", Shift: " + shiftType + "\n" +
                "Amash: " + amash;
    }

}
