package swingApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class XController implements ActionListener {
  private JTextField x;

  public XController(JTextField x) {
    this.x = x;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    x.setText(
            ((JComboBox)(e.getSource())).getSelectedItem().toString()
    );
  }
}


