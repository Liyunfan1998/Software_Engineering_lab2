package lab2;

import java.time.LocalDate;

/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a lab2.CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate {
    private int year;
    private int month;
    private int day;

    public CalendarDate(int year, int month, int day) {
        try {
            LocalDate localDate = LocalDate.of(year, month, day);
            this.year = year;
            this.month = month;
            this.day = day;
        } catch (Exception e) {
            return;
        }

    }

    /**
     * a constructor that can return a lab2.CalendarDate object through the given string.
     *
     * @param dateString format: 2018-3-18
     */
    public CalendarDate(String dateString) {
        String[] arr = dateString.split("-");
        int y, m, d;
        y = Integer.parseInt(arr[0]);
        m = Integer.parseInt(arr[1]);
        d = Integer.parseInt(arr[2]);
        if (y < 0 | m < 1 | m > 100 | d < 1 | d > 100)
            return;
        try {
            System.out.print("stupid");
            LocalDate localDate = LocalDate.of(y, m, d);
            setYear(y);
            setMonth(m);
            setDay(d);
        } catch (Exception e) {
            return;
        }

    }

    public String toFormatStr() {
        String m = "" + this.getMonth(), d = "" + this.getDay();
        if (this.getMonth() < 10) {
            m = "0" + this.getMonth();
        }
        if (this.getDay() < 10) {
            d = "0" + this.getDay();
        }
        return this.getYear() + "-" + m + "-" + d;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Get index of the day in a week for one date.
     * <p>
     * Don't use the existing implement like Calendar.setTime(),
     * try to implement your own algorithm.
     *
     * @return 1-7, 1 stands for Monday and 7 stands for Sunday
     */
    public int getDayOfWeek() {
        int y, m, d;
        y = this.getYear();
        m = this.getMonth();
        d = this.getDay();
        try {
            LocalDate.of(y, m, d);
            if (m == 1 || m == 2) {
                m += 12;
                y--;
            }
            int dayOfWeek = 1 + (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
            return dayOfWeek;
        } catch (Exception e) {
            return -1;
        }

    }

    @Override
    public boolean equals(Object o) {
        try {
            CalendarDate cd = (CalendarDate) o;
            int dy = this.getYear() - cd.getYear();
            int dm = this.getMonth() - cd.getMonth();
            int dd = this.getDay() - cd.getDay();
            if (dy == 0 & dm == 0 & dd == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}
