package Test; /**
 * Created by luke1998 on 2018/4/15.
 */

import lab3.ToDo_item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ToDo_itemTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class lab3.ToDo_item tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class lab3.ToDo_item tests end! Are you satisfied?");
    }

    @Test
    public void testGetToDoItemTrue() {
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.of(2018,4,20,0,0), LocalDateTime.of(2018,4,20,0,0), "Sleep");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.of(2018,4,20,0,0), LocalDateTime.of(2018,4,20,0,0), "Sleep");
        assertEquals(toDoItem1, toDoItem2);
    }

    @Test
    public void testGetToDoItemFalse() {
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Sleep");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Getup");
        assertNotEquals(toDoItem1, toDoItem2);
    }

    @Test
    public void testgetThings2DoTrue() {
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Sleep");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Sleep");
        String str1 = toDoItem1.getThings2Do();
        String str2 = toDoItem2.getThings2Do();
        assertEquals(str1, str2);
    }

    @Test
    public void testgetThings2DoFalse() {
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Sleep");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.now(), LocalDateTime.now(), "Getup");
        String str1 = toDoItem1.getThings2Do();
        String str2 = toDoItem2.getThings2Do();
        assertNotEquals(str1, str2);
    }

}
