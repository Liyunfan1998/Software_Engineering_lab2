/*
* This class provides some utils that may help you to finish this lab.
* getToday() is finished, you can use this method to get the current date.
* The other four methods getDaysInMonth(), isValid(), isFormatted() and isLeapYear() are not finished,
* you should implement them before you use.
*
* */

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    /**
     * get a CalendarDate instance point to today
     *
     * @return a CalendarDate object
     */
    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    /**
     * get all dates in the same month with given date
     *
     * @param date the given date
     * @return a list of days in a whole month
     */
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        int y = date.getYear();
        int m = date.getMonth();
        int numberOfDaysInMonth = getNumberOfDaysInMonth(date);
        List<CalendarDate> list = new ArrayList<>();
        for (int i = 1; i <= numberOfDaysInMonth; i++) {
            list.add(new CalendarDate(y, m, i));
        }
        return list;
    }

    public static int getNumberOfDaysInMonth(CalendarDate date) {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int daysInMonth;
        if (month != 12)
            daysInMonth = dayIndexOfYear(year, month + 1, day) - dayIndexOfYear(year, month, day);
        else
            daysInMonth = 31;
        return daysInMonth;
//        List<CalendarDate> listCalendarDate  = new List<>();
    }

    public static int dayIndexOfYear(int year, int month, int day) {
        int count = 0;
        switch (month) {
            case 12:
                count += 30;
            case 11:
                count += 31;
            case 10:
                count += 30;
            case 9:
                count += 31;
            case 8:
                count += 30;
            case 7:
                count += 31;
            case 6:
                count += 31;
            case 5:
                count += 30;
            case 4:
                count += 31;
            case 3:
                count += 28;
            case 2:
                count += 31;
            case 1:
                count += 0;
        }
        count += day;
        if (isLeapYear(year)) {
            count += 1;
        }
        return count;
    }

    /**
     * Judge whether the input date is valid. For example, 2018-2-31 is not valid
     *
     * @param date the input date
     * @return true if the date is valid, false if the date is not valid.
     */
    public static boolean isValid(CalendarDate date) {
        String dateString = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();
        //String eL = "^((((1[6-9]|[2-9]\\d)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((1[6-9]|[2-9]\\d)\\d{2})-(0?[13456789]|1[012])-(0?[1-9]|[12]\\d|30))|(((1[6-9]|[2-9]\\d)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|(((1[6-9]|[2-9]\\d)(0[48]|[2468][048]|[13579][26])|((16|[2468][048]|[3579][26])00))-0?2-29-)) (20|21|22|23|[0-1]?\\d):[0-5]?\\d:[0-5]?\\d$";

        String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
        Pattern p = Pattern.compile(eL);
        Matcher m = p.matcher(dateString);
        boolean b = m.matches();
        return b;
    }

    /**
     * Judge whether the input is formatted.
     * For example, 2018/2/1 is not valid and 2018-2-1 is valid.
     *
     * @param dateString
     * @return true if the input is formatted, false if the input is not formatted.
     */
    public static boolean isFormatted(String dateString) {
        try {
            CalendarDate calendarDate = new CalendarDate(dateString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * Judge whether the input year is a leap year or not.
     * For example, year 2000 is a leap year, and 1900 is not.
     *
     * @param year
     * @return true if the input year is a leap year, false if the input is not.
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0;
    }

}

