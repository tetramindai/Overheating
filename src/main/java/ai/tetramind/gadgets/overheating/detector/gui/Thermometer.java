package ai.tetramind.gadgets.overheating.detector.gui;

import ai.tetramind.gadgets.overheating.detector.Resources;
import ai.tetramind.gadgets.overheating.detector.helper.Helper;

import javax.swing.*;
import java.awt.*;

public final class Thermometer extends JPanel {

    private static final int MARGIN = 3;
    private static final double MAX_VALUE = Helper.celsiusToKelvin(110.0); // °C

    private double value; // °K

    public Thermometer() {
        value = 0.0;
        setBackground(Color.DARK_GRAY);
    }

    public void setValue(double value) {
        this.value = value;
        repaint();
    }

    private Color color() {

        var result = Color.DARK_GRAY;

        if (value <= Resources.COLD_TEMPERATURE) {
            result = Color.blue;
        } else if (value < Resources.HIGH_TEMPERATURE) {
            result = Color.ORANGE;
        } else {
            result = Color.RED;
        }

        return result;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        var height = getHeight() - MARGIN;
        var width = getWidth() - MARGIN;

        var percentage = Math.min(1.0, Math.abs(value / MAX_VALUE));

        g.setColor(color());
        g.fillRect(MARGIN, (int) (height - (height * percentage)), width - MARGIN, (int) (height * percentage));
    }
}
