package shared;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.util.Objects;

public class GraphPoint {
    private double x;
    private double y;
    public Color color = Color.gray;

    public double X() {
        return this.x;
    }

    public double Y() {
        return this.y;
    }

    public  GraphPoint(double x,double y) {
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

    public void setGray() {
        this.changeColor(Color.gray);
    }

//    public void makeTransparent() {
//        float[] colorComponents = color.getColorComponents(null);
//        this.color = new Color(
//                ColorSpace.getInstance(ColorSpace.CS_sRGB),
//                colorComponents,
//                1
//        );
//    }



    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }

    @Override
    public boolean equals(Object obj) {
        try {
            GraphPoint newObj = (GraphPoint)obj;
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
