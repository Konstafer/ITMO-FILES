package alg;

import java.util.ArrayList;
import java.util.Arrays;


public class Graph {
  Integer[][] graph;

  public Graph(Integer[][] arr) {
    this.graph = arr;
  }

  public Integer lowestEdge(Integer vertex) {
    ArrayList<Integer> v = new ArrayList<>(Arrays.asList(graph[vertex]));
    Integer min = Integer.MAX_VALUE;
    Integer v2 = null;

    for (int i = 0; i < v.size(); ++i) {
      if (v.get(i) != null && v.get(i) < min) {
        v2 = i;
        min = v.get(i);
      }
    }

    return v2;
  }
}
