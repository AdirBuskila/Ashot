import java.util.HashMap;
import java.util.Map;

public class Constants {
    static Map<Integer, String> days = new HashMap<>();
    static Map<Integer, String> shiftType = new HashMap<>();

    public Constants() {
        days.put(0, "Sunday");
        days.put(1, "Monday");
        days.put(2, "Tuesday");
        days.put(3, "Wednesday");
        days.put(4, "Thursday");
        days.put(5, "Friday");
        days.put(6, "Saturday");
        shiftType.put(0, "Morning");
        shiftType.put(1, "Evening");
        shiftType.put(2, "Night");
    }

}
