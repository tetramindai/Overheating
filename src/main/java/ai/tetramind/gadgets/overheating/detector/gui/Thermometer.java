package ai.tetramind.gadgets.overheating.detector.gui;

import javax.swing.*;
import java.awt.*;

public final class Thermometer extends JPanel {

    private static final int MARGIN = 3;
    private static final double MAX_VALUE = 393.15;

    private double value;

    public Thermometer() {
        value = 0.0;
        setBackground(Color.DARK_GRAY);
    }

    public void setValue(double value) {
        this.value = value;
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        var height = getHeight() - MARGIN;
        var width = getWidth() - MARGIN;

        var percentage = Math.min(1.0, Math.abs(value / MAX_VALUE));

        g.setColor((percentage <= 0.6) ? Color.blue : Color.RED);
        g.fillRect(MARGIN, (int) (height - (height * percentage)), width - MARGIN, (int) (height * percentage));
    }
}
