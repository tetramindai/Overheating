package ai.tetramind.gadgets.overheating.detector.engine;

import com.profesorfalken.jsensors.JSensors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.security.SecureRandom;
import java.util.*;

public final class OverheatingDetector extends Thread {

    private static final double DEFAULT_LOW_TEMPERATURE = 50.0;
    private static final double DEFAULT_HIGH_TEMPERATURE = 80.0;

    private static final int TIME_INTERVAL = 1000;

    private final List<HighTemperatureListener> highTemperatureListeners;
    private final List<LowTemperatureListener> lowTemperatureListeners;
    private final List<ChangeTemperaturesListener> changeTemperaturesListeners;

    private final double lowTemperature;
    private final double highTemperature;

    public OverheatingDetector() {
        this(DEFAULT_LOW_TEMPERATURE, DEFAULT_HIGH_TEMPERATURE);
    }

    public OverheatingDetector(double lowTemperature, double highTemperature) {

        assert lowTemperature < highTemperature;

        this.lowTemperature = lowTemperature;
        this.highTemperature = highTemperature;

        highTemperatureListeners = new ArrayList<>();
        lowTemperatureListeners = new ArrayList<>();
        changeTemperaturesListeners = new ArrayList<>();
    }

    public void addHighTemperatureListener(@NotNull HighTemperatureListener listener) {
        this.highTemperatureListeners.add(listener);
    }

    public void addLowTemperatureListener(@NotNull LowTemperatureListener listener) {
        this.lowTemperatureListeners.add(listener);
    }

    public void addChangeTemperaturesListener(@NotNull ChangeTemperaturesListener listener) {
        this.changeTemperaturesListeners.add(listener);
    }

    private void fireHighTemperature() {
        for (var listener : highTemperatureListeners) {
            assert listener != null;
            listener.highTemperature();
        }
    }

    private void fireLowTemperature() {
        for (var listener : lowTemperatureListeners) {
            assert listener != null;
            listener.lowTemperature();
        }
    }

    private void fireChangeTemperatures(Map<String, Double> temperature) {
        for (var listener : changeTemperaturesListeners) {
            assert listener != null;
            listener.temperatures(temperature);
        }
    }


    @Override
    public void run() {

        var initTemperatures = readTemperatures();

        SwingUtilities.invokeLater(() -> {
            fireChangeTemperatures(initTemperatures);
        });


        while (!isInterrupted()) {

            var temperatures = readTemperatures();

            var maxTemperature = 0.0;

            for (var temperature : temperatures.values()) {
                maxTemperature = Math.max(maxTemperature, temperature);
            }

            if (maxTemperature < lowTemperature) {
                SwingUtilities.invokeLater(this::fireLowTemperature);
            } else if (maxTemperature > highTemperature) {
                SwingUtilities.invokeLater(this::fireHighTemperature);
            }


            SwingUtilities.invokeLater(() -> {
                fireChangeTemperatures(temperatures);
            });


            try {
                Thread.sleep(TIME_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private static Map<String, Double> readTemperatures() {

        var components = JSensors.get.components();

        var temperatures = new HashMap<String, Double>();

        var cpus = components.cpus;
        if (cpus != null) {
            for (var cpu : cpus) {
                if (cpu.sensors != null) {
                    for (var temperature : cpu.sensors.temperatures) {
                        temperatures.put(temperature.name, temperature.value);
                    }
                }
            }
        }


        var gpus = components.gpus;
        if (gpus != null) {
            for (var gpu : gpus) {
                if (gpu != null) {
                    if (gpu.sensors != null) {
                        for (var temperature : gpu.sensors.temperatures) {
                            temperatures.put(temperature.name, temperature.value);
                        }
                    }
                }
            }
        }


        return temperatures;
    }
}
