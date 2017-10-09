package com.company;

import java.util.ArrayList;

public class Main {

    private static ArrayList<Vertex> generate(int N) {
        ArrayList<Vertex> arr = new ArrayList<Vertex>();

        for (int i = 0; i < N; ++i) {
            arr.add(new Vertex(i));
        }

        for (int i = 0; i < N; ++i) {
            Vertex source = arr.get(i);
            Vertex to = arr.get((int)Math.floor(Math.random()*arr.size()));
            source.to = to;
            to.in.add(source);
        }
        return arr;
    };

    public static void main(String[] args) {
        ArrayList<Vertex> input = generate(100000);
        System.out.println(input);
        ArrayList<Vertex> result = solve(input);
        for (Vertex v : result) {
            System.out.print(v.id + " ");
        }

    }

    private static ArrayList<Vertex> solve(ArrayList<Vertex> input) {
        ArrayList<Vertex> loops = findLoops(input);
        // Find all nodes pointing on themselfes and add them to answer
        for (Vertex v : loops) {
            input.remove(v);
        }

        // Find a node which has nothing pointing on it or pointing to nowhere - delete it
        for (int i = 0; i < input.size(); ++i) {
            Vertex cur = input.get(i);

            if (cur.in.size() == 0) {
                input.remove(cur);
                if (cur.to != null) {
                    cur.to.in.remove(cur);
                }
                i = -1;
            } else if (cur.to == null) {
                input.remove(cur);
                i = -1;
            }
        }

        //Everything what is left after clearing is the answer!
        loops.addAll(input);

        return loops;
    };

    private static ArrayList<Vertex> findLoops(ArrayList<Vertex> input) {
        ArrayList<Vertex> loops = new ArrayList<Vertex>();

        for (int i = 0; i < input.size(); ++i) {
            Vertex v = input.get(i);
            if (v.to == v) {
                loops.add(v);
            }
        }
        return loops;
    }
}
