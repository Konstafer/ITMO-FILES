package alg;

import graph.Vertex;

import java.util.ArrayList;
import java.util.List;

public class BinaryHeap {
    private List<Vertex> keys = new ArrayList<>();

    public void siftDown(int i) {
        while ((2 * i + 1) < (this.keys.size() - 1)) {
            int right = 2 * i + 2;
            int left = 2 * i + 1;
            int cur = left;
            if ((right < (this.keys.size() - 1)) && (this.keys.get(right).key < this.keys.get(left).key)) {
                cur = right;
            }
            if (this.keys.get(i).key <= this.keys.get(cur).key) {
                break;
            }
            swap(i, cur);
            i = cur;
        }
    }

    public void siftUp(int i) {
        while ((i > 0) && (keys.get(i).key < keys.get((i - 1) / 2).key)) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    public Vertex extractMin() {
        Vertex min = this.keys.get(0);
        swap(0, this.keys.size() - 1);
        this.keys.remove(this.keys.size() - 1);
        siftDown(0);
        return min;
    }

    public void add(Vertex vertex) {
        this.keys.add(vertex);
        vertex.heapIndex = this.keys.size() - 1;
        this.siftUp(this.keys.size() - 1);
    }

    void swap(int i, int j) {
        Vertex buff = this.keys.get(i);
        buff.heapIndex = j;
        this.keys.get(j).heapIndex = i;
        this.keys.set(i, this.keys.get(j));
        this.keys.set(j, buff);
    }

    public List<Vertex> getKeys() {
        return keys;
    }
}