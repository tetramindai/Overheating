package ai.tetramind.gadgets.overheating.detector.gui;

import ai.tetramind.gadgets.overheating.detector.Resources;
import ai.tetramind.gadgets.overheating.detector.helper.Helper;

import javax.swing.*;
import java.awt.*;

public final class Thermometer extends JPanel {
    private static final int MARGIN = 3;

    private double value; // °K

    public Thermometer() {
        value = 0.0;
        setBackground(Color.DARK_GRAY);
    }

    public void setValue(double value) {
        this.value = value;
        repaint();
    }

    @Override
    public void paint(Graphics graphics) {

        super.paint(graphics);

        var height = this.getHeight() - MARGIN;
        var width = this.getWidth() - MARGIN;

        var percentage = Math.min(1.0, Math.max(0, Helper.kelvinToCelsius(value) / Helper.kelvinToCelsius(Resources.CRITICAL_TEMPERATURE)));

        graphics.setColor(Helper.temperatureColor(value));
        graphics.fillRect(MARGIN, (int) (height - (height * percentage)), width - MARGIN, (int) (height * percentage));
    }
}
