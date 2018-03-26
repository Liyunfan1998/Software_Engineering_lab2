/*
* Start here!
*
* */

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
        bTypeSearch.setOnAction((ActionEvent event) -> {
            String date = textField.getText();
            if (DateUtil.isFormatted(date)) {
                CalendarDate cd = new CalendarDate(date);
                if (DateUtil.isValid(cd)) {
                    calendarPane.search(cd);
                } else {
//                    String p_message = "输入格式错误";
//                    Alert _alert = new Alert(Alert.AlertType.CONFIRMATION,p_message,new ButtonType("取消", ButtonBar.ButtonData.NO),new ButtonType("确定", ButtonBar.ButtonData.YES));
                    calendarPane.f_alert_informationDialog("ERROR", "输入日期不合法", primaryStage);
                }
            } else {
                calendarPane.f_alert_informationDialog("ERROR", "输入格式错误", primaryStage);

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