package net.kkolyan.evo.plain;

import java.awt.Color;

/**
 * @author nplekhanov
 */
public interface Renderer {

    void drawEllipse(double centerX, double centerY, double radiusX, double radiusY, Color color, boolean fill);

    void drawText(String text, int x, int y, Color color);

    void drawLabel(double x, double y, String text, Color color);
}
