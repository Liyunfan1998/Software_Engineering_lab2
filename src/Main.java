/*
* Start here!
*
* */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
//import java.util.GregorianCalendar;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {

        CalendarDate calendarDate = DateUtil.getToday();
        // Create a new CalendarPane
        CalendarPane calendarPane = new CalendarPane(calendarDate);

        // Pane to hold buttons
        HBox buttonBox = new HBox(10);
        Button btAdd = new Button(">");
        Button btSub = new Button("<");
        Button btLastYear = new Button("<<");
        Button btNextYear = new Button(">>");
        btAdd.setOnAction(e -> calendarPane.nextMonth(calendarDate));
        btSub.setOnAction(e -> calendarPane.lastMonth(calendarDate));
        btLastYear.setOnAction(e -> calendarPane.lastYear(calendarDate));
        btNextYear.setOnAction(e -> calendarPane.nextYear(calendarDate));
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
        bChooseSearch.setOnAction(e -> {
            CalendarDate calendarDate1 = new CalendarDate(chooseYear[0], chooseMonth[0], 1);
            calendarPane.draw(calendarDate1);
        });

        topBox.getChildren().addAll(cbYear, cbMonth, bChooseSearch);
        topBox.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(10);
        Button bTypeSearch = new Button("输入查询");
        TextField textField = new TextField();
        bTypeSearch.setOnAction(event -> {
            String date = textField.getText();
            if (DateUtil.isFormatted(date)) {
                CalendarDate cd = new CalendarDate(date);
                if (DateUtil.isValid(cd)) {
                    calendarPane.search(cd);
                }
            }
        });
        bottomBox.getChildren().addAll(textField, bTypeSearch);
        bottomBox.setAlignment(Pos.CENTER);

        VBox vBottomBox = new VBox(10);
        vBottomBox.getChildren().addAll(buttonBox, bottomBox);
        vBottomBox.setAlignment(Pos.CENTER);

        // Create BorderPane and place all of the elements
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(calendarPane);
        borderPane.setBottom(vBottomBox);
        borderPane.setTop(topBox);
        Scene scene = new Scene(borderPane, 450, 400);
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
class CalendarPane extends Pane {
    //    private Calendar calendar;
//    private CalendarDate calendarDate;
    private Text calendarLabel;
    private String monthName;

    /**
     * Default constructor
     */
    CalendarPane(CalendarDate calendarDate) {
        calendarDate = DateUtil.getToday();
//        calendarDate.setDay(1);
        draw(calendarDate);
    }

    /**
     * Advance the calendar by 1 month
     */
    void nextYear(CalendarDate calendarDate) {
        simpleChangeYear(calendarDate, 1);
    }

    private void simpleChangeYear(CalendarDate calendarDate, int x) {
        int y = calendarDate.getYear();
        calendarDate.setYear(y + x);
        draw(calendarDate);
    }

    public void search(CalendarDate calendarDate) {
        draw(calendarDate);
    }

    void lastYear(CalendarDate calendarDate) {
        simpleChangeYear(calendarDate, -1);
    }

    void nextMonth(CalendarDate calendarDate) {
        int m = calendarDate.getMonth();
        if (m == 12) {
            m = 0;
            int y = calendarDate.getYear();
            calendarDate.setYear(y + 1);
        }
        calendarDate.setMonth(m + 1);
        draw(calendarDate);
    }

    /**
     * Decrement the calendar by 1 month
     */
    void lastMonth(CalendarDate calendarDate) {
        int m = calendarDate.getMonth();
        if (m == 1) {
            m = 13;
            int y = calendarDate.getYear();
            calendarDate.setYear(y - 1);
        }
        calendarDate.setMonth(m - 1);
        draw(calendarDate);
    }

    /**
     * Set up the calendar pane and get the require objects
     */
    protected void draw(CalendarDate calendarDate) {
//      先加高亮的逻辑部分,加在getCalendarGrid函数里
        this.getChildren().clear();
        // Create the border pane, then get the calendar header and body
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(getCalendarHeader(calendarDate)); //OK
        borderPane.setCenter(getCalendarGrid(calendarDate));    //Not ok

        displayCalendar(borderPane);
    }

    /**
     * Return the calendar pane
     */
    private void displayCalendar(BorderPane borderPane) {
        this.getChildren().addAll(borderPane);
    }

    private void showToday() {
        draw(DateUtil.getToday());
    }

    private Pane getCalendarGrid(CalendarDate calendarDate) {
        // Variables
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
        List<CalendarDate> thisMonth = DateUtil.getDaysInMonth(calendarDate);
        // Clone does not effect current calendar object
        for (int i = 1; i <= daysInMonth; i++) {
            CalendarDate cd = thisMonth.get(i - 1);
            int columnIndex = cd.getDayOfWeek() % 7;
            int rowIndex = DateUtil.getLineInPane(cd);
            Button button = new Button(cd.getDay() + "");
            button.setMinWidth(45);
            if (i == day) {
                button.setStyle("-fx-background-color: red");
            }
            calendarGrid.add(button, columnIndex, rowIndex);
        }

        // Print the dates for the previous month on the current calendar

        // Print the dates for the next month on the current calendar
        return calendarGrid;
    }

    /**
     * Create the label pane and display current month and year
     */
    private Pane getCalendarHeader(CalendarDate calendarDate) {
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
}
/*    public static void main(String[] args) {

        //todo  We will run this class to test your codes.
        CalendarDate calendarDate = new CalendarDate("2018-2-20");
        System.out.println(calendarDate.getDayOfWeek());
        System.out.println(DateUtil.getNumberOfDaysInMonth(calendarDate));
        System.out.println(DateUtil.isFormatted("18-3-20"));
    }
}*/

/*
                System.out.println(DateUtil.getCurrentYear());
                System.out.println(DateUtil.getCurrentMonth());
                System.out.println(DateUtil.getCurrentDay());
                */
//        List<CalendarDate> list = DateUtil.getDaysInMonth(calendarDate);

//        int x = DateUtil.getDaysInMonth(calendarDate);

//        int listSize = list.size();
//        for (int i = 0; i < listSize; i++) {
//            System.out.println(list.get(i).getDay());
//        }
//        System.out.println(x);
//        DateUtil.isFormatted("2018-3-20");