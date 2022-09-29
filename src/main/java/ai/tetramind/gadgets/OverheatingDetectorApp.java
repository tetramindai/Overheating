package ai.tetramind.gadgets;


import ai.tetramind.gadgets.overheating.detector.engine.OverheatingDetector;
import ai.tetramind.gadgets.overheating.detector.gui.OverheatingNotification;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


public class OverheatingDetectorApp {
    private static final Object MUTEX = new Object();
    private static OverheatingNotification overheatingNotification = null;

    private static void displayOverheatingNotification() {
        synchronized (MUTEX) {
            if (!overheatingNotification.isVisible()) {
                overheatingNotification.setVisible(true);
            }
        }
    }

    private static void hideOverheatingNotification() {
        synchronized (MUTEX) {
            overheatingNotification.setVisible(false);
        }
    }

    private static void changeTemperatures(@NotNull Map<String, Double> temperatures) {
        synchronized (MUTEX) {
            if (overheatingNotification == null) {
                overheatingNotification = new OverheatingNotification(temperatures.size());
            }
            overheatingNotification.changeTemperatures(temperatures);
        }
    }

    private static void usage() {
        System.err.println("Invalid usage");
        System.exit(-1);
    }

    public static void main(String[] args) {

        if (args.length != 0) {
            usage();
        }

        var detector = new OverheatingDetector();

        detector.addChangeTemperaturesListener(OverheatingDetectorApp::changeTemperatures);
        detector.addHighTemperatureListener(OverheatingDetectorApp::displayOverheatingNotification);
        detector.addLowTemperatureListener(OverheatingDetectorApp::hideOverheatingNotification);

        detector.start();
    }
}
