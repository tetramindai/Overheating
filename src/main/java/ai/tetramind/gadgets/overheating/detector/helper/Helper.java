package ai.tetramind.gadgets.overheating.detector.helper;

public final class Helper {

    private static final double ABSOLUTE_ZERO = -273.15;

    private Helper() {
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
