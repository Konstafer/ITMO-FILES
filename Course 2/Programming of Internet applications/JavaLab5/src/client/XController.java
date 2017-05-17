package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class XController implements ActionListener {
    private JTextField x;

    XController(JTextField x) {
        this.x = x;
    }

    public void actionPerformed(ActionEvent e) {
        x.setText(
                ((JComboBox)(e.getSource())).getSelectedItem().toString()
        );
    }
}
