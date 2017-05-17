
package swingApp;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

class CoordinatesPanel extends JPanel {
  private JTextField y;
  private JTextField x;
  private YController yController;
  private XController xController;
  private RController rController;
  private Double[] presetX = {1.0, 2.0, 3.0, 4.0, 5.0};
  static final Integer
      MIN_R = 0,
      MAX_R = 50,
      INIT_R = MAX_R / 2;

  public CoordinatesPanel(Graph graph) {
      super();
    this.setPreferredSize(new Dimension(200, 50));
    JLabel yLabel = new JLabel("Y:");
    yLabel.setLayout(new BoxLayout(yLabel, BoxLayout.PAGE_AXIS));
      yLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
    this.y = new JTextField("0");
    y.setEditable(false);

    this.add(yLabel);

    JLabel xLabel = new JLabel("X:");
    xLabel.setLayout(new BoxLayout(xLabel, BoxLayout.PAGE_AXIS));
    this.x = new JTextField("0");
    x.setEditable(false);

    this.add(y);

    // Y PANEL
    JPanel yCheckBoxes = new JPanel();
      yCheckBoxes.setLayout(new BoxLayout(yCheckBoxes, BoxLayout.X_AXIS));
      yCheckBoxes.setAlignmentX(Component.LEFT_ALIGNMENT);
    JCheckBox yDirection = new JCheckBox("Y -= 1");
    this.yController = new YController(y);
    yDirection.addActionListener(yController);

    JCheckBox yChange = new JCheckBox("Y += 1");
    yChange.addActionListener(yController);
    yCheckBoxes.add(yDirection);
    yCheckBoxes.add(yChange);

    JPanel yPanel = new JPanel();
      yPanel.setLayout(new BoxLayout(yPanel, BoxLayout.Y_AXIS));
      yPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    yPanel.add(yLabel);
    yPanel.add(y);
    yPanel.add(yCheckBoxes);
    this.add(yPanel);

    this.add(xLabel);
    this.add(x);

    // X PANEL
    JComboBox xCoordinates = new JComboBox<Double>(presetX);
    JPanel xPanel = new JPanel();
      xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.PAGE_AXIS));
      xPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    xPanel.add(xLabel);
    xPanel.add(x);
    xPanel.add(xCoordinates);
    this.add(xPanel);
    this.xController = new XController(x);
    xCoordinates.addActionListener(xController);


    // R PANEL
    JPanel rPanel = new JPanel();
      rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.PAGE_AXIS));
      rPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    JTextField rField = new JTextField(Integer.toString((MAX_R / 2)));
    rField.setEditable(false);
    JLabel rLabel = new JLabel("R:");
    rLabel.setLabelFor(rField);

    JSlider rSlider = new JSlider(JSlider.HORIZONTAL, MIN_R, MAX_R, INIT_R);
    Font sliderFont = new Font("Serif", Font.BOLD, 13);
    rSlider.setFont(sliderFont);
    Hashtable labelTable = new Hashtable();
    labelTable.put(MIN_R, new JLabel(MIN_R.toString()));
    labelTable.put(MAX_R/2, new JLabel(Integer.toString((MAX_R / 2))));
    labelTable.put(MAX_R, new JLabel(MAX_R.toString()));
    rSlider.setLabelTable(labelTable);
    rSlider.setPaintLabels(true);
    RController rController = new RController(rField, graph);
    rSlider.addChangeListener(rController);
    rPanel.add(rLabel);
    rPanel.add(rField);
    rPanel.add(rSlider);
    this.add(rPanel);
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(200, 200);
  }
  public JTextField getXField() {
    return x;
  }
  public JTextField getYField() {
    return y;
  }
}

