/*
* Start here!
*
* */

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
        btAdd.setOnAction(e -> calendarPane.nextMonth(calendarDate));
        btSub.setOnAction(e -> calendarPane.lastMonth(calendarDate));
        buttonBox.getChildren().addAll(btSub, btAdd);
        buttonBox.setAlignment(Pos.CENTER);

        // Create BorderPane and place all of the elements
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(calendarPane);
        borderPane.setBottom(buttonBox);

        Scene scene = new Scene(borderPane, 450, 200);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.show();
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
    public void nextMonth(CalendarDate calendarDate) {
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
    public void lastMonth(CalendarDate calendarDate) {
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
    private void draw(CalendarDate calendarDate) {
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
            calendarGrid.add(new Text(cd.getDay() + ""), columnIndex, rowIndex);
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