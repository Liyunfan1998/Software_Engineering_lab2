import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class ToDo_list {
    ArrayList<ToDo_item> toDoItemArrayList = new ArrayList<>();

    public ToDo_list() {
    }

    //    返回某个dateTime落在若干个toDoItem的起始区间内的所有toDoItem
    public ArrayList<ToDo_item> getToDoItemsContainsDT(LocalDateTime dateTime) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
// TODO: 2018/4/15
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsMatchesStartEnd(LocalDateTime dateTime1,LocalDateTime dateTime2) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
// TODO: 2018/4/15
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsMatchesStr(String str) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
// TODO: 2018/4/15
        return toDoItems;
    }

    public void addToDoItem(ToDo_item toDoItem) {
// TODO: 2018/4/15
        toDoItemArrayList.add(toDoItem);
    }
    public void deleteToDoItem(ToDo_item toDoItem) {
// TODO: 2018/4/15
        toDoItemArrayList.remove(toDoItem);
    }
}
