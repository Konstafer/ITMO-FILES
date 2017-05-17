
package swingApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class PointController implements ActionListener, MouseListener {
  JTextField x, y;
  Graph graph;
  public PointController(JTextField x, JTextField y, Graph graph) {
    this.x = x;
    this.y = y;
    this.graph = graph;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    //set button is pressed(or such event fired)
    try {
      graph.addPoint(Double.parseDouble(x.getText()), Double.parseDouble(y.getText()));
    } catch (Exception e1) {
      e1.printStackTrace();
    }
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    int x = e.getX();
    int y = e.getY();
    x -= graph.getWidth() / 2;
    y -= graph.getHeight() / 2;
    y *= -1;
    double realX = (double)x / graph.step;
    double realY = (double)y / graph.step;
    this.x.setText(Double.toString(Math.round(realX * 10000) / 10000.0));
    this.y.setText(Double.toString(Math.round(realY * 10000) / 10000.0));
    Boolean fits = graph.addPoint(realX, realY);
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }
}
