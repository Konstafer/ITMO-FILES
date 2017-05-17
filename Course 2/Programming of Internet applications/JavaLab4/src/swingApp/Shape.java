
package swingApp;

import java.util.ArrayList;
import java.util.Arrays;


/**
 * Class container for swingApp.Area objects
 */

public class Shape
    implements Areable {
  private ArrayList<Area> areas;

  Shape(Area[] areas) {
    this.areas = new ArrayList<Area>(Arrays.asList(areas));
  }

  public Boolean contains(GraphPoint2D point2D) {
    return areas.stream()
                .anyMatch(area -> area.contains(point2D));
  }
}

