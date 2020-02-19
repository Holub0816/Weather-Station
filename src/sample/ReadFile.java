package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class reading temperature and pressure values from choosen file.
 * @author MaciejHołub, holubmaciek@gmail.com
 */
public class ReadFile {

    private final ArrayList<JsonElement> tempValues = new ArrayList<>();
    private final ArrayList<JsonElement> pressureValues = new ArrayList<>();

    private static final ReadFile readFile = new ReadFile();

    /**
     * Gets the instance of the class ReadFile.
     * @return An Object by class ReadFile representing object instance.
     */
    public static ReadFile getReadFile() {
        return readFile;
    }

    /**
     * Initialize object of FileChooser that allows us to read temperature and pressure values from choosen text file.
     * @see FileChooser
     * @see Stage
     */
    public void read() {

        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select the file you want to load.");
        File file = fileChooser.showOpenDialog(stage);

        if (file.getPath().contains(".txt")) {

            try {
                Scanner s = new Scanner(file);
                StringBuilder stringBuilder = new StringBuilder();

                do {
                    stringBuilder.append(s.nextLine());
                } while (s.hasNextLine());

                s.close();

                JsonParser jsonParser = new JsonParser();
                JsonObject object = jsonParser.parse(stringBuilder.toString()).getAsJsonObject();
                for (int i = 0; i < object.get("temp").getAsJsonArray().size() - 1; i++) {
                    tempValues.add(object.get("temp").getAsJsonArray().get(i));
                    pressureValues.add(object.get("pressure").getAsJsonArray().get(i));
                }
            } catch (FileNotFoundException e) {
                System.out.println("File does not exists.");
            }
        }
    }

    /**
     * Gets values of temperature from choosen file in Json format.
     * @return An array which contain temperature values written in Json format.
     */
    public ArrayList<JsonElement> getTempValues() {
        return tempValues;
    }

    /**
     * Gets values of temperature from choosen file in Json format.
     * @return An array which contain temperature values written in Json format.
     */
    public ArrayList<JsonElement> getPressureValues() {
        return pressureValues;
    }
}


