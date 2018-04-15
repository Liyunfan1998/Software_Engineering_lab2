/**
 * Created by luke1998 on 2018/4/15.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CalendarDatePlusPlusTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDatePlus tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDatePlus tests end! Are you satisfied?");
    }

    @Test
    public void testGetDayOfWeekTrue() {
        CalendarDatePlus date = new CalendarDatePlus(2018, 4, 2);
        int actual = date.getDayOfWeek();
        assertEquals(1, actual);
    }

    @Test
    public void testGetDayOfWeekFalse() {
        CalendarDatePlus date1 = new CalendarDatePlus(2018, 4, 2);
        int actual1 = date1.getDayOfWeek();
        assertNotEquals(6, actual1);

        CalendarDatePlus date2 = new CalendarDatePlus(2018, 2, 29);
        int actual2 = date2.getDayOfWeek();
        assertEquals(-1, actual2);

    }
}
