package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Main extends Application {

  // Set window properties
  private static final int WINDOW_WIDTH = 1500;
  private static final int WINDOW_HEIGHT = 900;
  private static final String APP_TITLE = "Milk Weight";

  @Override
  public void start(Stage primaryStage) throws Exception {
    BorderPane root = new BorderPane();
    
    Label title = new Label("Milk Weight");
    title.setTextAlignment(TextAlignment.CENTER);
    title.setFont(Font.font("Comic Sans MS", 35));
    title.setPadding(new Insets(15,15,15,15));
    StackPane titleBox = new StackPane();
    titleBox.getChildren().add(title);
    StackPane.setAlignment(title, Pos.CENTER);
    
    
    //defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setAnimated(false); // axis animations are removed
    yAxis.setLabel("Milk Weight");
    yAxis.setAnimated(false); // axis animations are removed

    //creating the line chart with two axis created above
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    //lineChart.setTitle("Realtime JavaFX Charts");
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
    
    //series.getData().add(new XYChart.Data<>("1", 2));
    //series.getData().add(new XYChart.Data<>("2", 10));
    
    
    
    
    root.setCenter(lineChart);
    root.setTop(titleBox);
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
