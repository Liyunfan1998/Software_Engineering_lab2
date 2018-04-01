/**
 * We have finished part of this class yet, you should finish the rest.
 * 1. A constructor that can return a CalendarDate object through the given string.
 * 2. A method named getDayOfWeek() that can get the index of a day in a week.
 */
public class CalendarDate {
    private int year;
    private int month;
    private int day;

    public CalendarDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * a constructor that can return a CalendarDate object through the given string.
     *
     * @param dateString format: 2018-3-18
     */
    public CalendarDate(String dateString) {
        String[] arr = dateString.split("-");
        int y, m, d;
        y = Integer.parseInt(arr[0]);
        m = Integer.parseInt(arr[1]);
        d = Integer.parseInt(arr[2]);
        setYear(y);
        setMonth(m);
        setDay(d);
    }

    int getYear() {
        return year;
    }

    void setYear(int year) {
        this.year = year;
    }

    int getMonth() {
        return month;
    }

    void setMonth(int month) {
        this.month = month;
    }

    int getDay() {
        return day;
    }

    void setDay(int day) {
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
    int getDayOfWeek() {
        int y, m, d;
        y = this.getYear();
        m = this.getMonth();
        d = this.getDay();
        if (m == 1 || m == 2) {
            m += 12;
            y--;
        }
        int dayOfWeek = 1 + (d + 2 * m + 3 * (m + 1) / 5 + y + y / 4 - y / 100 + y / 400) % 7;
        return dayOfWeek;
    }
}
