package Application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.MalformedInputException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent root = FXMLLoader.load(this.getClass().getResource("../Views/MainView.fxml"));
            primaryStage.setTitle("RLC Maker");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
