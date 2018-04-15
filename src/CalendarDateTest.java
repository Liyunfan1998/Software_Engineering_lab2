import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CalendarDateTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDate tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDate tests end! Are you satisfied?");
    }

    @Test
    public void testGetDayOfWeekTrue() {
        CalendarDate date = new CalendarDate(2018, 4, 2);
        int actual = date.getDayOfWeek();
        assertEquals(1, actual);
    }

    @Test
    public void testGetDayOfWeekFalse() {
        CalendarDate date1 = new CalendarDate(2018, 4, 2);
        int actual1 = date1.getDayOfWeek();
        assertNotEquals(6, actual1);

        CalendarDate date2 = new CalendarDate(2018, 2, 29);
        int actual2 = date2.getDayOfWeek();
//        System.out.println(DateUtil.isValid(date2));
        assertEquals(-1, actual2);

    }
}