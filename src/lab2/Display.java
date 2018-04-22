package lab2;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lab3.Display_to_do_list;
import lab3.Main;
import lab3.ToDo_item;
import lab3.ToDo_list;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* You need to implement Calendar GUI here!
* show the calendar of month of today.
* jump to last/next month's calendar
* jump to last/next year's calendar
*
* jump to one specific day's calendar
* */

public class Display extends Pane {
    //    private Calendar calendar;
//    private lab2.CalendarDate calendarDate;
    private Text calendarLabel;
    private String monthName;
    private ToDo_list toDolist;
    private Stage stage;

    /**
     * Default constructor
     */
    public Display(CalendarDate calendarDate, ToDo_list toDoList) {
        calendarDate = DateUtil.getToday();
        this.toDolist = toDoList;
//        calendarDate.setDay(1);
        printDays(calendarDate, toDoList);
    }

    /**
     * Advance the calendar by 1 month
     */
    public void nextYear(CalendarDate calendarDate) {
        simpleChangeYear(calendarDate, 1);
    }

    private void simpleChangeYear(CalendarDate calendarDate, int x) {
        int y = calendarDate.getYear();
        calendarDate.setYear(y + x);
        printDays(calendarDate, toDolist);
    }

    public void search(CalendarDate calendarDate) {
        printDays(calendarDate, toDolist);
    }

    public void f_alert_informationDialog(String p_header, String p_message, Stage primaryStage) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.initOwner(primaryStage);
        _alert.show();
    }

    public void lastYear(CalendarDate calendarDate) {
        simpleChangeYear(calendarDate, -1);
    }

    public void nextMonth(CalendarDate calendarDate) {
        int m = calendarDate.getMonth();
        if (m == 12) {
            m = 0;
            int y = calendarDate.getYear();
            calendarDate.setYear(y + 1);
        }
        calendarDate.setMonth(m + 1);
        printDays(calendarDate, toDolist);
    }

    /**
     * Decrement the calendar by 1 month
     */
    public void lastMonth(CalendarDate calendarDate) {
        int m = calendarDate.getMonth();
        if (m == 1) {
            m = 13;
            int y = calendarDate.getYear();
            calendarDate.setYear(y - 1);
        }
        calendarDate.setMonth(m - 1);
        printDays(calendarDate, toDolist);
    }

    /**
     * Set up the calendar pane and get the require objects
     */
    public void printDays(CalendarDate calendarDate, ToDo_list toDoList) {
//      先加高亮的逻辑部分,加在getCalendarGrid函数里
        this.getChildren().clear();
        // Create the border pane, then get the calendar header and body
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getCalendarHeader(calendarDate)); //OK
        borderPane.setCenter(getCalendarGrid(calendarDate, toDoList));    //Not ok

        displayCalendar(borderPane);
    }

    /**
     * Return the calendar pane
     */
    private void displayCalendar(BorderPane borderPane) {
        this.getChildren().addAll(borderPane);
    }

    private void showToday() {
        printDays(DateUtil.getToday(), toDolist);
    }

    private Pane getCalendarGrid(CalendarDate calendarDate, ToDo_list toDoList) {
        // Variables
        List<LocalDate> localDateList = Display_to_do_list.emuDate(toDoList);
        int day = calendarDate.getDay();
        int dayOfWeek = calendarDate.getDayOfWeek();
        int daysInMonth = DateUtil.getNumberOfDaysInMonth(calendarDate);

        // Create the GridPane for the calendar
        GridPane calendarGrid = new GridPane();
        calendarGrid.setHgap(10.0);
        calendarGrid.setVgap(5.0);
        calendarGrid.setPadding(new Insets(10));

        // Add the days of the week to the top row of the calendar grid
        calendarGrid.addRow(0, new Text("Sunday"), new Text("Monday"),
                new Text("Tuesday"), new Text("Wednesday"), new Text("Thursday"),
                new Text("Friday"), new Text("Saturday"));

        // Print the dates for the current month's calendar
        //        Calendar lastMonth = (Calendar) calendar.clone();
        java.util.List<CalendarDate> thisMonth = DateUtil.getDaysInMonth(calendarDate);
        // Clone does not effect current calendar object
        ToggleGroup group = new ToggleGroup();
        int y = calendarDate.getYear();
        int m = calendarDate.getMonth();
        for (int i = 1; i <= daysInMonth; i++) {
            CalendarDate cd = thisMonth.get(i - 1);
            int columnIndex = cd.getDayOfWeek() % 7;
            int rowIndex = DateUtil.getLineInPane(cd);
            ToggleButton button = new ToggleButton(cd.getDay() + "");
            button.setMinWidth(45);
            button.setToggleGroup(group);
            button.setUserData(LocalDate.of(y, m, i));
            LocalDate localDate = LocalDate.of(y, m, i);
            for (Iterator<LocalDate> iterator = localDateList.iterator(); iterator.hasNext(); ) {
                if (localDate.isEqual(iterator.next())) {
                    button.setStyle("-fx-text-fill: blue;");
                }
            }
            if (i == day) {
                button.setStyle("-fx-background-color: red");
            }
            calendarGrid.add(button, columnIndex, rowIndex);
        }
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(
                    ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    System.out.println(group.getSelectedToggle().getUserData());
                    ArrayList<ToDo_item> matchesDate = toDoList.getToDoItemsMatchesDate((LocalDate) group.getSelectedToggle().getUserData());
                    Main.setToDisplay(matchesDate, stage);
                }
            }
        });

        // Print the dates for the previous month on the current calendar

        // Print the dates for the next month on the current calendar
        return calendarGrid;
    }

    /**
     * Create the label pane and display current month and year
     */

    private Pane getCalendarHeader(CalendarDate calendarDate) {
        int y = calendarDate.getYear();
        int m = calendarDate.getMonth();
        Main.cbMonth.getSelectionModel().select(m-1);
        Main.cbYear.getSelectionModel().select(y-1800);
        calendarLabel = new Text(getMonthName(calendarDate.getMonth()) + ", " + calendarDate.getYear());
        HBox labelBox = new HBox();
        labelBox.getChildren().addAll(calendarLabel);
        labelBox.setAlignment(Pos.CENTER);

        return labelBox;
    }

    /**
     * Return month name in English
     */
    private String getMonthName(int month) {
        // String monthName = null;

        switch (month) {
            case 1:
                monthName = "January";
                break;
            case 2:
                monthName = "February";
                break;
            case 3:
                monthName = "March";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "May";
                break;
            case 6:
                monthName = "June";
                break;
            case 7:
                monthName = "July";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "October";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "December";
                break;
            default:

        }
        return monthName;
    }

    public void setRadioButtonGroup(HBox hBoxRB1, HBox hBoxRB2, ToggleGroup group) {
        RadioButton rb1 = new RadioButton("按开始时间");
        rb1.setToggleGroup(group);
        RadioButton rb2 = new RadioButton("按结束时间");
        rb2.setToggleGroup(group);
        RadioButton rb3 = new RadioButton("按内容");
        rb3.setToggleGroup(group);
        RadioButton rb4 = new RadioButton("包含输入时间的所有Item");
        rb4.setToggleGroup(group);
        RadioButton rb5 = new RadioButton("输入开始时间和结束时间，返回期间所有Item\t");
        rb5.setToggleGroup(group);
        hBoxRB1.getChildren().addAll(rb1, rb2, rb3);
        hBoxRB2.getChildren().addAll(rb4, rb5);

        rb1.setUserData(1);
        rb2.setUserData(2);
        rb3.setUserData(3);
        rb4.setUserData(4);
        rb5.setUserData(5);
    }
}