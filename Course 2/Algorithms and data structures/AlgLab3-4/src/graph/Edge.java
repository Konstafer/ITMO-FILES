package graph;

public class Edge {
    private int weight;
    private Vertex firstVertex;
    private Vertex secondVertex;

    public Integer getWeight() {
        return weight;
    }

    public Vertex getEndVertex(Vertex vert) {
        return vert == firstVertex ? secondVertex : firstVertex;
    }

    public Edge(int weight, Vertex firstVertex, Vertex startVertex) {
        this.weight = weight;
        this.firstVertex = firstVertex;
        this.secondVertex = startVertex;
    }
}