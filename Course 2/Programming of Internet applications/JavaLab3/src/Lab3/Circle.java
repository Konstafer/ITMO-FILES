package Lab3;

public class Circle extends Shape {

    public  Circle(double r) {
        super(r);
    }

    @Override
    public boolean checkVertice(Vertice v) {
        float y = v.getY();
        float x = v.getX();
        return ((r/2)*(r/2) <= (x*x + y*y) && (x<=0) && (y>=0));
    }

}
