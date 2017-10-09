package com.company;

import java.util.ArrayList;

public class Vertex {
    public ArrayList<Vertex> in;
    public Vertex to;
    public Integer id;

    public Vertex(Integer id) {
        this.in = new ArrayList<Vertex>();
        this.id = id;
    }

    @Override
    public String toString() {
        return String.format("%s %s", id, to.id);
    }
}
