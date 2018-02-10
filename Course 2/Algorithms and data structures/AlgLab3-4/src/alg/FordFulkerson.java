package alg;

import java.lang.*;
import java.util.LinkedList;

class FordFulkerson {
  private int size;
  private int[][] graph;
  public FordFulkerson(int V, int[][] graph) {
    this.size = V;
    this.graph = graph;
  }

  private boolean unfilledPathExists(int rGraph[][], int source, int destination, int parent[]) {

    boolean visited[] = new boolean[size];
    for(int i = 0; i < size; ++i) {
      visited[i] = false;
    }

    LinkedList<Integer> queue = new LinkedList<Integer>();
    queue.add(source);
    visited[source] = true;
    parent[source] = -1;

    while (queue.size()!=0) {
      int u = queue.poll();

      for (int vertexId = 0; vertexId < size; vertexId++) {
        if (visited[vertexId]==false && rGraph[u][vertexId] > 0) {
          queue.add(vertexId);
          parent[vertexId] = u;
          visited[vertexId] = true;
        }
      }
    }

    return (visited[destination] == true);
  }

  int solve(int source, int destination) {
    int i, curVertex;

    int rGraph[][] = new int[size][size];

    for (i = 0; i < size; i++) {
      for (curVertex = 0; curVertex < size; curVertex++) {
        rGraph[i][curVertex] = graph[i][curVertex];
      }
    }

    int parent[] = new int[size];

    int max_flow = 0;

    while (unfilledPathExists(rGraph, source, destination, parent)) {
      int path_flow = Integer.MAX_VALUE;

      for (curVertex = destination; curVertex != source; curVertex = parent[curVertex]) {
        i = parent[curVertex];
        path_flow = Math.min(path_flow, rGraph[i][curVertex]);
      }

      for (curVertex = destination; curVertex != source; curVertex = parent[curVertex]) {
        i = parent[curVertex];
        rGraph[i][curVertex] -= path_flow;
        rGraph[curVertex][i] += path_flow;
      }

      max_flow += path_flow;
    }

    return max_flow;
  }

//  private static void generateWeights(int[][] graph, int maxW) {
//
//    for (int i = 0; i < graph.length; ++i) {
//      for (int j = i; j < graph.length; ++j) {
//        graph[i][j] *= Math.ceil(Math.random()*maxW);
//      }
//    }
//  }
  public static void main (String[] args) throws java.lang.Exception {
    int graph[][] = new int[][] {
        {0, 7,  3, 1, 0, 0},
        {0, 0,  0, 10, 5, 0},
        {0, 0, 0, 0, 4, 0},
        {0, 0,  0, 7, 0, 4},
        {0, 0, 0, 0, 0, 8},
        {0, 0, 0, 0, 0, 0}
    };
    //generateWeights(graph, 10);

    FordFulkerson m = new FordFulkerson(6, graph);
    System.out.print("Max flow: ");
    System.out.print(m.solve(0, 5));
  }
}