//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: a2
// Files: Main.java, DataMap.java, ExportData.java, Farm.java, FarmGroup.java,
// FarmMonth.java, FarmYear.java, InputParser.java, MilkWeight.java,
// Months.java
// Course: CS 400, Spring, 2020
//
// Author: Adam Shedivy, Calvin Sienatra, Charlie Mrkvicka
// Email: ajshedivy@wisc.edu,
// Lecturer's Name: Debra Deppeler
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here. Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do. If you received no outside help from either type
// of source, then please explicitly indicate NONE.
//
// Persons: N/A
// Online Sources: N/A
//
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////

package application;

import java.io.File;
import java.io.IOException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.MissingFormatArgumentException;
import java.util.NoSuchElementException;
import java.util.Set;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.geometry.Pos;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Main Milk weight application driver
 * 
 * @author aTeam 131
 *
 */
public class Main extends Application {
  // Sets the window resolution and title
  private static final int WINDOW_WIDTH = 1400;
  private static final int WINDOW_HEIGHT = 800;
  private static final String APP_TITLE = "Milk Weight App";

  // Field for the stage and the main borderpane
  private Stage mainStage;
  private BorderPane root;

  // Field for the cheese factory
  private FarmGroup cheeseFactory;

  // Field to store the milk weight total to be shown
  private int totalMilkWeightText = 0;

  // Field to store the type of graph being drawn
  private int saveType = -1;

  // Field to temporaily store data for saving into a file
  private String farmIdPlaceholder;
  private Integer yearPlaceholder;
  private Integer monthPlaceholder;
  private LocalDate fromDatePlaceholder;
  private LocalDate toDatePlaceholder;

  /**
   * Generates the Load/Save panel of the stage
   */
  private void generateLoadSavePanel() {
    TilePane loadSavePanelPane = new TilePane(); // Create the load panel pane

    Button loadButton = new Button("Browse A Farm Directory Data"); // Create a load data button
    Button saveButton = new Button("Save to file"); // Create a save data button

    // Construct an EventHandler for load data
    EventHandler<ActionEvent> loadEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {

        // Create a new directory chooser
        DirectoryChooser chooseCSVDirectory = new DirectoryChooser();
        File selectedDirectory = chooseCSVDirectory.showDialog(mainStage); // Show the dialog
        boolean fuse = true;

        try {
          if (selectedDirectory != null) {
            // Reset the cheese factory
            cheeseFactory = new FarmGroup();

            // Get the list of files inside the directory
            File[] listOfCSV = selectedDirectory.listFiles();

            // Create a new InputParser object
            InputParser parser = new InputParser();

            // Parse every file
            for (File file : listOfCSV) {
              // Reset the parser
              parser = new InputParser();
              try {
                parser.inputData(file.getAbsolutePath()); // Parse the file
              } catch (IOException e1) {
                // Error opening file
                fuse = false;
                generateStatusMessage("Error. File cannot be opened!");
                break;
              } catch (Exception e2) {
                generateStatusMessage("Error. Unknown exception occured when opening file!");
              }

              // Get the data parsed
              DataMap<String, String, Integer> inputData = parser.getData();

              // Get the farm ids
              Set<String> farmIds = inputData.keySet();
              for (String farmId : farmIds) {
                for (String date : inputData.getV(farmId).keySet()) { // Put in each date given for
                                                                      // the farm
                  try {
                    // Parse the data
                    String[] splittedDate = date.split("-");
                    Integer year = Integer.parseInt(splittedDate[0]);
                    Integer month = Integer.parseInt(splittedDate[1]);
                    Integer day = Integer.parseInt(splittedDate[2]);

                    LocalDate convDate = LocalDate.of(year, month, day);

                    // Insert the milk weight
                    cheeseFactory.insertMilkWeight(farmId, convDate,
                        inputData.getDeepV(farmId, date));
                  } catch (Exception e3) {
                    generateStatusMessage("Error. Parsing file failed, please check your files!");
                  }

                }

              }

            }


          } else {
            // If directory is empty
            fuse = false;
            generateStatusMessage("Error. Directory is empty!");
          }
        } catch (Exception E) {
          // If an exception occurs
          fuse = false;
          generateStatusMessage("Error. Loading file failed!");
        }

        if (fuse) {
          // If successful, generate a new status message
          generateStatusMessage("File successfully loaded!");
        }

      }
    };

    loadButton.setOnAction(loadEvent); // Set the action of load button

    // Construct an EventHandler for save data
    EventHandler<ActionEvent> saveEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // EVENT IF SAVE DATA IS CLICKED
        if (saveType == -1) {
          // If no graph has been generated
          generateStatusMessage("Please generate a graph first!");
        } else if (saveType == 1) { // If saving farm report
          // Create a new exporter object
          ExportData exportFarmReport = new ExportData(cheeseFactory);

          // Open a new save file chooser
          FileChooser fileChooser = new FileChooser();
          // Set file extension
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          fileChooser.getExtensionFilters().add(extFilter);
          // Open the dialog
          File file = fileChooser.showSaveDialog(mainStage);

          try {
            // Generate the report
            exportFarmReport.exportFarmReport(file.getAbsolutePath(), farmIdPlaceholder,
                yearPlaceholder);
          } catch (IOException e1) {
            // If writing failed
            generateStatusMessage("Error saving file!");
          }

        } else if (saveType == 2) { // If saving annual report
          // Create a new exporter object
          ExportData exportFarmReport = new ExportData(cheeseFactory);

          // Open a new save file chooser
          FileChooser fileChooser = new FileChooser();
          // Set file extension
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          fileChooser.getExtensionFilters().add(extFilter);
          // Open the dialog
          File file = fileChooser.showSaveDialog(mainStage);

          try {
            // Generate the report
            exportFarmReport.exportAnnualreport(file.getAbsolutePath(), yearPlaceholder);
          } catch (IOException e1) {
            // If writing failed
            generateStatusMessage("Error saving file!");
          }
        } else if (saveType == 3) { // If saving monthly report
          // Create a new exporter object
          ExportData exportFarmReport = new ExportData(cheeseFactory);

          // Open a new save file chooser
          FileChooser fileChooser = new FileChooser();
          // Set file extension
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          fileChooser.getExtensionFilters().add(extFilter);
          // Open the dialog
          File file = fileChooser.showSaveDialog(mainStage);

          try {
            // Generate the report
            exportFarmReport.exportMonthlyReport(file.getAbsolutePath(), yearPlaceholder,
                monthPlaceholder);
          } catch (IOException e1) {
            // If writing failed
            generateStatusMessage("Error saving file!");
          }

        } else if (saveType == 4) { // If saving date range
          // Create a new exporter object
          ExportData exportFarmReport = new ExportData(cheeseFactory);

          // Open a new save file chooser
          FileChooser fileChooser = new FileChooser();
          // Set file extension
          FileChooser.ExtensionFilter extFilter =
              new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
          fileChooser.getExtensionFilters().add(extFilter);
          // Open the dialog
          File file = fileChooser.showSaveDialog(mainStage);

          try {
            // Generate the report
            exportFarmReport.exportDateRangeReport(file.getAbsolutePath(), fromDatePlaceholder,
                toDatePlaceholder);
          } catch (IOException e1) {
            // If writing failed
            generateStatusMessage("Error saving file!");
          }
        }

      }
    };

    saveButton.setOnAction(saveEvent); // Set the action of save button

    loadSavePanelPane.getChildren().add(loadButton); // Add the buttons to the pane
    loadSavePanelPane.getChildren().add(saveButton);

    loadSavePanelPane.setPadding(new Insets(15, 15, 15, 15)); // Add padding
    root.setRight(loadSavePanelPane); // Set the right root pane
  }

  /**
   * Generates the report panel
   */
  private void generateGetReportPanel() {
    GridPane getReportPanelPane = new GridPane();

    // Create a farm id text field
    final TextField farmIDText = new TextField();
    farmIDText.setPrefColumnCount(15);
    farmIDText.setPromptText("Farm ID");
    GridPane.setConstraints(farmIDText, 0, 0);
    getReportPanelPane.getChildren().add(farmIDText);

    // Create a year text field
    final TextField yearText = new TextField();
    yearText.setPrefColumnCount(15);
    yearText.setPromptText("Year");
    GridPane.setConstraints(yearText, 0, 1);
    getReportPanelPane.getChildren().add(yearText);

    // Create a get farm report button
    Button getFarmReportButton = new Button("Get Farm Report");
    GridPane.setConstraints(getFarmReportButton, 0, 2);
    getReportPanelPane.getChildren().add(getFarmReportButton);

    // Construct an EventHandler to get farm report
    EventHandler<ActionEvent> getFarmReportEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // EVENT IF GET FARM REPORT IS CLICKED
        try {
          // Check if text fields are empty
          if (farmIDText.getText() != null && !farmIDText.getText().isEmpty()
              && yearText.getText() != null && !yearText.getText().isEmpty()) {

            // Get the fields
            String farmId = farmIDText.getText();
            Integer year = Integer.parseInt(yearText.getText());

            try {
              // Generate the report
              generateFarmReportGraph(farmId, cheeseFactory.getFarmReport(farmId, year));
              // Update the total weight
              totalMilkWeightText = cheeseFactory.getTotalMilkWeightForFarmAndYear(farmId, year);
              // Regenerate the option pane
              generateOptionPane();

              // Set the placeholders for saving
              farmIdPlaceholder = farmId;
              yearPlaceholder = year;

              // Update status message
              generateStatusMessage("Farm report successfully generated!");

            } catch (NoSuchFieldException e1) { // If the farm is invalid
              generateStatusMessage("Error. Given farm is invalid!");
            } catch (MissingFormatArgumentException e2) { // If the farm is incomplete
              generateStatusMessage("Error. Stated farm is incomplete!");
            } catch (NoSuchElementException e3) { // If the given year is invalid
              generateStatusMessage("Error. Given year is invalid!");
            }

          }
        } catch (NumberFormatException e2) { // If input is not an integer
          generateStatusMessage("Input must be an integer!");
        }

      }
    };

    getFarmReportButton.setOnAction(getFarmReportEvent); // Set the action of get farm report button

    // Create a year text field for annual report
    final TextField yearAnnualText = new TextField();
    yearAnnualText.setPrefColumnCount(15);
    yearAnnualText.setPromptText("Year");
    GridPane.setConstraints(yearAnnualText, 0, 3);
    getReportPanelPane.getChildren().add(yearAnnualText);

    // Create a get annual report button
    Button getAnnualReportButton = new Button("Get Annual Report");
    GridPane.setConstraints(getAnnualReportButton, 0, 4);
    getReportPanelPane.getChildren().add(getAnnualReportButton);

    // Construct an EventHandler to annual data
    EventHandler<ActionEvent> getAnnualReportEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // EVENT IF GET ANNUAL REPORT IS CLICKED
        try {
          // Check if the required text fields are empty
          if (yearAnnualText.getText() != null && !yearAnnualText.getText().isEmpty()) {

            // Get the year
            Integer year = Integer.parseInt(yearAnnualText.getText());

            boolean fuse = true;

            // Define the annualReport hashmap object
            HashMap<String, Double> annualReport = null;
            try {
              // Get the annual report
              annualReport = cheeseFactory.getAnnualReport(year);
              // Update the total milk weight
              totalMilkWeightText = cheeseFactory.getTotalMilkWeightForAllFarmAndYear(year);
            } catch (NoSuchElementException e1) { // If the given year is invalid
              fuse = false;
              generateStatusMessage("Error. Year is invalid!");
            }

            if (fuse) { // If successful
              // Update year placeholder for saving
              yearPlaceholder = year;

              // Update the graph
              generateAnnualReportGraph(annualReport);
              // Update the message
              generateStatusMessage("Annual report successfully generated!");
              // Update the option pane
              generateOptionPane();
            }
          }
        } catch (NumberFormatException e2) { // If the given input is not an integer
          generateStatusMessage("Input must be an integer!");
        }

      }
    };

    getAnnualReportButton.setOnAction(getAnnualReportEvent); // Set the action of get annual report
                                                             // button

    // Create a year text field for monthly report
    final TextField yearMonthlyText = new TextField();
    yearMonthlyText.setPrefColumnCount(15);
    yearMonthlyText.setPromptText("Year");
    GridPane.setConstraints(yearMonthlyText, 0, 5);
    getReportPanelPane.getChildren().add(yearMonthlyText);

    // Create a monthly text field for monthly report
    final TextField monthMonthlyText = new TextField();
    monthMonthlyText.setPrefColumnCount(15);
    monthMonthlyText.setPromptText("Month");
    GridPane.setConstraints(monthMonthlyText, 0, 6);
    getReportPanelPane.getChildren().add(monthMonthlyText);

    // Create a get monthly report button
    Button getMonthlyReportButton = new Button("Get Monthly Report");
    GridPane.setConstraints(getMonthlyReportButton, 0, 7);
    getReportPanelPane.getChildren().add(getMonthlyReportButton);

    // Construct an EventHandler to get monthly data
    EventHandler<ActionEvent> getMonthlyReportEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // EVENT IF GET MONTHLY REPORT IS CLICKED
        // Check if the required fields are not null
        if (yearMonthlyText.getText() != null && !yearMonthlyText.getText().isEmpty()
            && monthMonthlyText.getText() != null && !monthMonthlyText.getText().isEmpty()) {
          try {
            // Check the month if valid
            if (Integer.parseInt(monthMonthlyText.getText()) >= 1
                && Integer.parseInt(monthMonthlyText.getText()) <= 12) {
              // Get the year and month
              Integer year = Integer.parseInt(yearMonthlyText.getText());
              Integer month = Integer.parseInt(monthMonthlyText.getText());

              boolean fuse = true;

              // Define the monthlyReport hashmap
              HashMap<String, Double> monthlyReport = null;

              try {
                // Get the monthly report
                monthlyReport = cheeseFactory.getMonthlyReport(year, month);
                // Update the total milk weight
                totalMilkWeightText =
                    cheeseFactory.getTotalMilkWeightForAllFarmAndMonth(year, month);
              } catch (NoSuchElementException e1) { // If the given year is invalid
                fuse = false;
                generateStatusMessage("Error. Year is invalid!");
              }

              if (fuse) { // If successful
                // Set placeholders for saving
                yearPlaceholder = year;
                monthPlaceholder = month;

                // Generate the graph
                generateMonthlyReportGraph(monthlyReport);
                // Generate the status message
                generateStatusMessage("Monthly report successfully generated!");
                // Generate the option pane
                generateOptionPane();
              }

            } else { // If the given month is invalid
              generateStatusMessage("Error. Month given is invalid!");
            }
          } catch (NumberFormatException e2) { // If the input is not an integer
            generateStatusMessage("Input must be an integer!");
          }

        }

      }
    };

    getMonthlyReportButton.setOnAction(getMonthlyReportEvent); // Set the action of get monthly
                                                               // report button

    // Create a from date text field
    final TextField fromDateText = new TextField();
    fromDateText.setPrefColumnCount(15);
    fromDateText.setPromptText("From Date mm/dd/yyy");
    GridPane.setConstraints(fromDateText, 0, 8);
    getReportPanelPane.getChildren().add(fromDateText);

    // Create a to date text field
    final TextField toDateText = new TextField();
    toDateText.setPrefColumnCount(15);
    toDateText.setPromptText("To Date mm/dd/yyy");
    GridPane.setConstraints(toDateText, 0, 9);
    getReportPanelPane.getChildren().add(toDateText);

    // Create a get date report button
    Button getDateReportButton = new Button("Get Date Report");
    GridPane.setConstraints(getDateReportButton, 0, 10);
    getReportPanelPane.getChildren().add(getDateReportButton);

    // Construct an EventHandler for date to date report data
    EventHandler<ActionEvent> getDateReportEvent = new EventHandler<ActionEvent>() {
      public void handle(ActionEvent e) {
        // EVENT IF GET DATE REPORT IS CLICKED
        // Check if the required fields are not empty
        if (fromDateText.getText() != null && toDateText.getText() != null) {
          boolean fuse = true;

          // Define the dateRangeReport hashmap
          HashMap<String, Double> dateRangeReport = null;
          // Define the LocalDate objects
          LocalDate parsedFromDate = null;
          LocalDate parsedToDate = null;
          try {
            // Try parsing the date
            parsedFromDate = parseDate(fromDateText.getText());
            parsedToDate = parseDate(toDateText.getText());

            try {
              // Get the data
              dateRangeReport = cheeseFactory.getDateRangeReport(parsedFromDate, parsedToDate);
              // Update the total milk weight
              totalMilkWeightText = cheeseFactory
                  .getTotalMilkWeightForAllFromDateToDate(parsedFromDate, parsedToDate);

            } catch (DateTimeException e2) { // If the from date is larger than the to date
              fuse = false;
              generateStatusMessage("Error: from date is larger than to date!");
            }

          } catch (IllegalArgumentException e1) { // If parsing date failed
            fuse = false;
            generateStatusMessage("Error: " + e1.getMessage());
          }

          if (fuse) { // If successful
            // Update placeholders for saving
            fromDatePlaceholder = parsedFromDate;
            toDatePlaceholder = parsedToDate;

            // Generate the graph
            generateDateRangeReportGraph(dateRangeReport);
            // Update the status message
            generateStatusMessage("Date range report successfully generated!");
            // Regenerate the option pane
            generateOptionPane();
          }

        }

      }
    };

    getDateReportButton.setOnAction(getDateReportEvent); // Set the action of get date report button

    getReportPanelPane.setPadding(new Insets(15, 15, 15, 15)); // Set the padding

    root.setRight(getReportPanelPane);
  }

  /**
   * Helper method to parse dates
   * 
   * @param date date in string format (mm/dd/yyy)
   * @return LocalDate object of the date
   * @throws IllegalArgumentException if the given date is invalid
   */
  private LocalDate parseDate(String date) throws IllegalArgumentException {
    // Split the date
    String[] dateSplitted = date.split("/");

    // If the date is not as expected
    if (dateSplitted.length != 3) {
      throw new IllegalArgumentException("Date give is an incorrect format!");
    }

    // Define the parsed date
    LocalDate parsedDate = null;

    try {
      // Parse the month, day, and year
      Integer month = Integer.parseInt(dateSplitted[0]);
      Integer day = Integer.parseInt(dateSplitted[1]);
      Integer year = Integer.parseInt(dateSplitted[2]);

      // Create the localdate object
      parsedDate = LocalDate.of(year, month, day);

    } catch (NumberFormatException e) { // If the given date is not a valid integer
      throw new IllegalArgumentException("Date given is not an integer!");
    } catch (DateTimeException e) { // If the given date is an invalid date
      throw new IllegalArgumentException("Date given is invalid!");
    }

    return parsedDate;
  }

  /**
   * Generates the title
   */
  private void generateTitle() {
    // Create the title label
    Label title = new Label("Milk Weight");
    title.setTextAlignment(TextAlignment.CENTER); // Set center
    title.setFont(Font.font("Comic Sans MS", 35)); // Set the font family and size
    title.setPadding(new Insets(15, 15, 15, 15)); // Create paddings
    StackPane titleBox = new StackPane(); // Create a stack pane for the title
    titleBox.getChildren().add(title); // Place the label to the stackpane
    StackPane.setAlignment(title, Pos.CENTER); // Align center

    root.setTop(titleBox);
  }

  /**
   * Generates the DateRangeReport graph
   * 
   * @param dateRangeReport data to be graphed
   */
  private void generateDateRangeReportGraph(HashMap<String, Double> dateRangeReport) {

    // Defines the axis
    final CategoryAxis xAxis = new CategoryAxis(); // plot
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Farms");
    xAxis.setAnimated(true); // axis animations are removed
    yAxis.setLabel("Milk Weight Percentage");
    yAxis.setAnimated(true); // axis animations are removed

    // Create a new linechart
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAnimated(true);

    // Create a new series set
    XYChart.Series<String, Number> farm = new XYChart.Series<>();
    farm.setName("Farms");

    // Add the series to the graph
    lineChart.getData().add(farm);

    // Get the farms
    Set<String> farmIds = dateRangeReport.keySet();
    ArrayList<String> farmIdsSorted = new ArrayList<>(); // Sort the farms

    // Add the farms into the sorted array
    for (String farmId : farmIds) {
      farmIdsSorted.add(farmId);
    }

    Collections.sort(farmIdsSorted); // Sort the array

    // Loop through and add the farms and their percentages into the graph
    for (int i = 0; i < farmIdsSorted.size(); i++) {
      farm.getData()
          .add(new XYChart.Data<>(farmIdsSorted.get(i), dateRangeReport.get(farmIdsSorted.get(i))));
    }

    // Set the save type
    saveType = 4;

    root.setCenter(lineChart);

  }

  /**
   * Generates a month report graph
   * 
   * @param monthlyReport data to be graphed
   */
  private void generateMonthlyReportGraph(HashMap<String, Double> monthlyReport) {
    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Farms");
    xAxis.setAnimated(true); // axis animations are removed
    yAxis.setLabel("Milk Weight Percentage");
    yAxis.setAnimated(true); // axis animations are removed

    // Create the new linechart
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAnimated(true);

    // Create the series
    XYChart.Series<String, Number> farm = new XYChart.Series<>();
    farm.setName("Farms");

    // Add the series into the chart
    lineChart.getData().add(farm);

    // Set the farm ids
    Set<String> farmIds = monthlyReport.keySet();

    // Sort the farm ids into an arraylist
    ArrayList<String> farmIdsSorted = new ArrayList<>();


    for (String farmId : farmIds) {
      farmIdsSorted.add(farmId);
    }

    Collections.sort(farmIdsSorted);

    // Place the data into the graph
    for (int i = 0; i < farmIdsSorted.size(); i++) {
      farm.getData()
          .add(new XYChart.Data<>(farmIdsSorted.get(i), monthlyReport.get(farmIdsSorted.get(i))));
    }

    // Set the save type
    saveType = 3;

    root.setCenter(lineChart);

  }

  /**
   * Generate the annual report graph
   * 
   * @param annualReport data to be graphed
   */
  private void generateAnnualReportGraph(HashMap<String, Double> annualReport) {
    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Farms");
    xAxis.setAnimated(true); // axis animations are removed
    yAxis.setLabel("Milk Weight Percentage");
    yAxis.setAnimated(true); // axis animations are removed

    // Create a new line chart
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAnimated(true);
    lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);

    // Add a new series into the graph
    XYChart.Series<String, Number> farm = new XYChart.Series<>();
    farm.setName("Farms");

    lineChart.getData().add(farm);

    // Get the farm ids
    Set<String> farmIds = annualReport.keySet();

    // Sort the farm ids into an arraylist
    ArrayList<String> farmIdsSorted = new ArrayList<>();

    for (String farmId : farmIds) {
      farmIdsSorted.add(farmId);
    }

    Collections.sort(farmIdsSorted);

    // Put the data into the graph
    for (int i = 0; i < farmIdsSorted.size(); i++) {
      farm.getData()
          .add(new XYChart.Data<>(farmIdsSorted.get(i), annualReport.get(farmIdsSorted.get(i))));
    }

    // Set the save type
    saveType = 2;

    root.setCenter(lineChart);
  }

  /**
   * Generate the farm report graph
   * 
   * @param farmId farm id to graph
   * @param percentages percentages to graph
   */
  private void generateFarmReportGraph(String farmId, ArrayList<Double> percentages) {
    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setAnimated(true); // axis animations are removed
    yAxis.setLabel("Milk Weight");
    yAxis.setAnimated(true); // axis animations are removed

    // Creating the line chart with two axis created above
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAxisSortingPolicy(LineChart.SortingPolicy.X_AXIS);

    lineChart.setAnimated(true); // disable animations

    XYChart.Series<String, Number> farm = new XYChart.Series<>();
    farm.setName(farmId);

    // Add series to chart
    lineChart.getData().add(farm);

    // Put the data into the graph
    for (int i = 0; i < 12; i++) {
      farm.getData().add(new XYChart.Data<>(Integer.toString(i + 1), percentages.get(i)));
    }

    // Set the save type
    saveType = 1;

    root.setCenter(lineChart);
  }

  /**
   * Generate a default graph
   */
  private void generateGraph() {
    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setAnimated(true); // axis animations are removed
    yAxis.setLabel("Milk Weight");
    yAxis.setAnimated(true); // axis animations are removed

    // creating the line chart with two axis created above
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);

    lineChart.setAnimated(true); // disable animations

    root.setCenter(lineChart);
  }

  /**
   * Generate the status message
   * 
   * @param msg status message
   */
  private void generateStatusMessage(String msg) {
    GridPane statusMsgBox = new GridPane();

    // Create the label
    Label statusText = new Label("Status: " + msg);
    statusText.setFont(Font.font("Comic Sans MS", 20));
    statusText.setPadding(new Insets(15, 15, 15, 15));

    // Add the label into the pane
    statusMsgBox.getChildren().add(statusText);

    root.setBottom(statusMsgBox);

  }

  /**
   * Generate the option pane
   */
  private void generateOptionPane() {
    TilePane leftPane = new TilePane(Orientation.VERTICAL);

    // Create the combobox
    String[] options = {"Load/Save Data", "Get Report"};
    ComboBox<String> comboBox = new ComboBox<String>(FXCollections.observableArrayList(options));

    // Create a listener for the combobox
    comboBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg, String arg1, String arg2) {
        if (arg.getValue() == options[0]) { // If input/output data is chosen
          // Generate the load save panel
          generateLoadSavePanel();

        } else if (arg.getValue() == options[1]) { // If filter Data
          // Generate the filter data panel
          generateGetReportPanel();

        }
      }
    });

    leftPane.getChildren().add(comboBox);

    // Create the milk label
    Label totalMilkLabel = new Label("Total milk weight: " + totalMilkWeightText + " lbs");
    leftPane.getChildren().add(totalMilkLabel);

    // Set padding
    leftPane.setPadding(new Insets(15, 15, 15, 15));

    root.setLeft(leftPane); // Place the combobox button to the left borderpane


  }

  /**
   * Start method when application is launched
   * 
   * Stage arg0 main stage
   */
  @Override
  public void start(Stage arg0) throws Exception {
    // Set the main stage
    mainStage = arg0;

    // Create root
    this.root = new BorderPane();

    // Create new FarmGroup
    this.cheeseFactory = new FarmGroup();

    // Generate panes
    generateOptionPane();
    generateStatusMessage("Please load the milk weight data!");
    generateTitle(); // Generate the title
    generateGraph(); // Generate default graph

    // Create the main scene
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);

    arg0.setTitle(APP_TITLE); // Set window title
    arg0.setScene(mainScene); // Set main scene
    arg0.show(); // Show the scene
  }

  /**
   * Main driver of the class
   * 
   * @param args input params
   */
  public static void main(String[] args) {
    launch(args);
  }

}
