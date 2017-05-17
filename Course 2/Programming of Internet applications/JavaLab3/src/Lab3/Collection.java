package Lab3;

import java.util.LinkedHashSet;

public class Collection {
    private LinkedHashSet<Vertice> coorditants = new LinkedHashSet<Vertice>();

    public void addToCollection() {
        coorditants.add(new Vertice(0,0));
        coorditants.add(new Vertice(-3,3));
        coorditants.add(new Vertice(-1,1));
        coorditants.add(new Vertice(3,-3));
        coorditants.add(new Vertice(-1,1));
        coorditants.add(new Vertice(-5,-5));
        coorditants.add(new Vertice(3,-3));
        coorditants.add(new Vertice(3,4));
        coorditants.add(new Vertice(0,-1));
    }

    public LinkedHashSet getCollection() {
        return coorditants;
    }

}
