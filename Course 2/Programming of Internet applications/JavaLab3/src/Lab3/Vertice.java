package Lab3;

public class Vertice {
    private float x = 0;
    private float y = 0;

    public Vertice(float x, float y){
        this.x=x;
        this.y=y;
    }

    public String print() {
        return "({x}, {y})";
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

}
