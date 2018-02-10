package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
    private List<Edge> incidentEdges = new ArrayList<>();
    public double key;
    public int heapIndex;
    public int index;

    public Vertex(int i) {
        index = i;
    }

    public List<Edge> getIncidentEdges() {
        return incidentEdges;
    }

}