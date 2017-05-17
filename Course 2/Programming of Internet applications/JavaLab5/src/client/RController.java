package client;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


class RController implements ChangeListener {
    private JTextField r;
    Graph graph;

    public RController(JTextField r, Graph graph) {
        this.r = r;
        this.graph = graph;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSlider source = (JSlider)e.getSource();
        r.setText(Integer.toString(source.getValue()));
        graph.setR(source.getValue());
    }
}
