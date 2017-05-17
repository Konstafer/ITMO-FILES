package swingApp;

import javax.swing.*;
import java.awt.*;

final class App extends Thread {
  public static void main(String[] args) {
    SwingUtilities.invokeLater (new App());
  }

  public void run() {
    // Init main Frame
    JFrame frame = new JFrame("Java lab4");

    frame.setLayout(new GridLayout(2, 1));
    frame.setDefaultCloseOperation (JFrame.DISPOSE_ON_CLOSE);

    //swingApp.Graph
    Graph graph = new Graph();
    // Init Panel responsible for showing and changing current coordinates
    CoordinatesPanel coordsPanel = new CoordinatesPanel(graph);


    PointController pointController = new PointController(
        coordsPanel.getXField(),
        coordsPanel.getYField(),
        graph
    );
    JButton setButton = new JButton("Set");
    graph.addMouseListener(pointController);
    setButton.addActionListener(pointController);
    JPanel lowPanel = new JPanel();
    lowPanel.add(setButton);
    lowPanel.add(coordsPanel);
    frame.add(graph);
    frame.add(lowPanel);
    frame.setMinimumSize(new Dimension(300, 500));
    frame.setPreferredSize(new Dimension(300, 500));
    frame.pack();
    frame.setVisible(true);
  }
}
