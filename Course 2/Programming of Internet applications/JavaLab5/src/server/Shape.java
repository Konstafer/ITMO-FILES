package server;

import shared.GraphPoint;

import java.util.ArrayList;
import java.util.Arrays;

public class Shape implements Areable {
    private ArrayList<Area> areas;

    Shape(Area[] areas) {
        this.areas = new ArrayList<Area>(Arrays.asList(areas));
}

    public Boolean contains(GraphPoint point2D) {
        return areas.stream()
                .anyMatch(area -> area.contains(point2D));
    }
}
