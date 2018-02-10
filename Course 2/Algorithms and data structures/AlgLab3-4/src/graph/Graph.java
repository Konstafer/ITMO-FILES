package graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

}
