package application;

import java.util.List;
import java.util.Observable;
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

public class Main extends Application {
  private List<String> args;

  private static final int WINDOW_WIDTH = 1150;
  private static final int WINDOW_HEIGHT = 500;
  private static final String APP_TITLE = "Milk Weight App";

  @Override
  public void start(Stage arg0) throws Exception {
    BorderPane root = new BorderPane();
    Label tile = new Label("milk weights");
    root.setTop(tile);

    String[] options = {"input data", "get reports"};
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
          Button rightButton = new Button("Press here");
          EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            /**
             * event that will happen when button is pressed
             */
            @Override
            public void handle(ActionEvent e) {
              GridPane grid = new GridPane();
              label.setText("this is my first Event");
              final TextField in = new TextField();
              in.setPromptText("Enter path to data");
              GridPane.setConstraints(in, 0, 0);
              grid.getChildren().add(in);

              // set comment text field
              final TextField comment = new TextField();
              comment.setPrefColumnCount(15);
              comment.setPromptText("Enter farm name");
              GridPane.setConstraints(comment, 0, 2);
              grid.getChildren().add(comment);

              // Defining the Submit button
              Button submit = new Button("Enter");
              GridPane.setConstraints(submit, 1, 0);

              // add action to submit button
              submit.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                  Label outputLabel = new Label();
                  Label dataLabel = new Label();
                  GridPane.setConstraints(outputLabel, 0, 3);
                  GridPane.setConstraints(dataLabel, 0, 4);
                  grid.getChildren().add(outputLabel);
                  grid.getChildren().add(dataLabel);

                  if (in.getText() != null && !in.getText().isEmpty()) {
                    dataLabel.setText("data entered: " + in.getText());
                    if (comment.getText() != null && !comment.getText().isEmpty()) {
                      outputLabel.setText("farm entered: " + comment.getText());
                    } else {
                      outputLabel.setText("data is invalid");
                    }
                  } else {
                    dataLabel.setText("invalid");
                  }



                }
              });
              grid.getChildren().add(submit);
              root.setCenter(grid);
            }
          };

          rightButton.setOnAction(event);
          VBox vb = new VBox();
          vb.setPadding(new Insets(10));
          vb.getChildren().add(label);
          vb.getChildren().add(rightButton);
          actionPane.getChildren().add(rightButton);
          actionPane.getChildren().add(label);

          // add to root
          root.setRight(actionPane);
        } else if (arg.getValue() == options[1]) {
          System.out.println("");

        }



      }
    });
    root.setLeft(new TilePane(comboBox));


    Label title = new Label("Milk Weight");
    title.setTextAlignment(TextAlignment.CENTER);
    title.setFont(Font.font("Comic Sans MS", 35));
    title.setPadding(new Insets(15, 15, 15, 15));
    StackPane titleBox = new StackPane();
    titleBox.getChildren().add(title);
    StackPane.setAlignment(title, Pos.CENTER);


    // defining the axes
    final CategoryAxis xAxis = new CategoryAxis(); // we are gonna plot against time
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setAnimated(false); // axis animations are removed
    yAxis.setLabel("Milk Weight");
    yAxis.setAnimated(false); // axis animations are removed

    // creating the line chart with two axis created above
    final LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
    // lineChart.setTitle("Realtime JavaFX Charts");
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

}
