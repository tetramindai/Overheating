package ai.tetramind.gadgets.overheating.detector.helper;

import ai.tetramind.gadgets.overheating.detector.Resources;

import java.awt.*;

public final class Helper {

    private static final double ABSOLUTE_ZERO = -273.15;

    private Helper() {
    }


    public static Color temperatureColor(Double value) {

        var result  = Color.DARK_GRAY;

        if(value == null) {
            value = 0.0;
        }


        if (value <= Resources.COLD_TEMPERATURE) {
            result = Color.blue;
        } else if (value < Resources.HIGH_TEMPERATURE) {
            result = Color.ORANGE;
        } else {
            result = Color.RED;
        }


        return result;
    }

    public static double kelvinToCelsius(Double value) {

        if (value == null) {
            value = 0.0;
        }

        return value + ABSOLUTE_ZERO;
    }

    public static double celsiusToKelvin(Double value) {

        if (value == null) {
            value = 0.0;
        }

        return value - ABSOLUTE_ZERO;
    }
}
