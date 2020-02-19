package sample;

import com.google.gson.JsonElement;

import java.util.ArrayList;

/**
 * Class allowing to calculate basic statistics of collected measurements such as mean, maximum value, minimum value and standard deviation
 * @author Maciej Hołub, holubmaciek@gmail.com
 */
public class Statistics {

    private static final Statistics stats = new Statistics();
    private ArrayList<Double> listOfStatistics = new ArrayList<>();


    /**
     * Gets the instance of the class Statistics.
     * @return An Object by class Statistics representing object instance.
     */
    public static Statistics getStatistics() {
        return stats;
    }


    /**
     * Calculate and store statistics in proper array.
     * @param list An array of Json elements representing values of chosen weather parameter.
     */
    public void statisctics(ArrayList<JsonElement> list) {
        double min_value = Double.valueOf(list.get(0).toString());
        ;
        double max_value = Double.valueOf(list.get(0).toString());


        double sum = 0;
        double mean;
        double sum2 = 0;
        double odch_stand;
        double n = 0;

        for (JsonElement jsonElement : list) {
            listOfStatistics.clear();
            if (Double.valueOf(jsonElement.toString()) < min_value)
                min_value = Double.valueOf(jsonElement.toString());
            if (Double.valueOf(jsonElement.toString()) > max_value)
                max_value = Double.valueOf(jsonElement.toString());
            n++;
            sum = sum + Double.valueOf(jsonElement.toString());
        }
        mean = sum / n;

        for (JsonElement jsonElement : list) {
            double parameter = Math.pow((Double.valueOf(jsonElement.toString())) - mean, 2);
            sum2 = sum2 + parameter;
        }
        odch_stand = Math.sqrt(sum2 / n);

        listOfStatistics.add(min_value);
        listOfStatistics.add(max_value);
        listOfStatistics.add(mean);
        listOfStatistics.add(odch_stand);

    }

    /**
     * Gets array storing calculated statistics values.
     * @return An array of Double elements representing statistics values.
     */
    ArrayList<Double> getListOfStatistics() {
        return listOfStatistics;
    }

}
