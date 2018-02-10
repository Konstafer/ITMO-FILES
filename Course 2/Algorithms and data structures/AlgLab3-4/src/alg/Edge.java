package alg;

public class Edge implements Comparable<Edge> {
  public final int a;
  public final int b;

  public Edge(int a, int b) {
    if (a < b) {
      this.a = a;
      this.b = b;
    } else {
      this.a = b;
      this.b = a;
    }
  }

  @Override
  public boolean equals(Object obj) {
    if (obj.getClass() == this.getClass()) {
      Edge e = (Edge)obj;
      if (e.a == this.a && e.b == this.b || e.a == this.b && e.b == this.a) {
        return true;
      }
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 71 * hash + this.a;
    hash = 71 * hash + this.b;
    return hash;
  }

  @Override
  public int compareTo(Edge e) {
    if (a > b) {
      return -1;
    } else if (a < b) {
      return 1;
    } else {
      return 0;
    }
  }

  @Override
  public String toString() {
    return String.format("%s-%s", this.a, this.b);
  }
}
