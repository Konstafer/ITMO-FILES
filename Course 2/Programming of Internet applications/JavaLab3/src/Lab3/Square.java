package Lab3;

public class Square extends Shape {

    public Square(double r) {
        super(r);
    }

    @Override
    public boolean checkVertice(Vertice v) {
        float y = v.getY();
        float x = v.getX();
        return (((x <= r) && (x >= 0)) && ((y >= -r) && (y <= 0)));
    }

}
