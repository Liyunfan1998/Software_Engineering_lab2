package lab3;

import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lab2.DateUtil;
import lab2.Display;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by luke1998 on 2018/4/15.
 */
public class Display_to_do_list extends Pane {
    public static void clearPane(Pane pane) {
        int count = pane.getChildren().size();
        for (int i = 0; i < count; i++) {
            pane.getChildren().remove(i);
        }
    }

    public static void clearVbox(VBox vBox) {
        int count = vBox.getChildren().size();
        System.out.println("count: " + count);
        for (int i = 0; i < count; i++) {
            vBox.getChildren().remove(count - i - 1);
        }
    }

    public static void addButtonByItem(ToDo_list tempList, ToDo_list allList, ToDo_item toDoItem, ScrollPane s1, VBox vAll, Display display, Stage primaryStage) {
        Button b = new Button("ToDo:" + "\nstart: "
                + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                + "\nthingsToDo: " + toDoItem.getThings2Do());
        b.setMinWidth(200);
        b.setMaxWidth(200);
        b.setOnAction(event -> {
            VBox vChnageOrDelete = new VBox();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            TextField start = new TextField(toDoItem.getStartTime().toString());
            TextField end = new TextField(toDoItem.getEndTime().toString());
            TextField toDoThings = new TextField(toDoItem.getThings2Do().toString());
            Button sureDle = new Button("Sure Delete");
            Button sureCha = new Button("Sure Change");
            vChnageOrDelete.getChildren().addAll(start, end, toDoThings, sureCha, sureDle);
            s1.setContent(vChnageOrDelete);
            sureDle.setOnAction(event1 -> {
                try {
                    allList.deleteToDoItem(toDoItem);
                    vAll.getChildren().remove(b);
                    allList.saveListAsFile();
                    s1.setContent(vAll);
                    display.printDays(DateUtil.getToday(), allList);
                } catch (Exception e) {
                    display.f_alert_informationDialog("ERROR", "删除出错 ！", primaryStage);
                }
            });
            sureCha.setOnAction(event1 -> {
                try {
                    allList.deleteToDoItem(toDoItem);
                    vAll.getChildren().remove(b);
                    LocalDateTime startDT = LocalDateTime.parse(start.getText(), formatter);
                    LocalDateTime endDT = LocalDateTime.parse(end.getText(), formatter);
                    String toDoStr = toDoThings.getText();
//                    ToDo_item newToDoItem = new ToDo_item(startDT, endDT, toDoStr);
                    toDoItem.setStartTime(startDT);
                    toDoItem.setEndTime(endDT);
                    toDoItem.setThings2Do(toDoStr);
                    allList.addToDoItem(toDoItem);
                    allList.saveListAsFile();
                    display.printDays(DateUtil.getToday(), allList);
                } catch (Exception e) {
                    display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                }
            });
        });
        vAll.getChildren().add(b);
    }

    public static void iteratListAndAddButton(ArrayList<ToDo_item> a, VBox vSearch, ToDo_list allList, ScrollPane s1, Display display, Stage stage) {
        clearVbox(vSearch);
        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
            ToDo_item toDoItem = iterator.next();
            Display_to_do_list.addButtonByItem(new ToDo_list(), allList, toDoItem, s1, vSearch, display, stage);
        }
    }

    public static void setScrollPaneAttr(ScrollPane s1) {
        s1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        s1.setPannable(true);
        s1.setPrefSize(340, 220);
        s1.setMaxWidth(340);
    }

    /**
     * 收集起始时间到结束时间之间所有的时间并以字符串集合方式返回
     *
     * @param start
     * @param end
     * @return
     */
    public static List<LocalDate> collectLocalDates(LocalDate start, LocalDate end) {
        // 用起始时间作为流的源头，按照每次加一天的方式创建一个无限流
        return Stream.iterate(start, localDate -> localDate.plusDays(1))
                // 截断无限流，长度为起始时间和结束时间的差+1个
                .limit(ChronoUnit.DAYS.between(start, end) + 1)
                // 把流收集为List
                .collect(Collectors.toList());
    }

    public static List<LocalDate> emuDate(ToDo_list toDoList) {
        ArrayList<LocalDate> dateArray = new ArrayList<>();
        for (Iterator<ToDo_item> iterator = toDoList.getAllItems().iterator(); iterator.hasNext(); ) {
            ToDo_item toDoItem = iterator.next();
            LocalDate startDate = toDoItem.getStartTime().toLocalDate();
            LocalDate endDate = toDoItem.getEndTime().toLocalDate();
            List<LocalDate> localDateList = collectLocalDates(startDate, endDate);
            dateArray.addAll(localDateList);
        }
        return dateArray;
    }
}
