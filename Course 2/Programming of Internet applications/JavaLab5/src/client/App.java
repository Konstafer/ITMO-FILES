package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public final class App extends Thread {
    public static final int SERVER_PORT = 4444;
    public static final String CLIENT_ADDR = "localhost";
    public static final int BUFFER_SIZE = 1024;
    private JFrame frame;
    private JButton setButton;
    private JButton localeButton;
    private ResourceBundle labelBundle;
    private ArrayList<Locale> locales = new ArrayList<Locale>();
    private Locale currentLocale;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new App());
    }

    public void run() {
        locales.add(new Locale("ru"));
        locales.add(new Locale("esp"));
        Locale.setDefault(locales.get(1));
        this.currentLocale = locales.get(1);
        labelBundle = ResourceBundle.getBundle("Language_ru");

        Graph graph = null;

        try {
            graph = new Graph();
        } catch (IOException e) {
            e.printStackTrace();
        }
        CoordinatesPanel coordsPanel = new CoordinatesPanel(graph);

        //              ServApp Frame              //
        this.frame = new JFrame(labelBundle.getString("Title"));
        frame.setLayout(new GridLayout(1, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setMinimumSize(new Dimension(800, 400));
        frame.setPreferredSize(new Dimension(800, 400));
        frame.setVisible(true);

        //              client.Graph               //
        frame.add(graph);

        //              Bottom Panel              //
        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.PAGE_AXIS));
        this.setButton = new JButton(ResourceBundle.getBundle("Language_ru").getString("SetKey"));
        setButton.setPreferredSize(new Dimension(200,40));
        coordsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(coordsPanel);
        setButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        coordsPanel.setLayout(new BoxLayout(coordsPanel, BoxLayout.PAGE_AXIS));
        bottomPanel.add(setButton);
        this.localeButton = new JButton(ResourceBundle.getBundle("Language_ru").getString("Language"));
        localeButton.setPreferredSize(new Dimension(200,40));
        localeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        localeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLocale();
            }
        });
        bottomPanel.add(localeButton);
        frame.add(bottomPanel);

        PointController pointController = new PointController(
                coordsPanel.getXField(),
                coordsPanel.getYField(),
                graph
        );

        graph.addMouseListener(pointController);
        setButton.addActionListener(pointController);




    }

    public void changeLocale() {
        currentLocale = locales.get((locales.indexOf(currentLocale) + 1)  % locales.size());
        Locale.setDefault(currentLocale);
        ResourceBundle.clearCache();
        this.localeButton.setText(ResourceBundle.getBundle("Language", currentLocale).getString("Language"));
        this.setButton.setText(ResourceBundle.getBundle("Language", currentLocale).getString("SetKey"));
        this.frame.setTitle(ResourceBundle.getBundle("Language", currentLocale).getString("Title"));
    }
}
