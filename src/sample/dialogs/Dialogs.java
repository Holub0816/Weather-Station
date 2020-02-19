package sample.dialogs;


import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class that holds dialogs information.
 *
 * @author Maciej Hołub, holubmaciek@gmail.com
 */
public class Dialogs {

    /**
     * Display basic information about application.
     */
    public static void dialogAboutApplication() {
        Alert informationAlert = new Alert(Alert.AlertType.INFORMATION);
        informationAlert.setTitle("About application");
        informationAlert.setHeaderText("Weather Station, version 1.0");
        informationAlert.setContentText("Autor: Maciej Hołub, Wrocław University of Technology");
        informationAlert.showAndWait();
    }

    /**
     * Display a warning when entered frequency is incorrect.
     */
    public static void dialogWrongFrequency() {
        Alert errorAlert = new Alert(Alert.AlertType.WARNING);
        errorAlert.setTitle("Warning");
        errorAlert.setHeaderText("The data download period will default to 500 milliseconds.");
        errorAlert.showAndWait();

    }

    /**
     * Opens a window with question. User choose if he/she wants to exit an application or not.
     *
     * @return An information which button have been clicked.
     */
    public static Optional<ButtonType> dialogExitApplication() {
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setTitle("Exit");
        exitAlert.setHeaderText("Are you sure you want to close the program?");
        Optional<ButtonType> result = exitAlert.showAndWait();
        return result;
    }


}
