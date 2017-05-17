package server;

import client.App;
import shared.GraphPoint;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServApp extends Thread {

    Socket s;

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(App.SERVER_PORT, 0,
                    InetAddress.getByName("localhost"));

            while (true) {
                new ServApp(server.accept());
            }
        } catch (Exception e) {
            System.out.println("init error" +e);
        }
    }

    public ServApp(Socket s) {
        this.s = s;
        start();
    }




    private server.Shape recountShape(int r) {
        return new server.Shape(new Area[] {
                new Area(point -> point.Y() >= -r // rect
                        && point.Y() <= 0,
                        0, r),
                new Area(point -> point.Y() <= -point.X() + r/2 // triangle
                        && point.Y() >= 0,
                        0, r/2),
                new Area(point -> point.Y() <= Math.pow(Math.pow(-r/2, 2) - Math.pow(point.X(), 2), 0.5) // circ
                        && point.Y() >= 0,
                        -r/2, 0)
        });
    }

    public String receiveMessage(int bufferSize) throws IOException {
        byte[] receiveData = new byte[bufferSize];
        InputStream sin = s.getInputStream();
        DataInputStream in = new DataInputStream(sin);
        in.read(receiveData);
        return new String(receiveData);
    }

    public void sendMessage(String message) throws IOException {
        byte[] sendData = message.getBytes();
        OutputStream sout = s.getOutputStream();
        DataOutputStream out = new DataOutputStream(sout);
        out.write(sendData);
        out.flush();

    }

    public void run() {
        float[] data = new float[3]; //x,y,r
        while (true) {

            String message = null;
            try {
                message = receiveMessage(App.BUFFER_SIZE).trim();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Pattern pattern = Pattern.compile("-?\\d+[.]\\d+");
            Matcher matcher = pattern.matcher(message);

            for (int i = 0; i < 3; i++) {
                if (!matcher.find()) {
                    throw new IllegalArgumentException();
                } else {
                    data[i] = new Float(matcher.group());
                }
            }

            Boolean fits = recountShape((int) data[2]).contains(new GraphPoint(data[0], data[1]));

            String result = String.format("%s %s", fits, (int) data[2]);
            try {
                sendMessage(result);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



}
