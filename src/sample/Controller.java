package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sample.dialogs.Dialogs;
import java.util.Optional;
import static java.lang.Thread.sleep;

/**
 * Class that runs graphic interface of the application.
 * @author Maciej Hołub, holubmaciek@gmail.com
 */
public class Controller {

    private final Measurements measurements = Measurements.getMeasurments();
    private final WeatherStation weatherStation = WeatherStation.getWeatherStation();
    private final Statistics stats = Statistics.getStatistics();
    private final SaveFile saveFile = SaveFile.getSaveFile();
    private Thread t = new Thread("thread1");
    private XYChart.Series series1;
    private XYChart.Series series2;
    private int count = 0;
    private int period = 0;


    @FXML
    private ScatterChart<Number, Number> scatterchart1;

    @FXML
    private ScatterChart<Number, Number> scatterchart2;

    @FXML
    private TextField city;

    @FXML
    private Button start;

    @FXML
    private Button pauza;

    @FXML
    private Button stop;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private TextField per;


    /**
     * Method that controls which button is clicked and executes choosen task.
     * @param event An Object of ActionEvent class represents actual event( clicked button).
     */
    public void handle(ActionEvent event) {
        if (event.getSource().equals(start)) {

            String miasto = city.getText();
            try {
                period = Integer.valueOf(per.getText());
            } catch (NumberFormatException e) {
                Dialogs.dialogWrongFrequency();
                per.clear();
            }
            int finalPeriod;
            finalPeriod = period;
            t = new Thread(() -> {
                while (!Thread.currentThread().isInterrupted()) {
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            newPlace(measurements, miasto);
                        }};
                    try {
                        if (finalPeriod < 500) {
                            Thread.sleep(500);
                        } else {
                            sleep(finalPeriod);
                        }

                    } catch (InterruptedException exc) {
                        Thread.currentThread().interrupt();
                    }
                    Platform.runLater(runnable);
                }
            });

            t.start();
            start.setDisable(true);


        } else if (event.getSource().equals(stop)) {
            t.interrupt();
            clearParameters();
            start.setDisable(false);


        } else if (event.getSource().equals(pauza)) {
            t.interrupt();
            start.setDisable(false);

        }
    }

    /**
     *Method initializing tasks for different objects and variables.
     */
    public void initialize() {
        start.setOnAction(this::handle);
        stop.setOnAction(this::handle);
        pauza.setOnAction(this::handle);
        series1 = new XYChart.Series();
        series2 = new XYChart.Series();
        scatterchart1.getData().add(series1);
        scatterchart2.getData().add(series2);
        weatherStation.addObserver(measurements);
    }

    /**
     * Method that removes all measurements points from charts and content from both labels.
     */
    public void clearParameters() {
        measurements.clear();
        series1.getData().removeAll(series1.getData());
        series2.getData().removeAll(series2.getData());
        label1.setText("");
        label2.setText("");
        count = 0;

    }

    /**
     * Method that actualize data about statistics, weather parameters and add new measurement points to charts.
     * @param measurements An object of Measurements storing information about current temperature and pressure value.
     * @param miasto A String representing the name of the city.
     */
    public void newPlace(Measurements measurements, String miasto) {
        try {
            weatherStation.addWeatherValues(miasto);
            stats.statisctics(measurements.getTemperatureValues());
            setLabel1(weatherStation.getListOfWeatherParametres().get(0).toString(), weatherStation.getListOfWeatherParametres().get(1).toString(), weatherStation.getListOfWeatherParametres().get(2).toString(), weatherStation.getListOfWeatherParametres().get(3).toString(), weatherStation.getListOfWeatherParametres().get(4).toString());
            setLabel2(measurements.getTemperatureValues().size() - 1, stats.getListOfStatistics().get(0), stats.getListOfStatistics().get(1), stats.getListOfStatistics().get(2), stats.getListOfStatistics().get(3));
            addValuesToChart(series1, Double.valueOf(weatherStation.getListOfWeatherParametres().get(0).toString()));
            addValuesToChart(series2, Double.valueOf(weatherStation.getListOfWeatherParametres().get(1).toString()));
            count++;

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that fills labels and charts with information about weather readed from file.
     * @see ReadFile
     */
    public void loadFile() {
        final ReadFile readFile = ReadFile.getReadFile();
        readFile.read();
        for (int i = 0; i < readFile.getTempValues().size(); i++) {
            stats.statisctics(readFile.getTempValues());
            setLabel2(readFile.getTempValues().size() - 1, stats.getListOfStatistics().get(0), stats.getListOfStatistics().get(1), stats.getListOfStatistics().get(2), stats.getListOfStatistics().get(3));
            addValuesToChart(series1, Double.valueOf(readFile.getTempValues().get(i).toString()));
            stats.statisctics(readFile.getPressureValues());
            addValuesToChart(series2, Double.valueOf(readFile.getPressureValues().get(i).toString()));
            count++;
        }
    }

    /**
     * Add new measurements point to the chart.
     * @param series Series of the chart.
     * @param value A Double representing value of specific measurement.
     */
    public void addValuesToChart(XYChart.Series series, double value) {
        try {
            series.getData().add(new XYChart.Data(count, value));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Fills label with text about weather condition.
     * @param temp A String representing temperature.
     * @param pressure A String representing atmospheric pressure.
     * @param humidity A String representing air humidity.
     * @param min_temp A String representing maximum temperature.
     * @param max_tenp A String representing minimum temperature.
     */
    public void setLabel1(String temp, String pressure, String humidity, String min_temp, String max_tenp) {
        label1.setText("Temperatura: " + temp + " °C" + "\nCiśnienie atmosferyczne: " + pressure + " hPa" + "\nWilgotność: " + humidity + " %" + "\nMinimalna temperatura: " + min_temp + " °C" + "\nMaksymalna teperatura: " + max_tenp + " °C");
    }

    /**
     * Fills label with text about statistics.
     * @param numberOfMeasurements An Integer representing number of measurements.
     * @param min_value A Double representing minimal value of parameter when application is running.
     * @param max_value A Double representing maximal value of parameter when application is running.
     * @param mean A double representing mean from every measurements.
     * @param odch_std A double representing standard deviation of measurements.
     */
    public void setLabel2(int numberOfMeasurements, double min_value, double max_value, double mean, double odch_std) {
        label2.setText("Liczba pomiarów: " + numberOfMeasurements + "\nTemperatura minimalna: " + min_value + " °C" + "\nTemperatura maksymalna: " + max_value + " °C" + "\nŚrednia: " + mean + " °C" + "\nOdczylenie standardowe: " + odch_std + " °C");
    }


    @FXML
    /**
     * Save weather parameters to file.
     */
    void saveToFile() {
        if (measurements.getTemperatureValues().size() != 0)
            saveFile.save();

    }

    @FXML
    /**
     * Load weather parameters from file.
     */
    void loadFromFile() {
        t.interrupt();
        clearParameters();
        loadFile();
    }

    /**
     * Close application if "OK" button clicked.
     */
    public void closeApplication() {
        Optional<ButtonType> result = Dialogs.dialogExitApplication();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * Show information about application.
     */
    public void about() {
        Dialogs.dialogAboutApplication();
    }
}


