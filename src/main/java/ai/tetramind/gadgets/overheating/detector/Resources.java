package ai.tetramind.gadgets.overheating.detector;

import ai.tetramind.gadgets.overheating.detector.helper.Helper;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Resources {

    private Resources() {
    }


    private static final String CONFIG_FILE_NAME = "/config.json";
    private static final String ICON_FILE_NAME = "/logo.png";
    private static final String TEMPERATURE_KEY = "temperature";
    private static final String HIGH_KEY = "high";
    private static final String COLD_KEY = "cold";
    private static final String CRITICAL_KEY = "critical";

    private static final JSONObject CONFIG = readConfig();


    public static final double HIGH_TEMPERATURE = Helper.celsiusToKelvin(readHighTemperature()); // °K
    public static final double COLD_TEMPERATURE = Helper.celsiusToKelvin(readColdTemperature()); // °K
    public static final double CRITICAL_TEMPERATURE = Helper.celsiusToKelvin(readCriticalTemperature()); // °K


    public static Image ICON = Toolkit.getDefaultToolkit().getImage(Resources.class.getResource(ICON_FILE_NAME));

    private static double readHighTemperature() {

        assert CONFIG.has(TEMPERATURE_KEY);

        var temperature = CONFIG.getJSONObject(TEMPERATURE_KEY);

        assert temperature != null;

        assert temperature.has(HIGH_KEY);

        return temperature.getDouble(HIGH_KEY);
    }

    private static double readCriticalTemperature() {

        assert CONFIG.has(TEMPERATURE_KEY);

        var temperature = CONFIG.getJSONObject(TEMPERATURE_KEY);

        assert temperature != null;

        assert temperature.has(CRITICAL_KEY);

        return temperature.getDouble(CRITICAL_KEY);
    }


    private static double readColdTemperature() {

        assert CONFIG.has(TEMPERATURE_KEY);

        var temperature = CONFIG.getJSONObject(TEMPERATURE_KEY);

        assert temperature != null;

        assert temperature.has(COLD_KEY);

        return temperature.getDouble(COLD_KEY);
    }


    private static @NotNull JSONObject readConfig() {

        JSONObject result = null;

        var content = new StringBuilder();

        var stream = Resources.class.getResourceAsStream(CONFIG_FILE_NAME);

        assert stream != null;

        try (var reader = new BufferedReader(new InputStreamReader(stream))) {

            var line = "";

            while ((line = reader.readLine()) != null) {
                content.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            result = new JSONObject(new String(content));
        } catch (JSONException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        return result;
    }
}
