
package swingApp;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Objects;

/**
 * represents point on a 2D space
 */

public class GraphPoint2D {
  private double x;
  private double y;
  public Color color;

  public Boolean isNew = true;
  double X() {
    return this.x;
  }

  double Y() {
    return this.y;
  }

  public GraphPoint2D(double x, double y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public GraphPoint2D(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public void decAlpha(float decAlpha) {

    float[] colorComponents = color.getColorComponents(null);
    float newAlpha = (color.getAlpha() / 255.0f) - decAlpha;
    this.color = new Color(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            colorComponents,
            newAlpha < 0 ? 0 : newAlpha
    );
  }

  public void changeColor(Color color) {
    int alpha = this.color.getAlpha();
    this.color = new Color(
        ColorSpace.getInstance(ColorSpace.CS_sRGB),
        color.getComponents(null),
        alpha / 255.0f
    );
  }

  public void makeInvisible() {

    float[] colorComponents = color.getColorComponents(null);
    this.color = new Color(
            ColorSpace.getInstance(ColorSpace.CS_sRGB),
            colorComponents,
            1
    );
  }
  public GraphPoint2D(String x, String y) {
    this.x = Double.parseDouble(x);
    this.y = Double.parseDouble(y);
  }

  public GraphPoint2D(double[] coordinates) {
    if (coordinates.length < 2) {
      throw new IllegalArgumentException("In the array you passed to swingApp.GraphPoint2D constructor are not enough elements( <2 )");
    }
    this.x = coordinates[0];
    this.y = coordinates[1];
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.x, this.y);
  }

  @Override
  public boolean equals(Object obj) {
    try {
      GraphPoint2D newObj = (GraphPoint2D)obj;
      return newObj.hashCode() == this.hashCode();
    } catch (RuntimeException e) {
      return false;
    }
  }

  @Override
  public String toString() {
    return String.format("(%s, %s)", this.x, this.y);
  }
}

