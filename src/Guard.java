import java.util.Date;

public class Guard extends Worker {
    public Guard(String name, Date entryDate, boolean[][] workSchedule) {
        super(name, new Date(), workSchedule);
    }

    public Guard(String name, Date entryDate) {
        super(name, new Date(), new boolean[7][3]);
    }

}
