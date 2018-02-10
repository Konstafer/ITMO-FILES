package alg;

import graph.Graph;
import graph.Generator;



import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("n\t\t\tTime");
        for (int i = 101; i < 10000 + 1; i += 100) {
            testPrim(i, i*100);
        }
        System.out.println("################################################");
        for (int i = 101; i < 10000 + 1; i += 100) {
            testPrim(i, i*1000);
        }

//        System.out.println("################################################");
//        for (int i = 101; i < 10000 + 1; i += 100) {
//            testBoruvka(i, i*100);
//        }
//
//        System.out.println("################################################");
//        for (int i = 101; i < 10000 + 1; i += 100) {
//            testBoruvka(i, i * 1000);
//        }
    }

    private static void testPrim(Integer size, Integer nEdges) {
        Graph graph = new Generator().generateGraph(size, nEdges, 1, 1000000);
        List<graph.Edge> minTree = new ArrayList<>();

        long begin = System.nanoTime();
        Prima.solve(graph, 0, minTree);
        long end = System.nanoTime();

        System.out.println(size+"\t\t\t"+TimeUnit.NANOSECONDS.toMillis(end - begin));
        System.gc();
    }

    private static void testBoruvka(Integer size, Integer nEdges) {
        Integer[][] generated = generateGraph(size, 1000000, nEdges);
        Boruvka alg = new Boruvka(generated);

        long begin = System.nanoTime();
        alg.run();
        long end = System.nanoTime();

        System.out.println(size+"\t\t\t"+TimeUnit.NANOSECONDS.toMillis(end - begin));
        System.gc();
    }

    private static Integer[][] generateGraph(int size, int maxWeight, int nEdges) {
        Integer[][] graph = new Integer[size][size];

        Random rand = new Random(43);

        int edgesLeft = nEdges;

        for (int i = 0; i < size; ++i) {
            graph[i][i] = null;
            for (int j = i + 1; j < size; ++j) {
                if (edgesLeft == 0) {
                    graph[i][j] = null;
                    graph[j][i] = null;
                } else {
                    graph[i][j] = 1 + (int)Math.floor(rand.nextDouble() * maxWeight);
                    graph[j][i] = graph[i][j];
                    edgesLeft--;
                }
            }
        }
        return graph;
    }

//    private static void printGraph(Integer[][] graph) {
//        if (graph.length > 10) return;
//        for (int i = 0; i < graph.length; ++i) {
//            for (int j = 0; j < graph.length; ++j) {
//                System.out.print(graph[i][j] + " ");
//            }
//            System.out.println("");
//        }
//    }
}
