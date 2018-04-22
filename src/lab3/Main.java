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
    public static void main(String[] args) {
        Application.launch(args);
    }

    public static ScrollPane s1 = new ScrollPane();
    private static VBox vBoxAddItem = new VBox(10);
    public static VBox vSearch = new VBox();
    public static VBox vAll = new VBox();
    public static Display display;
    private static ToDo_list toDoList = new ToDo_list();
    public static ChoiceBox cbYear = new ChoiceBox(), cbMonth = new ChoiceBox();

    @Override
    public void start(Stage primaryStage) {

        CalendarDate calendarDate = DateUtil.getToday();
        // Create a new lab2.Display

        toDoList.loadListFromFile();

        display = new Display(calendarDate, toDoList);

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
        cbYear = new ChoiceBox<>(FXCollections.observableArrayList(arr));
        cbYear.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
            chooseYear[0] = newv.intValue() + 1800;
        });
        cbMonth = new ChoiceBox<>(FXCollections.observableArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"));
        cbMonth.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
            chooseMonth[0] = newv.intValue() + 1;
        });
        bChooseSearch.setOnAction((ActionEvent e) -> {
//            lab2.CalendarDate calendarDate1 = new lab2.CalendarDate(chooseYear[0], chooseMonth[0], 1);
            calendarDate.setYear(chooseYear[0]);
            calendarDate.setMonth(chooseMonth[0]);
            calendarDate.setDay(1);
            display.printDays(calendarDate, toDoList);
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


        HBox hBoxSearch = new HBox(10);
        Button searchToDo = new Button("查询");
        TextField textFieldToDo = new TextField();
        hBoxSearch.getChildren().addAll(textFieldToDo, searchToDo);

        HBox hBoxRB1 = new HBox(10);
        HBox hBoxRB2 = new HBox(10);
        final ToggleGroup group = new ToggleGroup();
        display.setRadioButtonGroup(hBoxRB1, hBoxRB2, group);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                 Toggle new_Toggle) -> {
                    if (group.getSelectedToggle() != null) {
                        int i = (int) group.getSelectedToggle().getUserData();
                        Display_to_do_list.clearVbox(vSearch);
                        s1.setContent(vSearch);
                        switch (i) {
                            case 1:
                                searchToDo.setOnAction((ActionEvent event) -> {
                                    String str = textFieldToDo.getText();
                                    try {
                                        System.out.print(i);
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesStart(localDateTime);
                                        Display_to_do_list.iteratListAndAddButton(a, toDoList, primaryStage);
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 2:
                                searchToDo.setOnAction(event -> {
                                    String str = textFieldToDo.getText();
                                    try {
                                        System.out.print(i);
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesEnd(localDateTime);
                                        Display_to_do_list.iteratListAndAddButton(a, toDoList, primaryStage);
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 3:
                                searchToDo.setOnAction(event -> {
                                    String str = textFieldToDo.getText();
                                    try {
                                        System.out.print(i);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsMatchesStr(str);
                                        Display_to_do_list.iteratListAndAddButton(a, toDoList, primaryStage);
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 4:
                                searchToDo.setOnAction(event -> {
                                    String str = textFieldToDo.getText();
                                    try {
                                        System.out.print(i);
                                        LocalDateTime localDateTime = LocalDateTime.parse(str, formatter);
                                        ArrayList<ToDo_item> a = toDoList.getToDoItemsContainsDT(localDateTime);
                                        Display_to_do_list.iteratListAndAddButton(a, toDoList, primaryStage);
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                            case 5:
                                searchToDo.setOnAction(event -> {
                                    String str = textFieldToDo.getText();
                                    try {
                                        System.out.print(i);
                                        String[] strArr = str.split(",");
                                        if (strArr.length != 2) {
                                            display.f_alert_informationDialog("ERROR", "输入两个LocalDateTime，用英文逗号隔开", primaryStage);
                                        } else {
                                            LocalDateTime start = LocalDateTime.parse(strArr[0], formatter);
                                            LocalDateTime end = LocalDateTime.parse(strArr[1], formatter);
                                            ArrayList<ToDo_item> a = toDoList.getToDoItemsBetweenStartEnd(start, end);
                                            Display_to_do_list.iteratListAndAddButton(a, toDoList, primaryStage);
                                        }
                                    } catch (Exception e) {
                                        display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
                                    }
                                });
                                break;
                        }

                    }
                });

        Display_to_do_list.setScrollPaneAttr(s1);
        s1.setContent(vSearch);

        TextField start = new TextField("StartTime");
        TextField end = new TextField("EndTime");
        TextField toDoThings = new TextField("Thing to Do");
        Button sureAdd = new Button("Sure Add");
        sureAdd.setOnAction(event -> {
            try {
                LocalDateTime startDT = LocalDateTime.parse(start.getText(), formatter);
                LocalDateTime endDT = LocalDateTime.parse(end.getText(), formatter);
                String toDoStr = toDoThings.getText();
                ToDo_item newToDoItem = new ToDo_item(startDT, endDT, toDoStr);
                toDoList.addToDoItem(newToDoItem);
                toDoList.saveListAsFile();
            } catch (Exception e) {
                display.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);
            }
        });
        vBoxAddItem.getChildren().addAll(start, end, toDoThings, sureAdd);


        Button addNew = new Button("Add Interface!");
        addNew.setOnAction(event -> {
            s1.setContent(vBoxAddItem);
        });
        Button backToAll = new Button("Refresh & Back to List");
        backToAll.setOnAction(event -> {
            Display_to_do_list.clearVbox(vAll);
            for (Iterator<ToDo_item> iterator = toDoList.getAllItems().iterator(); iterator.hasNext(); ) {
                ToDo_item toDoItem = iterator.next();
                Display_to_do_list.addButtonByItem(new ToDo_list(), toDoList, toDoItem, primaryStage);
            }
            s1.setContent(vAll);
            display.printDays(calendarDate, toDoList);
        });
        Button buttSave = new Button("Save as file");
        buttSave.setOnAction(event -> {
            toDoList.saveListAsFile();
        });

        VBox vBoxRight = new VBox(10);
        vBoxRight.getChildren().addAll(hBoxSearch, hBoxRB1, hBoxRB2, s1, addNew, backToAll);
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
        scene.getStylesheets().add("lab3/toggle.css");
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void setToDisplay(ArrayList<ToDo_item> toDoItemArrayList, Stage primaryStage) {
        ToDo_list tempList = new ToDo_list(toDoItemArrayList);
        Display_to_do_list.clearVbox(vAll);
        for (Iterator<ToDo_item> iterator = toDoItemArrayList.iterator(); iterator.hasNext(); ) {
            ToDo_item toDoItem = iterator.next();
            Display_to_do_list.addButtonByItem(tempList, toDoList, toDoItem, primaryStage);
        }
        s1.setContent(vAll);
    }

}