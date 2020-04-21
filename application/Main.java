package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
    root.setTop(title);
    
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    primaryStage.setTitle(APP_TITLE);
    primaryStage.setScene(mainScene);
    primaryStage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
