
package swingApp;

import java.util.function.Function;

class Area
    implements Areable {

  private Function<GraphPoint2D, Boolean> fitFunction; // This will check whether Y inside area or not
  private float[] interval;                       // Clone the interval array

  Area(Function<GraphPoint2D, Boolean> fitFunction, float[] interval) {
    if (interval.length != 2) {
      throw new RuntimeException("Interval should consist of 2 or more numbers");
    }
    this.fitFunction = fitFunction;
    this.interval = interval.clone();
  }
  Area(Function<GraphPoint2D, Boolean> fitFunction, float x0, float x1) {
    this.fitFunction = fitFunction;
    this.interval = new float[] {x0, x1};
  }

  public Boolean contains(GraphPoint2D point) {
    // If X coordinate of the point is out of the interval - no sense to check function
    if (!(point.X() >= this.interval[0] && point.X() <= this.interval[1])) return false;
    // Otherwise we know that X is inside interval, so we only check Y coordinate by some function
    return fitFunction.apply(point);
  }

  @Override
  public Area clone() {
    return new Area(this.fitFunction, this.interval.clone());
  }
}

