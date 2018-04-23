package lab3;

import lab2.CalendarDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class LocalDateTimeUtil {
    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDateTime toLocalDateTime(CalendarDate calendarDate) {
        try {
            String str = calendarDate.toFormatStr() + "T00:00";
            return LocalDateTime.parse(str, dateTimeFormatter);
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        return null;
    }

    public static LocalDate toLocalDate(CalendarDate calendarDate) {
        try {
            return LocalDate.parse(calendarDate.toFormatStr(), dateFormatter);
        } catch (Exception e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
        return null;
    }
}
