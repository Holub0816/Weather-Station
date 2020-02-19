package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Runs the application.
 *
 * @author Maciej Hołub, holubmaciek@gmail.com
 */
public class Main extends Application {
    /**
     * Sets a new scene on the stage, set title of the stage, adds css style to the application.
     *
     * @param primaryStage Object of Stage class that is the main stage of application.
     * @throws Exception IO Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("WEATHER STATION");
        Scene scene = new Scene(root, 770, 625);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (getClass().getResource("style.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.show();
    }

   
    public static void main(String[] args) {
        launch(args);
    }
}
