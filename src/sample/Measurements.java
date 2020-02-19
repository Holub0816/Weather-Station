package sample;

import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * Class storing measurements of weather condition downloaded from class WeatherStation.
 * @author MaciejHołub, holubmaciek@gmail.com
 * @see WeatherStation
 */

public class Measurements implements Observer {

    private final WeatherStation weatherStation = WeatherStation.getWeatherStation();
    private final ArrayList<JsonElement> temperatureValues = new ArrayList<>();
    private final ArrayList<JsonElement> pressureValues = new ArrayList<>();

    private static final Measurements measurements = new Measurements();

    /**
     * Gets the instance of the class Measurements.
     * @return An Object by class Measurements representing object instance.
     */
    public static Measurements getMeasurments() {
        return measurements;
    }


    /**
     * Clears the content of arrays storing values of atmospheric pressure and temperature.
     */
    public void clear() {
        pressureValues.clear();
        temperatureValues.clear();
    }

    /**
     * Adds next temporary value of temperature to proper array.
     * @param parameter A JsonElement representing value of temperature.
     */
    public void addTemperature(JsonElement parameter) {
        temperatureValues.add(parameter);
    }

    /**
     * Adds next temporary value of temperature to proper array.
     * @param parameter A JsonElement representing value of temperature.
     */
    public void addPressure(JsonElement parameter) {
        pressureValues.add(parameter);
    }

    /**
     * Gets an array with temperature values saved in Json format
     * @return An ArrayList that contains temperature values saved in Json format
     */
    public ArrayList<JsonElement> getTemperatureValues() {
        return temperatureValues;
    }

    /**
     * Gets an array with temperature values saved in Json format
     * @return An ArrayList that contains temperature values saved in Json format
     */

    public ArrayList<JsonElement> getPressureValues() {
        return pressureValues;
    }

    @Override
    public void update() {
        measurements.addTemperature(weatherStation.getListOfWeatherParametres().get(0));
        measurements.addPressure(weatherStation.getListOfWeatherParametres().get(1));

    }


}