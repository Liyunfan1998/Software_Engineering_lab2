/*
* Start here!
*
* */

public class Main {
    public static void main(String[] args) {
        /*
        System.out.println(DateUtil.getCurrentYear());
        System.out.println(DateUtil.getCurrentMonth());
        System.out.println(DateUtil.getCurrentDay());
        */
        //todo  We will run this class to test your codes.
        CalendarDate calendarDate = new CalendarDate("2018-2-20");
        System.out.println(calendarDate.getDayOfWeek());
        System.out.println(DateUtil.getNumberOfDaysInMonth(calendarDate));
//        List<CalendarDate> list = DateUtil.getDaysInMonth(calendarDate);

//        int x = DateUtil.getDaysInMonth(calendarDate);

//        int listSize = list.size();
//        for (int i = 0; i < listSize; i++) {
//            System.out.println(list.get(i).getDay());
//        }
//        System.out.println(x);
//        DateUtil.isFormatted("2018-3-20");
        System.out.println(DateUtil.isFormatted("18-3-20"));
    }
}
