package graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Generator {
    public Graph generateGraph(int vertexesCount, int edgesCount, int minWeight, int maxWeight) {
        Graph graph = new Graph();
        Random random = new Random();
        List<Vertex> inGraph = new ArrayList<>();
        List<Vertex> notInGraph = new ArrayList<>();
        graph.setEdges(new ArrayList<>());

        for (int i = 0; i < vertexesCount; i++) {
            notInGraph.add(new Vertex(i));
        }

        Vertex curr = notInGraph.get(random.nextInt(notInGraph.size()));
        notInGraph.remove(curr);
        inGraph.add(curr);

        while (!notInGraph.isEmpty()) {
            Vertex vert = notInGraph.get(random.nextInt(notInGraph.size()));
            notInGraph.remove(vert);
            inGraph.add(vert);
            Edge edge = new Edge(random.nextInt((maxWeight - minWeight) + minWeight), curr, vert);
            curr.getIncidentEdges().add(edge);
            vert.getIncidentEdges().add(edge);
            graph.getEdges().add(edge);
            edgesCount--;
            curr = vert;
        }

        while (edgesCount > 0) {
            Vertex from = inGraph.get((random.nextInt(inGraph.size())));
            Vertex to = inGraph.get((random.nextInt(inGraph.size())));
            if (from != to) {
                Edge edge = new Edge(random.nextInt((maxWeight - minWeight) + minWeight), to, from);
                from.getIncidentEdges().add(edge);
                to.getIncidentEdges().add(edge);
                graph.getEdges().add(edge);
                edgesCount--;
            }
        }

        graph.setVertexes(inGraph);
        return graph;
    }
}
