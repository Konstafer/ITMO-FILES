package Lab3;

import java.util.ArrayList;

public class Shape {
    protected double r = 0;
    private ArrayList<Shape> shapes = new ArrayList<Shape>();

    public Shape(double r) {
        setR(r);
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getR() {
        return r;
    }

    public boolean checkVertice(Vertice v) {
        for (Shape shape : shapes ) {
            if (shape.checkVertice(v)) {
                return true;
            }
        }
        return false;
    }

    public void addShapes(Shape ...shapes) {
        for (Shape shape : shapes) {
            this.shapes.add(shape);
        }
    }




}
