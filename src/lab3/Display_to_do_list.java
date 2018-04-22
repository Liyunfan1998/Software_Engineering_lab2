package lab3;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lab2.DateUtil;

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

    public static void addButtonByItem(ToDo_list tempList, ToDo_list allList, ToDo_item toDoItem, Stage primaryStage) {
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
            Main.s1.setContent(vChnageOrDelete);
            sureDle.setOnAction(event1 -> {
                try {
                    allList.deleteToDoItem(toDoItem);
                    Main.vAll.getChildren().remove(b);
                    allList.saveListAsFile();
                    Main.s1.setContent(Main.vAll);
                    Main.display.printDays(DateUtil.getToday(), allList);
                } catch (Exception e) {
                    Main.display.f_alert_informationDialog("ERROR", "删除出错 ！", primaryStage);
                }
            });
            sureCha.setOnAction(event1 -> {
                try {
                    allList.deleteToDoItem(toDoItem);
                    Main.vAll.getChildren().remove(b);
                    LocalDateTime startDT = LocalDateTime.parse(start.getText(), formatter);
                    LocalDateTime endDT = LocalDateTime.parse(end.getText(), formatter);
                    String toDoStr = toDoThings.getText();
//                    ToDo_item newToDoItem = new ToDo_item(startDT, endDT, toDoStr);
                    toDoItem.setStartTime(startDT);
                    toDoItem.setEndTime(endDT);
                    toDoItem.setThings2Do(toDoStr);
                    allList.addToDoItem(toDoItem);
                    allList.saveListAsFile();
                    Main.display.printDays(DateUtil.getToday(), allList);
                } catch (Exception e) {
                    Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                }
            });
        });
        Main.vAll.getChildren().add(b);
    }

    public static void setRadioButtonGroup(HBox hBoxRB1, HBox hBoxRB2, ToggleGroup group) {
        RadioButton rb1 = new RadioButton("按开始时间");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("按结束时间");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("按内容");
        rb3.setToggleGroup(group);
        RadioButton rb4 = new RadioButton("包含输入时间\n的所有Item");
        rb4.setToggleGroup(group);
        RadioButton rb5 = new RadioButton("输入开始时间和结束时间，\n返回期间所有Item\t");
        rb5.setToggleGroup(group);
        hBoxRB1.getChildren().addAll(rb1, rb2, rb3);
        hBoxRB2.getChildren().addAll(rb4, rb5);

        rb1.setUserData(1);
        rb2.setUserData(2);
        rb3.setUserData(3);
        rb4.setUserData(4);
        rb5.setUserData(5);
    }

    public static void iteratListAndAddButton(ArrayList<ToDo_item> a, ToDo_list allList, Stage stage) {
        clearVbox(Main.vSearch);
        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
            ToDo_item toDoItem = iterator.next();
            Display_to_do_list.addButtonByItem(new ToDo_list(), allList, toDoItem, stage);
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

    public static void setVBoxAddItem(VBox vBoxAddItem, Stage primaryStage) {
        TextField start = new TextField("StartTime");
        TextField end = new TextField("EndTime");
        final Tooltip tooltip = new Tooltip("请输入格式为 yyyy-mm-ddThh:mm 的日期时间");
        tooltip.setFont(new Font("Arial", 16));
        start.setTooltip(tooltip);
        end.setTooltip(tooltip);
        TextField toDoThings = new TextField("Thing to Do");
        Button sureAdd = new Button("Sure Add");
        sureAdd.setOnAction(event -> {
            try {
                LocalDateTime startDT = LocalDateTime.parse(start.getText(), Main.formatter);
                LocalDateTime endDT = LocalDateTime.parse(end.getText(), Main.formatter);
                String toDoStr = toDoThings.getText();
                ToDo_item newToDoItem = new ToDo_item(startDT, endDT, toDoStr);
                Main.toDoList.addToDoItem(newToDoItem);
                Main.toDoList.saveListAsFile();
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
        vBoxAddItem.getChildren().addAll(start, end, toDoThings, sureAdd);

    }

    public static void setSearchToDoHandleByStartTime(Button searchToDo, TextField textFieldToDo, Stage primaryStage) {
        searchToDo.setOnAction((ActionEvent event) -> {
            String str = textFieldToDo.getText();
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(str, Main.formatter);
                ArrayList<ToDo_item> a = Main.toDoList.getToDoItemsMatchesStart(localDateTime);
                Display_to_do_list.iteratListAndAddButton(a, Main.toDoList, primaryStage);
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
    }

    public static void setSearchToDoHandleByEndTime(Button searchToDo, TextField textFieldToDo, Stage primaryStage) {
        searchToDo.setOnAction(event -> {
            String str = textFieldToDo.getText();
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(str, Main.formatter);
                ArrayList<ToDo_item> a = Main.toDoList.getToDoItemsMatchesEnd(localDateTime);
                Display_to_do_list.iteratListAndAddButton(a, Main.toDoList, primaryStage);
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
    }

    public static void setSearchToDoHandleByContent(Button searchToDo, TextField textFieldToDo, Stage primaryStage) {
        searchToDo.setOnAction(event -> {
            String str = textFieldToDo.getText();
            try {
                ArrayList<ToDo_item> a = Main.toDoList.getToDoItemsMatchesStr(str);
                Display_to_do_list.iteratListAndAddButton(a, Main.toDoList, primaryStage);
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
    }

    public static void setSearchToDoHandleByContaining(Button searchToDo, TextField textFieldToDo, Stage primaryStage) {
        searchToDo.setOnAction(event -> {
            String str = textFieldToDo.getText();
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(str, Main.formatter);
                ArrayList<ToDo_item> a = Main.toDoList.getToDoItemsContainsDT(localDateTime);
                Display_to_do_list.iteratListAndAddButton(a, Main.toDoList, primaryStage);
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
    }

    public static void setSearchToDoHandleBetweenStartEnd(Button searchToDo, TextField textFieldToDo, Stage primaryStage) {
        searchToDo.setOnAction(event -> {
            String str = textFieldToDo.getText();
            try {
                String[] strArr = str.split(",");
                if (strArr.length != 2) {
                    Main.display.f_alert_informationDialog("ERROR", "输入两个LocalDateTime，用英文逗号隔开", primaryStage);
                } else {
                    LocalDateTime start = LocalDateTime.parse(strArr[0], Main.formatter);
                    LocalDateTime end = LocalDateTime.parse(strArr[1], Main.formatter);
                    ArrayList<ToDo_item> a = Main.toDoList.getToDoItemsBetweenStartEnd(start, end);
                    Display_to_do_list.iteratListAndAddButton(a, Main.toDoList, primaryStage);
                }
            } catch (Exception e) {
                Main.display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
    }
}
