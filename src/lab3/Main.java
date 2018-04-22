package lab3;/*
* Start here!
*
* */

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
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
    public static ToDo_list toDoList = new ToDo_list();
    public static ChoiceBox cbYear = new ChoiceBox(), cbMonth = new ChoiceBox();
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    @Override
    public void start(Stage primaryStage) {
        CalendarDate calendarDate = DateUtil.getToday();
        // Create a new lab2.Display
        toDoList.loadListFromFile();

        display = new Display(calendarDate, toDoList);

        // only UI business
        HBox buttonBox = new HBox(10);
        display.setButtonBox(buttonBox, calendarDate);
        HBox topBox = new HBox(10);
        display.setTopBox(topBox, calendarDate);

        HBox bottomBox = new HBox(10);
        display.setBottomBox(bottomBox, calendarDate, primaryStage);

        VBox vBottomBox = new VBox(10);
        vBottomBox.getChildren().addAll(buttonBox, bottomBox);
        vBottomBox.setAlignment(Pos.CENTER);


        HBox hBoxSearch = new HBox(10);
        Button searchToDo = new Button("查询");
        TextField textFieldToDo = new TextField();
        final Tooltip tooltip = new Tooltip("提醒：若输入日期时间，格式为 yyyy-mm-ddThh:mm");
        tooltip.setFont(new Font("Arial", 16));
        textFieldToDo.setTooltip(tooltip);
        hBoxSearch.getChildren().addAll(textFieldToDo, searchToDo);

        HBox hBoxRB1 = new HBox(10);
        HBox hBoxRB2 = new HBox(10);
        final ToggleGroup group = new ToggleGroup();
        Display_to_do_list.setRadioButtonGroup(hBoxRB1, hBoxRB2, group);

        group.selectedToggleProperty().addListener(
                (ObservableValue<? extends Toggle> ov, Toggle old_Toggle,
                 Toggle new_Toggle) -> {
                    if (group.getSelectedToggle() != null) {
                        int i = (int) group.getSelectedToggle().getUserData();
                        System.out.print(i);

                        Display_to_do_list.clearVbox(vSearch);
                        s1.setContent(vSearch);
                        switch (i) {
                            case 1:
                                Display_to_do_list.setSearchToDoHandleByStartTime(searchToDo, textFieldToDo, primaryStage);
                                break;
                            case 2:
                                Display_to_do_list.setSearchToDoHandleByEndTime(searchToDo, textFieldToDo, primaryStage);
                                break;
                            case 3:
                                Display_to_do_list.setSearchToDoHandleByContent(searchToDo, textFieldToDo, primaryStage);
                                break;
                            case 4:
                                Display_to_do_list.setSearchToDoHandleByContaining(searchToDo, textFieldToDo, primaryStage);
                                break;
                            case 5:
                                Display_to_do_list.setSearchToDoHandleBetweenStartEnd(searchToDo, textFieldToDo, primaryStage);
                                break;
                        }

                    }
                });

        Display_to_do_list.setScrollPaneAttr(s1);
        s1.setContent(vSearch);

        Display_to_do_list.setVBoxAddItem(vBoxAddItem, primaryStage);

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
//        Button buttSave = new Button("Save as file");
//        buttSave.setOnAction(event -> {
//            toDoList.saveListAsFile();
//        });

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