/**
 * Created by luke1998 on 2018/4/15.
 */

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class ToDo_listTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class CalendarDate tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class CalendarDate tests end! Are you satisfied?");
    }

    @Test
    public void testGetToDoItemsContainsDT() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsContainsDT(LocalDateTime.now());
        assertEquals(new ToDo_list(), toDoItems);
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsContainsDT(LocalDateTime.of(2018, 4, 15, 21, 00));

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn, toDoItems);

    }

    @Test
    public void testGetToDoItemsMatchesStartEnd() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsMatchesStartEnd(LocalDateTime.now(), LocalDateTime.now());
        assertEquals(new ToDo_list(), toDoItems);
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsMatchesStartEnd(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59));

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn, toDoItems);
    }

    @Test
    public void testGetToDoItemsMatchesStr() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsMatchesStr("Sleep");
        assertEquals(new ToDo_list(), toDoItems);
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsMatchesStr("Sleep");

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn, toDoItems);

    }

    @Test
    public void testDeleteToDoItem() {
        ToDo_list toDoList = new ToDo_list();
        ToDo_item toDoItem = new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep");
        toDoList.addToDoItem(toDoItem);

        toDoList.deleteToDoItem(toDoItem);
        assertEquals(new ToDo_list(), toDoList);

    }
}
