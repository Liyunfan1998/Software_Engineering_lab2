package lab3;

import com.oracle.javafx.jmx.json.JSONException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class ToDo_list {
    private ArrayList<ToDo_item> toDoItemArrayList = new ArrayList<>();

    public ToDo_list() {
    }

    public ToDo_list(ArrayList<ToDo_item> toDoItemArrayList) {
        this.toDoItemArrayList = toDoItemArrayList;
    }

    public ArrayList<ToDo_item> getAllItems() {
        return toDoItemArrayList;
    }


    public ArrayList<ToDo_item> getToDoItemsMatchesDate(LocalDate date) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDate startDate = nextToDo.getStartTime().toLocalDate();
            LocalDate endDate = nextToDo.getEndTime().toLocalDate();
            if (date.isEqual(startDate) || date.isEqual(endDate) || date.isAfter(startDate) && date.isBefore(endDate)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }


    //    返回某个dateTime落在若干个toDoItem的起始区间内的所有toDoItem
    public ArrayList<ToDo_item> getToDoItemsContainsDT(LocalDateTime dateTime) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDateTime start = nextToDo.getStartTime();
            LocalDateTime end = nextToDo.getEndTime();
            if (dateTime.isAfter(start) && dateTime.isBefore(end) || dateTime.isEqual(start) || dateTime.isEqual(end)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsMatchesStartEnd(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDateTime start = nextToDo.getStartTime();
            LocalDateTime end = nextToDo.getEndTime();
            if (start.isEqual(dateTime1) && end.isEqual(dateTime2)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsBetweenStartEnd(LocalDateTime dateTime1, LocalDateTime dateTime2) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDateTime start = nextToDo.getStartTime();
            LocalDateTime end = nextToDo.getEndTime();
            if ((dateTime1.isBefore(start) || dateTime1.isEqual(start)) && (end.isBefore(dateTime2) || end.isEqual(dateTime2))) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }


    public ArrayList<ToDo_item> getToDoItemsMatchesStart(LocalDateTime dateTime1) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDateTime start = nextToDo.getStartTime();
            if (start.isEqual(dateTime1)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsMatchesEnd(LocalDateTime dateTime2) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            LocalDateTime end = nextToDo.getEndTime();
            if (end.isEqual(dateTime2)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }

    public ArrayList<ToDo_item> getToDoItemsMatchesStr(String str) {
        ArrayList<ToDo_item> toDoItems = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item nextToDo = (ToDo_item) iterator.next();
            String string = nextToDo.getThings2Do();
            if (isSubstring(str, string)) {
                toDoItems.add(nextToDo);
            }
        }
        return toDoItems;
    }

    public void addToDoItem(ToDo_item toDoItem) {
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            if (((ToDo_item) iterator.next()).isEqual(toDoItem)) {
                System.out.println("Cannot add because of duplication!");
                return;
            }
        }
        toDoItemArrayList.add(toDoItem);
        sortItemsByStartTime();
    }

    public void deleteToDoItem(ToDo_item toDoItem) {
        try {
            toDoItemArrayList.remove(toDoItem);
        } catch (Exception e) {
            System.out.println("No Such Item !");
        }
    }

    public void sortItemsByStartTime() {
        Collections.sort(this.toDoItemArrayList);
    }

    public static boolean isSubstring(String str, String target) {
        if (target.length() == 0)
            return false;
//        if (str.equalsIgnoreCase(target))
//            return true;
        else {
            return target.toLowerCase().contains(str.toLowerCase());
//            return isSubstring(str, target.substring(0, target.length() - 1))
//                    || isSubstring(str, target.substring(1));
        }
    }

    public void loadListFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(
                    "src/json/ToDoList.json"));// 读取原始json文件
//            br = new BufferedReader(new FileReader(
//                    "/Users/luke1998/ToDoList.json"));// 读取原始json文件

            String s = null;
            String allStr = "";
            while ((s = br.readLine()) != null) {
                allStr += s;
            }
            try {
                System.out.println(allStr);
                JSONObject dataJson = JSONObject.fromObject(allStr);
                convertJsonArray2ToDoList(dataJson);
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            br.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void convertJsonArray2ToDoList(JSONObject jsonObjectFromFile) {
        JSONArray features = jsonObjectFromFile.getJSONArray("ToDoList");
        for (int i = 0; i < features.size(); i++) {
            JSONObject toDo = features.getJSONObject(i);
            String start = toDo.getString("start");
            String end = toDo.getString("end");
            String str = toDo.getString("str");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime startTime = LocalDateTime.parse(start, formatter);
            LocalDateTime endTime = LocalDateTime.parse(end, formatter);
            ToDo_item toDoItem = new ToDo_item(startTime, endTime, str);
            this.addToDoItem(toDoItem);
        }
    }

    public JSONArray convert2JsonArray() {
        JSONArray jsonArray = new JSONArray();
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item toDoItem = iterator.next();
            String start = toDoItem.getStartTime().toString();
            String end = toDoItem.getEndTime().toString();
            String str = toDoItem.getThings2Do();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("start", start);
            jsonObject.put("end", end);
            jsonObject.put("str", str);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public void saveListAsFile() {
        try {
            JSONArray jsonArray = convert2JsonArray();
            BufferedWriter bw = new BufferedWriter(new FileWriter(
                    "src/json/ToDoList.json"));// 输出新的json文件
            String ws = "{\"ToDoList\":" + jsonArray.toString(1) + "}";
            bw.write(ws);
            bw.flush();
            bw.close();
        } catch (Exception e) {
            System.out.println("No return list !");
        }
    }
}
