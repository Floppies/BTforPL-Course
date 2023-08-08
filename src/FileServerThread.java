package com.example;
import java.net.*;

public class FileServerThread extends Thread {
    private Socket socket;

    public FileServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            FileServer server = new FileServer();
            if (server.start(socket)) {
                System.out.println("File server started!");

                // The server goes into hasRequest() to check if the client sent a file request.
                while (server.hasRequest()) {
                    String filename = server.getLastFilename();
                    System.out.println("File gooood!" + filename);
                    server.sendFile(filename);
                }

                server.close();
            } else {
                System.out.println("Could not start server!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

