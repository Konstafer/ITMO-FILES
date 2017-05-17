package swingApp;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;


class Graph extends JPanel{

  private static final Color
    BACKGROUND_COLOR = Color.WHITE,
    LINE_COLOR = Color.black,
    FIGURE_COLOR = Color.getHSBColor(25,86,55).darker().darker().darker().darker(),
    POINT_IN_COLOR = Color.magenta,
    POINT_OUT_COLOR = Color.black;

  private Shape shape;
  public int step = 5;
  public int r = 25;
  private int h = 300;
  private int w = 300;
  private int fitPoints = 0;
  private HashSet<GraphPoint2D> points = new HashSet<GraphPoint2D>();

  public Graph() {
    super();
    this.shape = recountShape();
    new Thread(new PointAppearAnimation(this, points)).start();
  }


  private Shape recountShape() {
    return new Shape(new Area[] {
            new Area(graphPoint2D -> graphPoint2D.Y() <= r // rect
                    && graphPoint2D.Y() <= 0,
                    0, r),
            new Area(graphPoint2D -> graphPoint2D.Y() <= -graphPoint2D.X() + r/2//triangle
                    && graphPoint2D.Y() >= 0,
                    0, r/2),
            new Area(graphPoint2D -> graphPoint2D.Y() <= Math.pow(Math.pow(-r/2, 2) - Math.pow(graphPoint2D.X(), 2), 0.5) // circ
                    && graphPoint2D.Y() >= 0,
                    -r/2, 0)
    });
  }

  public int getStep() {
    return this.step;
  }

  @Override
  public void paintComponent(Graphics g) {
    this.h = this.getHeight();
    this.w = this.getWidth();
    this.step = h /2  / (this.r != 0 ? this.r : 1);
    super.paintComponent(g);
    drawGraph(g);
  }

  public void setR(int r) {
    this.r = r;
    this.shape = recountShape();
    this.repaint();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(400, 400);
  }

  public Boolean addPoint(double x, double y) {
    GraphPoint2D point = new GraphPoint2D(x, y);

    Boolean fits = this.shape.contains(point);
    Color pointColor = fits ? POINT_IN_COLOR : POINT_OUT_COLOR;
    point.color = pointColor;
    point.makeInvisible();
    points.add(point);
    this.repaint();
    return fits;
  }



  private void drawPoints(Graphics g) {
    Iterator<GraphPoint2D> iter = points.iterator();
    while (iter.hasNext()) {
      GraphPoint2D point = iter.next();
      point.changeColor(this.shape.contains(point) ? POINT_IN_COLOR : POINT_OUT_COLOR); //set color point to be black or magenta
      drawPoint(g, point);
    }
  }
  private void drawPoint(Graphics g, GraphPoint2D point) {
    g.setColor(point.color);
    g.fillRect((int)(point.X() * step + (this.getWidth() / 2)), (int)(-point.Y() * step + (this.getHeight() / 2)), 3, 3);
  }
  private void drawSteps(Graphics g) {
    g.setColor(LINE_COLOR);
    for (int i = w / 2; i <= w; i += step) {
      g.drawLine(
          i,
          h / 2 + 2,
          i,
          h/ 2 - 2);

      g.drawLine(
          (w - i),
          h / 2 + 2,
          (w - i),
          h / 2 - 2);
    }

    for (int i = h / 2; i <= h; i += step) {
      g.drawLine(
          w / 2 + 1,
          i,
          w / 2 - 1,
          i);

      g.drawLine(
          w / 2 + 1,
          (h - i),
          w / 2 - 1,
          (h - i));
    }
  }
  private void drawGraph(Graphics g) {
    g.setColor(BACKGROUND_COLOR);
    g.fillRect(0, 0, w, h);
    g.setColor(FIGURE_COLOR);
    //rect
    g.fillRect(w/2, h/2,  r*step, r*step);

    Polygon triangle = new Polygon();
    triangle.addPoint(w/2 + r*step / 2, h/2 );
    triangle.addPoint(w/2 , h/2 );
    triangle.addPoint(w/2  , h/2 - r*step / 2);
    g.fillPolygon(triangle);

    g.fillArc(w/2- (int)(r * step / 2), h/2 - (int)(r * step / 2), (int)(r * step), (int)(r * step), 90, 90);
    g.setColor(LINE_COLOR);

    //Coordinate lines
    g.drawLine(w / 2, 0, w / 2,  h);
    g.drawLine(0, h / 2, w, h / 2);
    drawSteps(g);
    //Draw points
    drawPoints(g);
  }

  class PointAppearAnimation extends Thread {
    int timer;
    Graph graph;
    HashSet<GraphPoint2D> animatedPoints;
    public PointAppearAnimation(Graph graph, HashSet<GraphPoint2D> animatedPoints) {
      this.timer = 7000;
      this.animatedPoints = animatedPoints;
      this.graph = graph;
    }

    @Override
    public void run() {
      int steps = 10;
      float alpha = 0;
      float alphaInc = 1.0f / steps;
      while (true) {
        for (GraphPoint2D point2D: this.animatedPoints) {
          if (point2D.color.getAlpha() != 0) point2D.decAlpha(alphaInc);
        }
        this.graph.repaint();
        try {
          Thread.sleep(timer / steps);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}

