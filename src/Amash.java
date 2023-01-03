import java.util.Date;

public class Amash extends Worker {
    public Amash(String name, Date entryDate, boolean[][] workSchedule) {
        super(name, new Date(), workSchedule);
    }

    public Amash(String name, Date entryDate) {
        super(name, new Date(), new boolean[7][3]);
    }
}