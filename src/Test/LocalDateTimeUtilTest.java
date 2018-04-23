package Test;

import lab2.CalendarDate;
import lab3.LocalDateTimeUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class LocalDateTimeUtilTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class lab3.LocalDateTimeUtil tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class lab3.LocalDateTimeUtil tests end! Are you satisfied?");
    }

    @Test
    public void testToLocalDateTime() {
//        TEST EXIST
        CalendarDate cd = new CalendarDate(2016, 2, 29);
        LocalDateTime dateTime = LocalDateTimeUtil.toLocalDateTime(cd);
        LocalDateTime dt = LocalDateTime.of(2016, 2, 29, 0, 0);
        boolean isEqual = dateTime.isEqual(dt);
        assertTrue(isEqual);
//        TEST NULL
        LocalDateTime dateTime2 = LocalDateTimeUtil.toLocalDateTime(null);
        assertNull(dateTime2);
    }

    @Test
    public void testToLocalDate() {
        //        TEST EXIST
        CalendarDate cd = new CalendarDate(2016, 2, 29);
        LocalDate date = LocalDateTimeUtil.toLocalDate(cd);
        LocalDate dt = LocalDate.of(2016, 2, 29);
        boolean isEqual = date.isEqual(dt);
        assertTrue(isEqual);
        //        TEST NULL
        LocalDate date2 = LocalDateTimeUtil.toLocalDate(null);
        assertNull(date2);

    }
}
