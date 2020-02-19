package sample;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.*;

/**
 * Class allowing to save measurements into specific file, chosen by user of application.
 * @author MaciejHołub, holubmaciek@gmail.com
 */
public class SaveFile {
    public SaveFile() {
    }

    private static final SaveFile saveFile = new SaveFile();

    /**
     * Gets the instance of the class SaveFile.
     * @return An Object by class SaveFile representing object instance.
     */
    public static SaveFile getSaveFile() {
        return saveFile;
    }

    /**
     * Provide choosing a text document where values should be saved. Saves values in Json format.
     * @see FileChooser
     */
    public void save() {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a Directory");

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            saveTextToFile(Measurements.getMeasurments(), file);

        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (PrintWriter printWriter = new PrintWriter("D:\\baza.txt")) {
            gson.toJson(Measurements.getMeasurments(), printWriter);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that saves directly temperature and pressure values to chosen file.
     * @param measurements Object of Measurements class storing temperature and pressure values.
     * @param file Object by class file representing the name of file where values will be saved.
     * @see Measurements
     * @see FileWriter
     */

     private void saveTextToFile(Measurements measurements, File file) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(file);
            fileWriter.write("{\"temp\":");
            fileWriter.write(measurements.getTemperatureValues().toString());
            fileWriter.write(",\"pressure\":");
            fileWriter.write(measurements.getPressureValues().toString());
            fileWriter.write("}");
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
