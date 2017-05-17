package Lab3;

import java.util.Iterator;
import java.util.LinkedHashSet;

public class Work3 {

    public static void main(String [] args) {
        Collection collection = new Collection();
        UserInterface ui = new UserInterface();
        Shape figure;
        Shape square;
        Shape circle;
        Shape triangle;
        double r = 0;

        collection.addToCollection();
        r = ui.workWithUser();
        figure = new Shape(r);
        square = new Square(figure.getR());
        circle = new Circle(figure.getR());
        triangle = new Triangle(figure.getR());
        figure.addShapes(square,circle,triangle);

        LinkedHashSet coordinates = collection.getCollection();
        Iterator<Vertice> iter = coordinates.iterator();
        for(;iter.hasNext();) {
            Vertice v1 = iter.next();
            System.out.println("{" + v1.getX() + "," + v1.getY() + "}" + " --> " + figure.checkVertice(v1));

        }
    }

    static void print(Object obj) {
        System.out.println(obj.toString());
    }

}
