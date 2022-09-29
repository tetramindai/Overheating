package ai.tetramind.gadgets.overheating.detector.gui;

import ai.tetramind.gadgets.overheating.detector.Resources;
import ai.tetramind.gadgets.overheating.detector.helper.Helper;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public final class OverheatingNotification extends JFrame {
    private static final String TEMPERATURE_MESSAGE = "%.2f °C";

    private static final Font DANGER_FONT = new Font("Serif", Font.BOLD, 24);

    private static final int MARGIN = 35;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 400;
    private static final String TITLE = "Overheating Detector";

    private final JLabel temperatureLabel;

    private final Thermometer[] thermometers;

    public OverheatingNotification(int size) {
        super(TITLE);

        assert size >= 0;

        temperatureLabel = new JLabel(String.format(TEMPERATURE_MESSAGE, 0.0));
        temperatureLabel.setHorizontalTextPosition(JLabel.CENTER);
        temperatureLabel.setHorizontalAlignment(SwingConstants.CENTER);
        temperatureLabel.setVerticalAlignment(SwingConstants.CENTER);
        temperatureLabel.setForeground(Color.RED);
        temperatureLabel.setFont(DANGER_FONT);
        temperatureLabel.setBorder(BorderFactory.createLineBorder(Color.orange, 4));


        var thermometersPanel = new JPanel();
        thermometersPanel.setLayout(new GridLayout(1, size));


        thermometers = new Thermometer[size];
        for (int i = 0; i < thermometers.length; i++) {
            thermometers[i] = new Thermometer();
            thermometers[i].setBorder(BorderFactory.createLineBorder(Color.orange, 2));
            thermometersPanel.add(thermometers[i]);
        }

        setSize(WIDTH, HEIGHT);
        setVisible(false);
        setResizable(false);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY);


        add(thermometersPanel, BorderLayout.CENTER);
        add(temperatureLabel, BorderLayout.PAGE_END);

        setIconImage(Resources.ICON);

        initLocation();
    }

    private void initLocation() {

        var screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        var screenSizeWidth = screenSize.getWidth();
        var screenSizeHeight = screenSize.getHeight();

        var width = this.getWidth();
        var height = this.getHeight();

        setLocation((int) (screenSizeWidth - width - MARGIN), (int) (screenSizeHeight - height - MARGIN));
    }

    private void drawThermometers(double[] temperatures) {

        for (int i = 0; i < thermometers.length && i < temperatures.length; i++) {

            var temperature = temperatures[i];

            var thermometer = thermometers[i];

            assert thermometer != null;

            thermometer.setValue(temperature);
        }
    }

    private void displayTemperature(double[] temperatures) {

        var maxKelvin = 0.0;

        for (var temperature : temperatures) {
            maxKelvin = Math.max(maxKelvin, temperature);
        }

        temperatureLabel.setText(String.format(TEMPERATURE_MESSAGE, Helper.kelvinToCelsius(maxKelvin)));
        temperatureLabel.setForeground((maxKelvin > Resources.HIGH_TEMPERATURE) ? Color.RED : Color.blue);
    }

    public void changeTemperatures(@NotNull Map<String, Double> temperatures) {

        var size = temperatures.size();

        var array = new double[size];

        var index = 0;
        for (var temperature : temperatures.values()) {
            array[index++] = temperature;
        }

        changeTemperatures(array);
    }

    public void changeTemperatures(double[] temperatures) {
        displayTemperature(temperatures);
        drawThermometers(temperatures);
    }
}
