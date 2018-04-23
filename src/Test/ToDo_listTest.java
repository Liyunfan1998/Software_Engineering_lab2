package Test; /**
 * Created by luke1998 on 2018/4/15.
 */

import lab3.FileOperation;
import lab3.ToDo_item;
import lab3.ToDo_list;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class ToDo_listTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("Class lab3.ToDo_list tests begin! Good luck!");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Class lab3.ToDo_list tests end! Are you satisfied?");
    }

    @Test
    public void testGetToDoItemsContainsDT() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsContainsDT(LocalDateTime.now());
        assertEquals((new ArrayList<ToDo_item>()).size(), toDoItems.size());
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsContainsDT(LocalDateTime.of(2018, 4, 15, 21, 0));

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn.size(), toDoItems.size());

    }

    @Test
    public void testGetToDoItemsMatchesStartEnd() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsMatchesStartEnd(LocalDateTime.now(), LocalDateTime.now());
        assertEquals((new ArrayList<ToDo_item>()).size(), toDoItems.size());
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsMatchesStartEnd(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59));

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn.size(), toDoItems.size());
    }

    @Test
    public void testGetToDoItemsMatchesStr() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsMatchesStr("Sleep");
        assertEquals((new ArrayList<ToDo_item>()).size(), toDoItems.size());
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsMatchesStr("Sleep");

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn.size(), toDoItems.size());

    }

    @Test
    public void testGetToDoItemsBetweenStartEnd() {
        ToDo_list toDoList = new ToDo_list();
        ArrayList<ToDo_item> toDoItems = toDoList.getToDoItemsMatchesStr("Sleep");
        assertEquals((new ArrayList<ToDo_item>()).size(), toDoItems.size());
        toDoList.addToDoItem(new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep"));
        toDoItems = toDoList.getToDoItemsBetweenStartEnd(LocalDateTime.of(2018, 4, 15, 19, 59), LocalDateTime.of(2018, 4, 15, 22, 59));

        ArrayList<ToDo_item> rightReturn = new ArrayList<>();
        rightReturn.add(
                new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                        LocalDateTime.of(2018, 4, 15, 21, 59),
                        "Sleep"));
        assertEquals(rightReturn.size(), toDoItems.size());
    }

    @Test
    public void testDeleteToDoItem() {
        ToDo_list toDoList = new ToDo_list();
        ToDo_item toDoItem = new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep");
        toDoList.addToDoItem(toDoItem);

        toDoList.deleteToDoItem(toDoItem);
        assertEquals((new ToDo_list()), toDoList);

    }

    @Test
    public void testAddToDoItem() {
        ToDo_list toDoList = new ToDo_list();
        ToDo_item toDoItem = new ToDo_item(LocalDateTime.of(2018, 4, 15, 20, 59),
                LocalDateTime.of(2018, 4, 15, 21, 59), "Sleep");
        toDoList.addToDoItem(toDoItem);
        ArrayList<ToDo_item> toDoItems = new ArrayList<ToDo_item>();
        toDoItems.add(toDoItem);
        ToDo_list add = new ToDo_list(toDoItems);
        assertEquals(add, toDoList);
    }

    @Test
    public void testSortItemsByStartTime() {
        ToDo_list toDoList = new ToDo_list();
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.of(2018, 4, 17, 0, 0),
                LocalDateTime.of(2018, 4, 17, 0, 0), "Sleep");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.of(2018, 4, 16, 0, 0),
                LocalDateTime.of(2018, 4, 16, 0, 0), "Sleep");
        ToDo_item toDoItem3 = new ToDo_item(LocalDateTime.of(2018, 4, 15, 0, 0),
                LocalDateTime.of(2018, 4, 15, 0, 0),
                "Sleep");
        toDoList.addToDoItem(toDoItem1);
        toDoList.addToDoItem(toDoItem2);
        toDoList.addToDoItem(toDoItem3);
        toDoList.sortItemsByStartTime();
        ArrayList<ToDo_item> toDoItems = new ArrayList<ToDo_item>();
//        toDoItem3,toDoItem2,toDoItem1 是按照start time 顺序排序的
        toDoItems.add(toDoItem3);
        toDoItems.add(toDoItem2);
        toDoItems.add(toDoItem1);
        ToDo_list add = new ToDo_list(toDoItems);
        assertEquals(add, toDoList);
    }

    @Test
//    tool method --------- isSubstring()
    public void testIsSubstring() {
        assertTrue(ToDo_list.isSubstring("aaa", "aaab"));
        assertFalse(ToDo_list.isSubstring("aaa", "aab"));
    }

    @Test
    public void testLoadListFromFile() {
//loadListFromFile() 的file 本来是json文件目录下的ToDoList.json，
// 但是由于这个文件可以动态改变，所以我们现在包装一下loadListFromFile()
// 使得file变成Test目录下的ToDoList.json
// 注明：如果文件不存在那么将创建新文件，
// parse Json， 这是唯一一个程序崩溃的可能性
        ToDo_list toDo_list = new ToDo_list();
//        String path = ToDo_listTest.class.getResource("ToDoList.json").getPath();
//        System.out.print(path);
//        toDo_list.createFile("../src/json/ToDoList.json", "{\"ToDoList\": []}");
        toDo_list.loadListFromFile();
        toDo_list.sortItemsByStartTime();
        ArrayList<ToDo_item> toDoItems = new ArrayList<ToDo_item>();
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.of(2018, 3, 17, 9, 0),
                LocalDateTime.of(2018, 3, 17, 9, 0), "Thing to Do");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.of(2018, 4, 16, 20, 0),
                LocalDateTime.of(2018, 4, 16, 23, 0), "Sleep");
        toDoItems.add(toDoItem1);
        toDoItems.add(toDoItem2);
        ToDo_list ready = new ToDo_list(toDoItems);
        ready.sortItemsByStartTime();
        assertEquals(ready, toDo_list);
        FileOperation.delFile("src/json/ToDoList.json");
        toDo_list.loadListFromFile();
        assertEquals((new ToDo_list()), toDo_list);
        fileRedo();
    }

    @Test
    public void testSaveListAsFile() {
        ArrayList<ToDo_item> toDoItems = new ArrayList<ToDo_item>();
        ToDo_item toDoItem1 = new ToDo_item(LocalDateTime.of(2018, 3, 17, 9, 0),
                LocalDateTime.of(2018, 3, 17, 9, 0), "Thing to Do");
        ToDo_item toDoItem2 = new ToDo_item(LocalDateTime.of(2018, 4, 16, 20, 0),
                LocalDateTime.of(2018, 4, 16, 23, 0), "Sleep");
        ToDo_item toDoItem3 = new ToDo_item(LocalDateTime.of(2018, 4, 22, 20, 0),
                LocalDateTime.of(2018, 4, 22, 23, 0), "Sleep");
        toDoItems.add(toDoItem1);
        toDoItems.add(toDoItem2);
        toDoItems.add(toDoItem3);
        ToDo_list ready = new ToDo_list(toDoItems);
        ready.sortItemsByStartTime();
        ready.saveListAsFile();

        ToDo_list toDo_list = new ToDo_list();
        toDo_list.loadListFromFile();
        toDo_list.sortItemsByStartTime();
        assertEquals(ready, toDo_list);
        fileRedo();
    }


    private void fileRedo() {
        try {
            FileOperation.writeFileContent("src/json/ToDoList.json", "{\"ToDoList\":[\n" +
                    "  {\n" +
                    "  \"start\": \"2018-03-17T09:00\",\n" +
                    "  \"end\": \"2018-03-17T09:00\",\n" +
                    "  \"str\": \"Thing to Do\"\n" +
                    " },\n" +
                    "  {\n" +
                    "  \"start\": \"2018-04-16T20:00\",\n" +
                    "  \"end\": \"2018-04-16T23:00\",\n" +
                    "  \"str\": \"Sleep\"\n" +
                    " }\n" +
                    "]}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
