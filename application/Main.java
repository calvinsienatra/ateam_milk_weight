//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title:           a2 
// Files:           Main.java
// Course:          CS 400, Spring, 2020
//
// Author:          Adam Shedivy, Calvin Sienatra, Charlie Mrkvicka
// Email:           ajshedivy@wisc.edu, 
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully 
// acknowledge and credit those sources of help here.  Instructors and TAs do 
// not need to be credited here, but tutors, friends, relatives, room mates, 
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         N/A
// Online Sources:  N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
/********************************************
 * File: Main.java
 * Date: 4/21/2020
 * Course: CS 400 
 * Compiler: javac.exe 
 * Platform: Windows 10
 *******************************************/
package application;

import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/**
 * Main Milk weight application driver
 * 
 * @author aTeam 131
 *
 */
public class Main extends Application {
  private List<String> args;
  private static final int WINDOW_WIDTH = 1400;
  private static final int WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Milk Weight App";

  @Override
  public void start(Stage arg0) throws Exception {
    BorderPane root = new BorderPane();
    Label tile = new Label("milk weights");
    root.setTop(tile);

    String[] options = {"Load/Save Data", "Filter Data", "Get Report"};
    ComboBox<String> comboBox = new ComboBox<String>(FXCollections.observableArrayList(options));

    comboBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg, String arg1, String arg2) {
        System.out.println(arg);
        if (arg.getValue() == options[0]) { // option 1: input data
          System.out.println("option 1 selected");
          TilePane actionPane = new TilePane();
          Label label = new Label("button not selected");
          // final TextField in = new TextField("enter input data");
          // root.setCenter(new TilePane(in));
          Button loadButton = new Button("Load Data");
          Button saveButton = new Button("Save to file");
          EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {

            /**
             * event that will happen when button is pressed
             */
            @Override
            public void handle(ActionEvent e) {
              GridPane grid = new GridPane();
              // label.setText("this is my first Event");
              Label inputManualLabel = new Label("Input manually");
              final TextField milkWeight = new TextField();
              milkWeight.setPromptText("Enter milk weight");
              GridPane.setConstraints(milkWeight, 0, 1);
              GridPane.setConstraints(inputManualLabel, 0, 0);
              grid.getChildren().add(milkWeight);
              grid.getChildren().add(inputManualLabel);

              // set comment text field
              final TextField farmName = new TextField();
              farmName.setPrefColumnCount(15);
              farmName.setPromptText("Enter farm name");
              GridPane.setConstraints(farmName, 0, 3);
              grid.getChildren().add(farmName);

              // set comment text field
              final TextField date = new TextField();
              date.setPrefColumnCount(15);
              date.setPromptText("mm/dd/yyy");
              GridPane.setConstraints(date, 0, 4);
              grid.getChildren().add(date);

              // Defining the Submit button
              Button submit = new Button("Enter");
              GridPane.setConstraints(submit, 0, 5);

              Label browseFromFileLabel = new Label("Browse from file");
              GridPane.setConstraints(browseFromFileLabel, 0, 6);
              grid.getChildren().add(browseFromFileLabel);
              Button openFile = new Button("Browse File");
              grid.getChildren().add(openFile);
              GridPane.setConstraints(openFile, 0, 7);

              // add action to submit button
              submit.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                  Label outputLabel = new Label();
                  Label dataLabel = new Label();
                  GridPane.setConstraints(outputLabel, 0, 10);
                  GridPane.setConstraints(dataLabel, 0, 11);
                  grid.getChildren().add(outputLabel);
                  grid.getChildren().add(dataLabel);

                  if (milkWeight.getText() != null && !milkWeight.getText().isEmpty()) {
                    dataLabel.setText("data entered: " + milkWeight.getText());
                    if (farmName.getText() != null && !farmName.getText().isEmpty()) {
                      outputLabel.setText("farm entered: " + farmName.getText());
                    } else {
                      outputLabel.setText("data is invalid");
                    }
                  } else {
                    dataLabel.setText("invalid");
                  }
                }
              }); // end handler method

              grid.getChildren().add(submit);
              root.setRight(grid);
            }
          };

          loadButton.setOnAction(loadEvent);
          VBox vb = new VBox();
          vb.setPadding(new Insets(10));
          vb.getChildren().add(loadButton);
          vb.getChildren().add(saveButton);
          actionPane.getChildren().add(loadButton);
          actionPane.getChildren().add(saveButton);

          // add to root
          root.setRight(actionPane);
        } else if (arg.getValue() == options[1]) { // Filter Data

          GridPane grid = new GridPane();

          Label onlyShowLabel = new Label("Only show farms:");
          GridPane.setConstraints(onlyShowLabel, 0, 0);
          grid.getChildren().add(onlyShowLabel);

          // set fromFarm text field
          final TextField fromFarm = new TextField();
          fromFarm.setPrefColumnCount(15);
          fromFarm.setPromptText("From");
          GridPane.setConstraints(fromFarm, 0, 1);
          grid.getChildren().add(fromFarm);

          // set toFarm text field
          final TextField toFarm = new TextField();
          toFarm.setPrefColumnCount(15);
          toFarm.setPromptText("From");
          GridPane.setConstraints(toFarm, 1, 1);
          grid.getChildren().add(toFarm);


          Label onlyShowDateLabel = new Label("Only show dates:");
          GridPane.setConstraints(onlyShowDateLabel, 0, 2);
          grid.getChildren().add(onlyShowDateLabel);

          // set fromDate text field
          final TextField fromDate = new TextField();
          fromDate.setPrefColumnCount(15);
          fromDate.setPromptText("mm/dd/yyy");
          GridPane.setConstraints(fromDate, 0, 3);
          grid.getChildren().add(fromDate);

          // set toDate text field
          final TextField toDate = new TextField();
          toDate.setPrefColumnCount(15);
          toDate.setPromptText("From");
          GridPane.setConstraints(toDate, 1, 3);
          grid.getChildren().add(toDate);

          // Defining the Filter button
          Button filterButton = new Button("Filter");
          GridPane.setConstraints(filterButton, 0, 4);
          grid.getChildren().add(filterButton);


          root.setRight(grid);

        } else if (arg.getValue() == options[2]) { // Get Report
          GridPane grid = new GridPane();

          // set farmID text field
          final TextField farmIDText = new TextField();
          farmIDText.setPrefColumnCount(15);
          farmIDText.setPromptText("Farm ID");
          GridPane.setConstraints(farmIDText, 0, 0);
          grid.getChildren().add(farmIDText);

          // set yearText text field
          final TextField yearText = new TextField();
          yearText.setPrefColumnCount(15);
          yearText.setPromptText("Year");
          GridPane.setConstraints(yearText, 1, 0);
          grid.getChildren().add(yearText);

          // Defining the getFarmReport button
          Button getFarmReportButton = new Button("Get Farm Report");
          GridPane.setConstraints(getFarmReportButton, 2, 0);
          grid.getChildren().add(getFarmReportButton);

          // set yearAnnualText text field
          final TextField yearAnnualText = new TextField();
          yearAnnualText.setPrefColumnCount(15);
          yearAnnualText.setPromptText("Year");
          GridPane.setConstraints(yearAnnualText, 0, 1);
          grid.getChildren().add(yearAnnualText);

          // Defining the Annual Report button
          Button getAnnualReportButton = new Button("Get Annual Report");
          GridPane.setConstraints(getAnnualReportButton, 1, 1);
          grid.getChildren().add(getAnnualReportButton);

          // set yearMonthlyText text field
          final TextField yearMonthlyText = new TextField();
          yearMonthlyText.setPrefColumnCount(15);
          yearMonthlyText.setPromptText("Year");
          GridPane.setConstraints(yearMonthlyText, 0, 2);
          grid.getChildren().add(yearMonthlyText);

          // set monthMonthlyText text field
          final TextField monthMonthlyText = new TextField();
          monthMonthlyText.setPrefColumnCount(15);
          monthMonthlyText.setPromptText("Month");
          GridPane.setConstraints(monthMonthlyText, 1, 2);
          grid.getChildren().add(monthMonthlyText);

          // Defining the getMothlyReport button
          Button getMonthlyReportButton = new Button("Get Monthly Report");
          GridPane.setConstraints(getMonthlyReportButton, 2, 2);
          grid.getChildren().add(getMonthlyReportButton);

          // set fromDateText text field
          final TextField fromDateText = new TextField();
          fromDateText.setPrefColumnCount(15);
          fromDateText.setPromptText("From Date mm/dd/yyy");
          GridPane.setConstraints(fromDateText, 0, 3);
          grid.getChildren().add(fromDateText);

          // set toDateText text field
          final TextField toDateText = new TextField();
          toDateText.setPrefColumnCount(15);
          toDateText.setPromptText("To Date mm/dd/yyy");
          GridPane.setConstraints(toDateText, 1, 3);
          grid.getChildren().add(toDateText);

          // Defining the getDateReport button
          Button getDateReportButton = new Button("Get Date Report");
          GridPane.setConstraints(getDateReportButton, 2, 3);
          grid.getChildren().add(getDateReportButton);

          // set grid in root
          root.setRight(grid);
        }
      }
    }); // end listener

    root.setLeft(new TilePane(comboBox));


    Label title = new Label("Milk Weight");
    title.setTextAlignment(TextAlignment.CENTER);
    title.setFont(Font.font("Comic Sans MS", 35));
    title.setPadding(new Insets(15, 15, 15, 15));
    StackPane titleBox = new StackPane();
    titleBox.getChildren().add(title);
    StackPane.setAlignment(title, Pos.CENTER);


    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setAnimated(false); // axis animations are removed
    yAxis.setLabel("Milk Weight");
    yAxis.setAnimated(false); // axis animations are removed

    // creating the line chart with two axis created above
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAnimated(false); // disable animations

    XYChart.Series<String, Number> farm1 = new XYChart.Series<>();
    farm1.setName("Farm 1");

    XYChart.Series<String, Number> farm2 = new XYChart.Series<>();
    farm2.setName("Farm 2");

    // add series to chart
    lineChart.getData().add(farm1);
    lineChart.getData().add(farm2);

    farm1.getData().add(new XYChart.Data<>("1", 2));
    farm1.getData().add(new XYChart.Data<>("2", 10));
    farm1.getData().add(new XYChart.Data<>("3", 1));

    farm2.getData().add(new XYChart.Data<>("1", 1));
    farm2.getData().add(new XYChart.Data<>("2", 5));
    farm2.getData().add(new XYChart.Data<>("3", 9));
    farm2.getData().add(new XYChart.Data<>("4", 1));
    farm2.getData().add(new XYChart.Data<>("5", 5));
    farm2.getData().add(new XYChart.Data<>("6", 9));
    farm2.getData().add(new XYChart.Data<>("7", 1));
    farm2.getData().add(new XYChart.Data<>("8", 5));
    farm2.getData().add(new XYChart.Data<>("9", 9));
    farm2.getData().add(new XYChart.Data<>("10", 1));
    farm2.getData().add(new XYChart.Data<>("11", 5));
    farm2.getData().add(new XYChart.Data<>("12", 9));


    root.setCenter(lineChart);
    root.setTop(titleBox);


    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    arg0.setTitle(APP_TITLE);
    arg0.setScene(mainScene);
    arg0.show();
  }



  public static void main(String[] args) {
    launch(args);
  }

} // end Main class
