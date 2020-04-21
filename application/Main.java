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
    ComboBox<String> comboBox =
        new ComboBox<String>(FXCollections.observableArrayList(options));
    
    comboBox.valueProperty().addListener(new ChangeListener<String>() {
      @Override
      public void changed(ObservableValue<? extends String> arg, String arg1, String arg2) {
        System.out.println(arg);
        if (arg.getValue() == options[0]) { // option 1: input data
          System.out.println("option 1 selected");
          TilePane actionPane = new TilePane();
          Label label = new Label("button not selected");
//          final TextField in = new TextField("enter input data");
//          root.setCenter(new TilePane(in));
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
              
              //Defining the Submit button
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
                  }
                  else {
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
        }
        else if (arg.getValue() == options[1]) {
          System.out.println("");
          
        }
        
        

      }
    });
    root.setLeft(new TilePane(comboBox));
    
    
    
    
    
    
    
    
    
    
    
    
    
    Scene mainScene =  new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    arg0.setTitle(APP_TITLE);
    arg0.setScene(mainScene);
    arg0.show();
    

  }
  
  

  public static void main(String[] args) {
    launch("");

  }

}
