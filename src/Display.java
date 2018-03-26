import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/*
* You need to implement Calendar GUI here!
* show the calendar of month of today.
* jump to last/next month's calendar
* jump to last/next year's calendar
*
* jump to one specific day's calendar
* */
public class Display {

    public Display() {
    }

    /**
     * Init the UI Windows here. For example, the frame, some panels and buttons.
     */

    private void init() {
    }

    /**
     * paint the days of whole current month on the frame with the given CalendarDate
     *
     * @param date a valid CalendarDate param.
     */

    private void paintDays(CalendarDate date) {
    }


}

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

    public void f_alert_informationDialog(String p_header, String p_message, Stage primaryStage) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("信息");
        _alert.setHeaderText(p_header);
        _alert.setContentText(p_message);
        _alert.initOwner(primaryStage);
        _alert.show();
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
        java.util.List<CalendarDate> thisMonth = DateUtil.getDaysInMonth(calendarDate);
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