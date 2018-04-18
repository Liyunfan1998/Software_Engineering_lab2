package lab3;/*
* Start here!
*
* */

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import lab2.CalendarDate;
import lab2.DateUtil;
import lab2.Display;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        CalendarDate calendarDate = DateUtil.getToday();
        // Create a new lab2.Display
        Display display = new Display(calendarDate);

        // Pane to hold buttons
        HBox buttonBox = new HBox(10);
        Button btAdd = new Button(">");
        Button btSub = new Button("<");
        Button btLastYear = new Button("<<");
        Button btNextYear = new Button(">>");
        btAdd.setOnAction(e -> display.nextMonth(calendarDate));
        btSub.setOnAction(e -> display.lastMonth(calendarDate));
        btLastYear.setOnAction(e -> display.lastYear(calendarDate));
        btNextYear.setOnAction(e -> display.nextYear(calendarDate));
        buttonBox.getChildren().addAll(btLastYear, btSub, btAdd, btNextYear);
        buttonBox.setAlignment(Pos.CENTER);


        HBox topBox = new HBox(10);
        Button bChooseSearch = new Button("选择框查询");
        ObservableList<String> arr;
        arr = FXCollections.observableArrayList();
        for (int i = 0; i < 300; i++) {
            arr.add((1800 + i) + "");
        }
        final int[] chooseYear = {1800};
        final int[] chooseMonth = {1};
        ChoiceBox cbYear = new ChoiceBox<>(FXCollections.observableArrayList(arr));
        cbYear.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
            chooseYear[0] = newv.intValue() + 1800;
        });
        ChoiceBox<String> cbMonth = new ChoiceBox<>(FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        cbMonth.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
            chooseMonth[0] = newv.intValue() + 1;
        });
        bChooseSearch.setOnAction((ActionEvent e) -> {
//            lab2.CalendarDate calendarDate1 = new lab2.CalendarDate(chooseYear[0], chooseMonth[0], 1);
            calendarDate.setYear(chooseYear[0]);
            calendarDate.setMonth(chooseMonth[0]);
            calendarDate.setDay(1);
            display.printDays(calendarDate);
        });

        topBox.getChildren().addAll(cbYear, cbMonth, bChooseSearch);
        topBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(10);
        Button bTypeSearch = new Button("输入查询");
        TextField textField = new TextField();
        bTypeSearch.setOnAction((ActionEvent event) -> {
            String date = textField.getText();
            if (DateUtil.isFormatted(date)) {
                CalendarDate cd = new CalendarDate(date);
                int y, m, d;
                y = cd.getYear();
                m = cd.getMonth();
                d = cd.getDay();
                calendarDate.setYear(y);
                calendarDate.setMonth(m);
                calendarDate.setDay(d);
                if (DateUtil.isValid(cd)) {
                    display.search(cd);
                } else {
//                    String p_message = "输入格式错误";
//                    Alert _alert = new Alert(Alert.AlertType.CONFIRMATION,p_message,new ButtonType("取消", ButtonBar.ButtonData.NO),new ButtonType("确定", ButtonBar.ButtonData.YES));
                    display.f_alert_informationDialog("ERROR", "输入日期不合法", primaryStage);
                }
            } else {
                display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);

            }
        });
        final Tooltip tooltip = new Tooltip("请输入格式为 YYYY-MM-DD 的日期");
        tooltip.setFont(new Font("Arial", 16));
        textField.setTooltip(tooltip);

        bottomBox.getChildren().addAll(textField, bTypeSearch);
        bottomBox.setAlignment(Pos.CENTER);

        VBox vBottomBox = new VBox(10);
        vBottomBox.getChildren().addAll(buttonBox, bottomBox);
        vBottomBox.setAlignment(Pos.CENTER);

        VBox v1 = new VBox();
        ScrollPane s1 = new ScrollPane();

        HBox hBoxSearch = new HBox(10);
        Button searchToDo = new Button("查询");
        TextField textFieldToDo = new TextField();
        hBoxSearch.getChildren().addAll(textFieldToDo, searchToDo);

        HBox hBoxRB1 = new HBox(10);
        HBox hBoxRB2 = new HBox(10);
        final ToggleGroup group = new ToggleGroup();
        RadioButton rb1 = new RadioButton("按开始时间");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("按结束时间");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("按内容\t");
        rb3.setToggleGroup(group);
        RadioButton rb4 = new RadioButton("时间段包含指定时间");
        rb4.setToggleGroup(group);
        RadioButton rb5 = new RadioButton("按开始时间和结束时间\t");
        rb5.setToggleGroup(group);
        hBoxRB1.getChildren().addAll(rb1, rb2, rb3);
        hBoxRB2.getChildren().addAll(rb4, rb5);

        rb1.setUserData(1);
        rb2.setUserData(2);
        rb3.setUserData(3);
        rb4.setUserData(4);
        rb5.setUserData(5);

        ToDo_list toDoList = new ToDo_list();
        toDoList.loadListFromFile();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
//default


        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                 Toggle new_Toggle) -> {
                    if (group.getSelectedToggle() != null) {
                        switch ((int) group.getSelectedToggle().getUserData()) {
                            case 1:
                                searchToDo.setOnAction((ActionEvent event) -> {
                                    System.out.print(1);
                                    String str = textFieldToDo.getText();
                                    try {
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesStart(localDateTime);
                                        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
                                            ToDo_item toDoItem = iterator.next();
                                            Button b = new Button("ToDo:" + "\nstart: "
                                                    + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                                                    + "\nthingsToDo: " + toDoItem.getThings2Do());
                                            v1.getChildren().add(b);
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 2:
                                searchToDo.setOnAction(event -> {
                                    System.out.print(2);
                                    String str = textFieldToDo.getText();
                                    try {
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesEnd(localDateTime);
                                        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
                                            ToDo_item toDoItem = iterator.next();
                                            Button b = new Button("ToDo:" + "\nstart: "
                                                    + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                                                    + "\nthingsToDo: " + toDoItem.getThings2Do());
                                            v1.getChildren().add(b);
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 3:
                                searchToDo.setOnAction(event -> {
                                    System.out.print(3);
                                    String str = textFieldToDo.getText();
                                    try {
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesStr(str);
                                        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
                                            ToDo_item toDoItem = iterator.next();
                                            Button b = new Button("ToDo:" + "\nstart: "
                                                    + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                                                    + "\nthingsToDo: " + toDoItem.getThings2Do());
                                            v1.getChildren().add(b);
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 4:
                                searchToDo.setOnAction(event -> {
                                    System.out.print(4);
                                    String str = textFieldToDo.getText();
                                    try {
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsContainsDT(localDateTime);
                                        for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
                                            ToDo_item toDoItem = iterator.next();
                                            Button b = new Button("ToDo:" + "\nstart: "
                                                    + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                                                    + "\nthingsToDo: " + toDoItem.getThings2Do());
                                            v1.getChildren().add(b);
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 5:
                                searchToDo.setOnAction(event -> {
                                    System.out.print(5);
                                    String str = textFieldToDo.getText();
                                    try {
                                        String[] strArr = str.split(",");
                                        if (strArr.length != 2) {
                                            display.f_alert_informationDialog("ERROR", "输入两个LocalDateTime", primaryStage);
                                        } else {
                                            LocalDateTime start = LocalDateTime.parse(strArr[0], formatter);
                                            LocalDateTime end = LocalDateTime.parse(strArr[1], formatter);
                                            ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesStartEnd(start, end);
                                            for (Iterator<ToDo_item> iterator = a.iterator(); iterator.hasNext(); ) {
                                                ToDo_item toDoItem = iterator.next();
                                                Button b = new Button("ToDo:" + "\nstart: "
                                                        + toDoItem.getStartTime() + "\nend: " + toDoItem.getEndTime()
                                                        + "\nthingsToDo: " + toDoItem.getThings2Do());
                                                v1.getChildren().add(b);
                                            }
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                        }

                    }
                });


        s1.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        s1.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        s1.setPannable(true);
        s1.setPrefSize(340, 220);
        s1.setMaxWidth(340);
        s1.setContent(v1);

        VBox vBoxRight = new VBox(5);
        vBoxRight.getChildren().addAll(hBoxSearch, hBoxRB1, hBoxRB2, s1);
        vBoxRight.setAlignment(Pos.CENTER);

        // Create BorderPane and place all of the elements
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(display);
        borderPane.setBottom(vBottomBox);
        borderPane.setTop(topBox);

        BorderPane borderPaneRight = new BorderPane();
        borderPaneRight.setRight(vBoxRight);

        BorderPane borderPaneAll = new BorderPane();
        borderPaneAll.setLeft(borderPane);
        borderPaneAll.setRight(borderPaneRight);

        Scene scene = new Scene(borderPaneAll, 850, 400);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void bTypeSearch(CalendarDate calendarDate) {
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

/**
 * Extend the Pane class that will create a calendar pane
 */

/*    public static void main(String[] args) {

        //todo  We will run this class to test your codes.
        lab2.CalendarDate calendarDate = new lab2.CalendarDate("2018-2-20");
        System.out.println(calendarDate.getDayOfWeek());
        System.out.println(lab2.DateUtil.getNumberOfDaysInMonth(calendarDate));
        System.out.println(lab2.DateUtil.isFormatted("18-3-20"));
    }
}*/

/*
                System.out.println(lab2.DateUtil.getCurrentYear());
                System.out.println(lab2.DateUtil.getCurrentMonth());
                System.out.println(lab2.DateUtil.getCurrentDay());
                */
//        List<lab2.CalendarDate> list = lab2.DateUtil.getDaysInMonth(calendarDate);

//        int x = lab2.DateUtil.getDaysInMonth(calendarDate);

//        int listSize = list.size();
//        for (int i = 0; i < listSize; i++) {
//            System.out.println(list.get(i).getDay());
//        }
//        System.out.println(x);
//        lab2.DateUtil.isFormatted("2018-3-20");