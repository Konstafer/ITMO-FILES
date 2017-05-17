package client;

import shared.GraphPoint;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.HashSet;
import java.util.Iterator;

public class Graph extends JPanel{

    Socket socket = Client.get();
    private static final Color
            BACKGROUND_COLOR = Color.WHITE,
            LINE_COLOR = Color.black,
            FIGURE_COLOR = Color.getHSBColor(25,86,55).darker().darker().darker().darker(),
            POINT_IN_COLOR = Color.magenta,
            POINT_OUT_COLOR = Color.black;

    private int h = 300;
    private float r = 25;
    public float step = 5;
    private server.Shape shape;
    private HashSet<GraphPoint> points = new HashSet<GraphPoint>();

    public  Graph() throws IOException {
        super();
        new Thread(new PointAppearAnimation(this, points)).start();
    }

    public void setR(double r) {
        this.r = (float) r;

        Iterator<GraphPoint> iter = points.iterator();
        try {
            checkPoints(iter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        this.step = h/2 / (this.r != 0 ? this.r : 1);
        drawGraph(g);
    }

    public String receiveMessage(int bufferSize) throws IOException {
        byte[] receiveData = new byte[bufferSize];
        InputStream sin = socket.getInputStream();
        DataInputStream in = new DataInputStream(sin);
        in.read(receiveData);
        return new String(receiveData);
    }

    public void sendMessage(String message) throws IOException {
        byte[] sendData = message.getBytes();
        OutputStream sout = socket.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(sendData);
        out.flush();

    }

    public void checkPoints(Iterator<GraphPoint> iter) throws Exception{
        Socket socket = new Socket();
        socket.setSoTimeout(30);
        GraphPoint point;
        Boolean fits;
        String receiveData;

        while (iter.hasNext()) {
            point = iter.next();
            sendMessage((point.X() + " " + point.Y() + " " + r));
            try {
                receiveData = receiveMessage(App.BUFFER_SIZE);
            } catch (SocketTimeoutException e) {
                point.setGray();
                socket.close();
                break;
            }
            fits = parseData(receiveData);
            if (fits != null) {
                point.changeColor(fits ? POINT_IN_COLOR : POINT_OUT_COLOR);
            }
        }
        socket.close();
    }

    private Boolean checkPoint(GraphPoint point) throws Exception {
        Socket socket = new Socket();
        socket.setSoTimeout(30);
        String receiveData;
        sendMessage((point.X() + " " + point.Y() + " " + r));
        try {
            receiveData = receiveMessage(App.BUFFER_SIZE);
        } catch (SocketTimeoutException e) {
            socket.close();
            return null;
        }
        Boolean result = parseData(receiveData);
        socket.close();
        return result;
    }

    public Boolean parseData (String data) {
        String[] info = data.split(" ");
        Boolean res = null;
        try {
            res = Boolean.parseBoolean(info[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public void drawGraph(Graphics g) {
        //              Background              //
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, 400, 400);

        //              Rect                //
        g.setColor(FIGURE_COLOR);
        if (r != 0) {
            g.fillRect(200, 200, 151, 151);
        }

        //              Triangle                //
        Polygon triangle = new Polygon();
        triangle.addPoint(200 + 75, 200 );
        triangle.addPoint(200 , 200 );
        triangle.addPoint(200  , 200 - 75);
        if (r != 0) {
            g.fillPolygon(triangle);
        }

        //              Arc             //
        if (r != 0) {
            g.fillArc(200 - 75, 200 - 75, 151, 151, 90, 90);
        }

        //              Coordinate lines                //
        g.setColor(LINE_COLOR);
        g.drawLine(200, 0, 200,  400);
        g.drawLine(0, 200, 400, 200);
        drawSteps(g);
        drawPoints(g);
    }

    private void drawSteps(Graphics g) {
        for (int i = 200; i <= 400; i += step) {
            g.drawLine(
                    i,
                    200 + 2,
                    i,
                    200 - 2);

            g.drawLine(
                    (400 - i),
                    200 + 2,
                    (400 - i),
                    200 - 2);
        }

        for (int i = 200; i <= 400; i += step) {
            g.drawLine(
                    200 + 1,
                    i,
                    200 - 1,
                    i);

            g.drawLine(
                    200 + 1,
                    (400 - i),
                    200 - 1,
                    (400 - i));
        }
    }

    public  Boolean addPoint(double x, double y) {
        GraphPoint point = new GraphPoint(x, y);
        Boolean fits = null;

        try {
            fits = checkPoint(point);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                socket = Client.get();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (fits != null) {
            point.color = fits ? POINT_IN_COLOR : POINT_OUT_COLOR;
        }

        points.add(point);
        this.repaint();
        return true;
    }

    private void drawPoint(Graphics g, GraphPoint point) {
        g.setColor(point.color);
        g.fillRect((int)(point.X() * step + (this.getWidth() / 2)), (int)(-point.Y() * step + (this.getHeight() / 2)), 3, 3);
    }

    private void drawPoints(Graphics g) {
        Iterator<GraphPoint> iter = points.iterator();
        while (iter.hasNext()) {
            GraphPoint point = iter.next();
            drawPoint(g, point);
        }
    }

    class PointAppearAnimation extends Thread {
        int timer;
        Graph graph;
        HashSet<GraphPoint> points;

        public PointAppearAnimation(Graph graph, HashSet<GraphPoint> points) {
            this.timer = 7000;
            this.points = points;
            this.graph = graph;
        }

        @Override
        public void run() {
            int steps = 10;
            float alphaInc = 1.0f / steps;
            while (true) {
                for (GraphPoint point: this.points) {
                    if (point.color.getAlpha() != 0 && point.color.getRed() != 0 && point.color != Color.gray ) point.decAlpha(alphaInc);
                }
                this.graph.repaint();
                try {
                    Thread.sleep(timer / steps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
