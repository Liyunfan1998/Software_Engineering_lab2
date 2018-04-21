package lab2;/*
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
     * get a lab2.CalendarDate instance point to today
     *
     * @return a lab2.CalendarDate object
     */
    public static CalendarDate getToday() {
        Calendar calendar = Calendar.getInstance();
        return new CalendarDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static int getWeekOfMonth(CalendarDate calendarDate) {
        if (!isValid(calendarDate)) {
            return -1;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendarDate.getYear(), calendarDate.getMonth(), calendarDate.getDay());
        return Calendar.WEEK_OF_MONTH;
    }

    /**
     * get all dates in the same month with given date
     *
     * @param date the given date
     * @return a list of days in a whole month
     */
    public static List<CalendarDate> getDaysInMonth(CalendarDate date) {
        if (!isValid(date)) {
            // TODO: 2018/4/15
            return null;
        }
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
        if (!isValid(date)) {
            return -1;
        }
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int daysInMonth;
        if (month != 12) {
            daysInMonth = dayIndexOfYear(year, month + 1, day) - dayIndexOfYear(year, month, day);
//            System.out.println("2016-2-29 dayIndexOfYear:" + dayIndexOfYear(2016, 2, 29));
//            System.out.println("2016-2-28 dayIndexOfYear:" + dayIndexOfYear(2016, 2, 28));
//            System.out.println("2016-3-1 dayIndexOfYear:" + dayIndexOfYear(2016, 3, 1));
        } else
            daysInMonth = 31;

        return daysInMonth;
    }

    public static int getLineInPane(CalendarDate calendarDate) {
        if (!isValid(calendarDate)) {
            return -1;
        }
        int m = calendarDate.getMonth();
        int y = calendarDate.getYear();
        CalendarDate cd = new CalendarDate(y, m, 1);
        int addD = cd.getDayOfWeek() % 7;   // 4
//        int dim = lab2.DateUtil.getNumberOfDaysInMonth(calendarDate);
//        int daysInMonthADD = addD + dim;
//        int firstLineHasDays = 7 - addD;     //2
        int lineIndex = (calendarDate.getDay() + addD - 1) / 7 + 1;
//        int d = calendarDate.getDay();
//        int x = calendarDate.getDayOfWeek();
//        int wom = (d - x) / 7 + 1;
        return lineIndex;
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
                count += 31;
            case 7:
                count += 30;
            case 6:
                count += 31;
            case 5:
                count += 30;
            case 4:
                count += 31;
            case 3:
                if (isLeapYear(year)) {
                    count += 29;
                } else count += 28;
            case 2:
                count += 31;
            case 1:
                count += 0;
        }
        count += day;
        return count;
    }

    /**
     * Judge whether the input date is valid. For example, 2018-2-31 is not valid
     *
     * @param date the input date
     * @return true if the date is valid, false if the date is not valid.
     */
    public static boolean isValid(CalendarDate date) {
        if (date == null) {
            return false;
        } else if (!isLeapYear(date.getYear()) && date.getMonth() == 2 && date.getDay() == 29) {
            return false;
        }
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
            String[] arr = dateString.split("-");
            int y, m, d;
            y = Integer.parseInt(arr[0]);
            m = Integer.parseInt(arr[1]);
            d = Integer.parseInt(arr[2]);
            if (y < 0 | m < 1 | m > 100 | d < 1 | d > 100) {
                return false;
            }
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
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

}

