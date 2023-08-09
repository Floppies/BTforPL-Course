import java.io.*;
import java.net.*;
import jatyc.lib.Typestate;

@Typestate("FileServer")
public class FileServer {
    private Socket socket;
    protected OutputStream out;
    protected BufferedReader in;
    protected String lastFilename;

    public boolean start(Socket s) {
        try {
            socket = s;
            out = socket.getOutputStream();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean hasRequest() throws Exception {
        String command = in.readLine();
        if (command != null && command.equals("REQUEST")) {
            lastFilename = in.readLine(); // Read the filename from the client
            return true;
        }
        return false;
    }

    public String getLastFilename() {
        return lastFilename;
    }

    public void sendFile(String filename) throws Exception {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            sendEndOfTransmission();
            return;
        }

        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
                out.flush();
            }
        }

        sendEndOfTransmission();
    }

    private void sendEndOfTransmission() throws Exception {
        out.write(0);
        out.flush();
    }

    public void close() throws Exception {
        socket.close();
    }

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(1234);
        while (true) {
            new FileServerThread(serverSocket.accept()).start();
        }
    }
}
