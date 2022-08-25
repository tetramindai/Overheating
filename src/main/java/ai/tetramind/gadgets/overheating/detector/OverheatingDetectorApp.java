package ai.tetramind.gadgets.overheating.detector;


import ai.tetramind.gadgets.overheating.detector.engine.OverheatingDetector;
import ai.tetramind.gadgets.overheating.detector.gui.OverheatingNotification;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class OverheatingDetectorApp {

    private static OverheatingNotification overheatingNotification = null;


    private static void displayOverheatingNotification() {
        overheatingNotification.setVisible(true);
    }

    private static void hideOverheatingNotification() {
        overheatingNotification.setVisible(false);
    }


    private static void changeTemperatures(@NotNull Map<String, Double> temperatures) {

        if (overheatingNotification == null) {
            overheatingNotification = new OverheatingNotification(temperatures.size());
        }

        overheatingNotification.changeTemperatures(temperatures);
    }


    public static void main(String[] args) {

        var detector = new OverheatingDetector();

        detector.addChangeTemperaturesListener(OverheatingDetectorApp::changeTemperatures);
        detector.addHighTemperatureListener(OverheatingDetectorApp::displayOverheatingNotification);
        detector.addLowTemperatureListener(OverheatingDetectorApp::hideOverheatingNotification);


        detector.start();
    }
}
