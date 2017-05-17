
package swingApp;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

class RController implements ChangeListener {
  JTextField r;
  Graph graph;
  public RController(JTextField r, Graph graph) {
    this.r = r;
    this.graph = graph;
  }

  @Override
  public void stateChanged(ChangeEvent event) {
    JSlider source = (JSlider)event.getSource();
    r.setText(Integer.toString(source.getValue()));
    graph.setR(source.getValue());
  }
}

