package ai.tetramind.gadgets;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class Resources {

    private Resources() {
    }

    private static final String CONFIG_FILE_NAME = "config.json";

    private static final String DATABASE_KEY = "database";
    private static final String HOSTNAME_KEY = "hostname";
    private static final String PASSWORD_KEY = "password";
    private static final String PORT_KEY = "port";
    private static final String USERNAME_KEY = "username";


    private static final JSONObject CONFIG = readConfig();


    private static final String DATABASE_HOSTNAME = readDatabaseHostname();

    private static @NotNull String readDatabaseHostname() {

        assert CONFIG.has(DATABASE_KEY);

        var database = CONFIG.getJSONObject(DATABASE_KEY);

        assert database.has(HOSTNAME_KEY);

        var result = database.getString(HOSTNAME_KEY);

        assert result != null;

        return result;
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
