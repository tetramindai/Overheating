package ai.tetramind.gadgets.overheating.detector.engine;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ChangeTemperaturesListener {


    void temperatures(@NotNull Map<String, Double> temperatures);

}
