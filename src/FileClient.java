package com.example;
import java.io.*;
import java.net.*;

public class FileClient {
    private Socket socket;
    protected OutputStream out;
    protected BufferedReader in;
    protected int lastByte;

    public boolean start() {
        try {
            socket = new Socket("localhost", 1234);
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void request(String filename) throws Exception {
        out.write(("REQUEST\n" + filename + "\n").getBytes());
        out.flush();
    }

    public void receiveFile(String filename) throws Exception {
        File file = new File(filename);

        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            int byteRead;
            while ((byteRead = in.read()) != 0) {
                fileOutputStream.write(byteRead);
            }
            fileOutputStream.flush();
        }
    }

    public void close() throws Exception {
        out.write("CLOSE\n".getBytes());
        out.flush();
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        FileClient client = new FileClient();
        if (client.start()) {
            System.out.println("File client started!");

            // Request a file
            client.request("example.txt");
            client.receiveFile("received_example.txt");

            client.close();
        } else {
            System.out.println("Could not start client!");
        }
    }
}
