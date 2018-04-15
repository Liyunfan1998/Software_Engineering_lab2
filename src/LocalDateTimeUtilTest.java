import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class LocalDateTimeUtilTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class LocalDateTimeUtil tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class LocalDateTimeUtil tests end! Are you satisfied?");
    }

    @Test
    public void testGetStartTimeValid() {
        LocalDateTime dateTime = LocalDateTime.of(2018, -2, 29, 25, 0, 0, 0);
//        System.out.println(erro messages);
        boolean actual = LocalDateTimeUtil.dateTimeValid(dateTime);
        assertEquals(true, actual);
    }
}
