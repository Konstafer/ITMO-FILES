package server;

import java.util.function.Function;
import shared.GraphPoint;

public class Area implements Areable {

    private Function<GraphPoint, Boolean> fitFunction;

    private float[] interval;

    Area(Function<GraphPoint, Boolean> fitFunction, float[] interval) {
        if (interval.length != 2) {
            throw new RuntimeException("Interval should consist of 2 or more numbers");
        }
        this.fitFunction = fitFunction;
        this.interval = interval.clone();
    }

    Area(Function<GraphPoint, Boolean> fitFunction, float x0, float x1) {
        this.fitFunction = fitFunction;
        this.interval = new float[] {x0, x1};
    }

    public  Boolean contains(GraphPoint point) {
        if (!(point.X() >= this.interval[0] && point.X() <= this.interval[1])) return false;
        return fitFunction.apply(point);
    }

    public Area clone() {
        return new Area(this.fitFunction, this.interval.clone());
    }
}
