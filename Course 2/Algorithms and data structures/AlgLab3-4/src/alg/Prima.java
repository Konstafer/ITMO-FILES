package alg;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.util.List;


public class Prima {
    public static void solve(Graph graph, int s, List<Edge> minimumTree) {
        minimumTree.clear();
        for (int i = 0; i < graph.getVertexes().size(); i++) {
            minimumTree.add(null);
        }
        BinaryHeap heap = new BinaryHeap();

        for (int i = 0; i < graph.getVertexes().size(); i++) {
            Vertex vertex = graph.getVertexes().get(i);
            vertex.key = Double.POSITIVE_INFINITY;
            if (i == s) {
                vertex.key = 0;
            }
            heap.add(vertex);
        }

        while (heap.getKeys().size() > 0) {
            Vertex vertex = heap.extractMin();
            for (int i = 0; i < vertex.getIncidentEdges().size(); i++) {
                Vertex next = vertex.getIncidentEdges().get(i).getEndVertex(vertex);
                Edge e = vertex.getIncidentEdges().get(i);
                if (next.key > e.getWeight() && next.heapIndex < heap.getKeys().size()) {
                    next.key = vertex.getIncidentEdges().get(i).getWeight();
                    minimumTree.set(next.index, e);
                    heap.siftUp(next.heapIndex);
                }
            }
        }
    }
}
