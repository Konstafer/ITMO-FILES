package Lab3;

public class Triangle extends Shape {

    public Triangle (double r) {
        super(r);
    }

    @Override
    public boolean checkVertice(Vertice v) {
        float y = v.getY();
        float x = v.getX();
        return ((y < r/2 - x) && (x>=0) && (y>=0));
    }
}
