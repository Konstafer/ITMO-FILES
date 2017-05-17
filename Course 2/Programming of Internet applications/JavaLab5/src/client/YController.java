package client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class YController implements ActionListener{
    private JTextField y;

    YController(JTextField y) {
        this.y = y;
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        Double value = new Double(y.getText());
        if (command.equals("Y -= 1")) {
            value -= 1;
        } else if (command.equals(("Y += 1"))) {
            value += 1;
        }
        y.setText(value.toString());
    }
}
