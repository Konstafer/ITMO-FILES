package client;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class CoordinatesPanel extends JPanel  {
    private JTextField x;
    private  JTextField y;
    private  JTextField r;
    private XController xController;
    private YController yController;
    private RController rController;
    private Double[] presetX = {1.0, 2.0, 3.0, 4.0, 5.0};
    private static final Integer
            MIN_R = 0,
            MAX_R = 50,
            INIT_R = MAX_R / 2;


    public CoordinatesPanel(Graph graph) {
        //              "R" Panel               //
        JPanel rPanel = new JPanel();
        rPanel.setLayout(new BoxLayout(rPanel, BoxLayout.PAGE_AXIS));
        rPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.r = new JTextField(Integer.toString((INIT_R)));
        r.setLayout(new BoxLayout(r, BoxLayout.PAGE_AXIS));
        r.setAlignmentX(Component.CENTER_ALIGNMENT);
        r.setEditable(false);
        r.setHorizontalAlignment(JTextField.CENTER);
        JLabel rLabel = new JLabel("R:");
        rLabel.setLayout(new BoxLayout(rLabel, BoxLayout.PAGE_AXIS));
        rLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        rLabel.setLabelFor(r);

        JSlider rSlider = new JSlider(JSlider.VERTICAL, MIN_R, MAX_R, INIT_R);
        Font sliderFont = new Font("Serif", Font.BOLD, 13);
        rSlider.setFont(sliderFont);
        rSlider.setPaintLabels(true);
        rSlider.setMajorTickSpacing(5);
        rSlider.setMinorTickSpacing(1);
        rSlider.setPaintTicks(true);
        rSlider.setSnapToTicks(true);
        rSlider.setPaintTrack(false);
        rSlider.setSize(400 , 200);
        rPanel
                .setSize(400 , 200);
        Hashtable labelTable = new Hashtable();
        labelTable.put(MIN_R, new JLabel(MIN_R.toString()));
        labelTable.put(INIT_R, new JLabel(INIT_R.toString()));
        labelTable.put(MAX_R, new JLabel(MAX_R.toString()));
        rSlider.setLabelTable(labelTable);

        this.rController = new RController(r, graph);
        rSlider.addChangeListener(rController);
        rPanel.add(rLabel);
        rPanel.add(r);
        rPanel.add(rSlider);
        this.add(rPanel);

        //              "X" Panel               //
        JLabel xLabel = new JLabel("X:");
        xLabel.setLayout(new BoxLayout(xLabel, BoxLayout.PAGE_AXIS));
        this.x = new JTextField("0");
        x.setEditable(false);
        x.setHorizontalAlignment(JTextField.CENTER);
        this.add(xLabel);
        this.add(x);

        JComboBox xCoordinates = new JComboBox<Double>(presetX);
        xCoordinates.setAlignmentX(Component.CENTER_ALIGNMENT);
        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.PAGE_AXIS));
        xPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        xPanel.add(xLabel);
        xPanel.add(x);
        xPanel.add(xCoordinates);
        this.add(xPanel);
        this.xController = new XController(x);
        xCoordinates.addActionListener(xController);

        //              "Y" Panel               //
        JLabel yLabel = new JLabel("Y:");
            yLabel.setLayout(new BoxLayout(yLabel, BoxLayout.PAGE_AXIS));
            yLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        this.y = new JTextField("0");
        y.setEditable(false);
        y.setHorizontalAlignment(JTextField.CENTER);
        this.yController = new YController(y);
        JPanel yCheckBoxes = new JPanel();
            yCheckBoxes.setLayout(new BoxLayout(yCheckBoxes, BoxLayout.X_AXIS));
            yCheckBoxes.setAlignmentX(Component.CENTER_ALIGNMENT);
        JCheckBox yMinus = new JCheckBox("Y -= 1");
        yMinus.addActionListener(yController);
        yCheckBoxes.add(yMinus);

        JCheckBox yPlus = new JCheckBox("Y += 1");
        yPlus.addActionListener(yController);
        yCheckBoxes.add(yPlus);

        JPanel yPanel = new JPanel();
            yPanel.setLayout(new BoxLayout(yPanel, BoxLayout.Y_AXIS));
            yPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        yPanel.add(yLabel);
        yPanel.add(y);
        yPanel.add(yCheckBoxes);
        this.add(yPanel);
    }

    public JTextField getXField() {
        return x;
    }
    public JTextField getYField() {
        return y;
    }
}
