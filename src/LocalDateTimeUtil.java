import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class LocalDateTimeUtil {
    public static boolean dateTimeValid(LocalDateTime dateTime) {
        // TODO: 2018/4/15
        return true;
    }

    public static LocalDateTime toLocalDateTime(CalendarDate calendarDate) {
        try {
            return LocalDateTime.parse(calendarDate.toFormatStr());
        } catch (Exception e) {
            System.out.println("[ERROR] "+e.getMessage());
        }
        return null;
    }

    public static LocalDate toLocalDate(CalendarDate calendarDate) {
        try {
            return LocalDate.parse(calendarDate.toFormatStr());
        } catch (Exception e) {
            System.out.println("[ERROR] "+e.getMessage());
        }
        return null;
    }
}
