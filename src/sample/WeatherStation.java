package sample;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Class to collecting information about weather condition in different cities around the world and storing in proper array.
 *
 * @author Maciej Hołub, holubmaciek@gmail.com
 * @version 1.0
 */

public class WeatherStation implements Observable {
    private final ArrayList<JsonElement> listOfWeatherParametres = new ArrayList<>();
    private volatile ArrayList<Observer> observerList = new ArrayList<>();
    private static final WeatherStation weatherStation = new WeatherStation();

    /**
     * Gets the instance of the class WeatherStation.
     *
     * @return An Object by class WeatherStation representing object instance.
     */
    public final static WeatherStation getWeatherStation() {
        return weatherStation;
    }

    /**
     * Method adding weather parameters such as temperature, atmospheric pressure, humidity, maximum temperature and minimum temperature,
     * which are loaded from OpenWeather server to the empty array.
     *
     * @param city Name of the city from which we want to collect weather parameters.
     * @see Observer
     */

    public void addWeatherValues(String city) {
        listOfWeatherParametres.clear();
        try {
            if (!validateCity(city)) {
                System.out.println("The correct name has not been entered.");
            }
            StringBuilder response = new StringBuilder();
            String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&APPID=b1a85173cf89ddba5b775e3c682ff6df";
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JsonParser jp = new JsonParser();
            JsonObject object = jp.parse(response.toString()).getAsJsonObject();
            JsonElement object1 = object.get("main").getAsJsonObject().get("temp");
            listOfWeatherParametres.add(object1);
            JsonElement object2 = object.get("main").getAsJsonObject().get("pressure");
            listOfWeatherParametres.add(object2);
            JsonElement object3 = object.get("main").getAsJsonObject().get("humidity");
            listOfWeatherParametres.add(object3);
            JsonElement object4 = object.get("main").getAsJsonObject().get("temp_min");
            listOfWeatherParametres.add(object4);
            JsonElement object5 = object.get("main").getAsJsonObject().get("temp_max");
            listOfWeatherParametres.add(object5);
        } catch (MalformedURLException f) {
            f.printStackTrace();
        } catch (IOException e) {
            System.out.println("An error was detected during the connection.");
        }
        updateObserver();
    }

    /**
     * Gets array with weather parameters saved in Json format.
     *
     * @return An ArrayList that contains weather parameters saved in Json format.
     * @throws ArrayIndexOutOfBoundsException when array is empty.
     */


    public ArrayList<JsonElement> getListOfWeatherParametres() {
        if (listOfWeatherParametres.size() == 0)
            throw new ArrayIndexOutOfBoundsException("List doesn't contain elements.");
        return listOfWeatherParametres;
    }

    /**
     * @param o An object of Observer class which we want to create/add to list.
     * @see Observable
     */
    @Override
    public void addObserver(Observer o) {
        if (!observerList.contains(o)) {
            observerList.add(o);
        }
    }

    /**
     * @param o An object of Observer class which we want to remove from list.
     * @see Observable
     */
    @Override
    public void removeObserver(Observer o) {
        if (observerList.contains(o)) {
            observerList.remove(o);
        }
    }


    /**
     * @see Observable
     */
    @Override
    public void updateObserver() {
        for (Observer o : observerList)
            o.update();

    }


    /**
     * Validate parameter of method if chars are alphabet letters.
     *
     * @param city A String representing name of the city.
     * @return A String representing name of the city if it is correct.
     */

    public boolean validateCity(String city) {
        return city.matches("([a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+)");
    }
}