package alg;

import java.util.*;

public class Boruvka {

  LinkedList<TreeSet<Integer>> trees;
  ArrayList<Edge> minimumTree;
  Integer[][] graph;

  public Boruvka(Integer[][] graph) {
    this.trees = new LinkedList<>();
    this.minimumTree = new ArrayList<>();
    this.graph = graph.clone();
    for (int i = 0; i < graph.length; ++i) {
      this.graph[i] = graph[i].clone();
    }
    initializeSets(graph.length);
  }

  public void run() {
    Graph graph = new Graph(this.graph);

    //пока множество деревьев не состоит из 1
    int i = -1;
    while (trees.size() > 1) {
      // для каждого дерева из множества
      i += 1;
      if (trees.size() <= i) {
        i = 0;
      }

      TreeSet<Integer> tree = trees.get(i);
      Integer min = Integer.MAX_VALUE;

      //минимальное ребро из этого дерева, связывающее его с другим деревом
      Integer v1Index = null;
      Integer v2Index = null;

      // для каждой вершины в множестве
      for (Integer v1 : tree) {
        // найти минимальное по весу ребро
        Integer v2 = graph.lowestEdge(v1);
        if (v2 == null) {
          continue;
        }
        Integer weight = this.graph[v1][v2];

        if (weight <= min) {
          v1Index = v1;
          v2Index = v2;
        }
      }

      if (v2Index != null && this.graph[v2Index][v1Index] != null) {
        // найти какому дереву принадлежит вторая вершина и обьединить
        for (TreeSet<Integer> tree_ : trees) {
          if (tree_.contains(v2Index)) {
            uniteSets(tree, tree_);
            minimumTree.add(new Edge(v1Index, v2Index));
            break;
          }
        }
      } else {
        System.out.println("Minimum tree can not be built.");
        break;
      }
    }
  }

  private void initializeSets(int size) {
    for (Integer i = 0; i < size; ++i) {
      TreeSet<Integer> tree = new TreeSet<>();
      tree.add(i);
      this.trees.add(tree);
    }
  }

  private void uniteSets(TreeSet<Integer> tree1, TreeSet<Integer> tree2) {
    for (Integer vertA: tree1) {
      for (Integer vertB: tree2) {
        this.graph[vertA][vertB] = null;
        this.graph[vertB][vertA] = null;
      }
    }

    tree1.addAll(tree2);
    this.trees.remove(tree2);
  }
}
