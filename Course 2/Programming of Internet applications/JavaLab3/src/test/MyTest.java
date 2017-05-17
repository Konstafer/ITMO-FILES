package test;
import Lab3.*;
import org.junit.Assert;
import org.junit.Test;

public class MyTest {
    @Test
    public void testPointContaining() {
        Shape shape = new Shape(10.0);
        Circle circle = new Circle(shape.getR());
        Square square = new Square(shape.getR());
        Triangle triangle = new Triangle(shape.getR());
        shape.addShapes(circle, square, triangle);

        Assert.assertTrue(shape.checkVertice(new Vertice(0, 0)));
        Assert.assertFalse(shape.checkVertice(new Vertice(-3, 3)));
        Assert.assertFalse(shape.checkVertice(new Vertice(-1, 1)));
        Assert.assertTrue(shape.checkVertice(new Vertice(3, -3)));
    }

    @Test
    public void testShapeCreatingPositive() {
        Assert.assertNotNull(new Shape(5));
        Assert.assertNotNull(new Shape(100));
    }
}
