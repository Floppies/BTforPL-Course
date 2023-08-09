import java.io.*;
import java.net.*;
import jatyc.lib.Typestate;

@Typestate("FileClient")
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
        File file = new File("Received by byte " + filename);
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        while(receive(fileOutputStream)){
            System.out.println("Receiving Byte");
        }
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    public boolean receive(FileOutputStream fileOutputStream) throws Exception {
        int byteRead;
        if ((byteRead = in.read()) != 0) {
            fileOutputStream.write(byteRead);
            return true;
        }
        else
            return false;
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

            client.request("example2.txt");

            client.close();
        } else {
            System.out.println("Could not start client!");
        }
    }
}
