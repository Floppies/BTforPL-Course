import java.io.*;
import java.net.*;
import jatyc.lib.Typestate;

@Typestate("FileClient2")
public class FileClient2 extends FileClient {
    public FileClient2() {
        super();
    }

    @Override
    public void request(String filename) throws Exception {
        out.write(("REQUEST\n" + filename + "\n").getBytes());
        out.flush();
        File file = new File("Received by line " + filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        StringBuilder lineBuilder = new StringBuilder();
        Ending readByte;
        while((readByte = receiveLines(writer, lineBuilder))!= Ending.EMPTY){
            if (readByte == Ending.END){
                System.out.println("End of transmission");
                break;
            }
            else if (readByte == Ending.JUMP){
                lineBuilder = new StringBuilder();
                System.out.println("Receiving line");
            }
        }
        writer.flush();
    }

    public Ending receiveLines(BufferedWriter writer, StringBuilder lineBuilder) throws Exception{
        int byteRead = in.read();
        if (byteRead == '\0') {
            writer.write(lineBuilder.toString());
            writer.newLine();
            lineBuilder.setLength(0);
            return Ending.END;
        } else if (byteRead == '\n') {
            writer.write(lineBuilder.toString());
            writer.newLine();
            lineBuilder.setLength(0);
            return Ending.JUMP;
        }else if (byteRead == -1) {
            return Ending.EMPTY;
        } else {
            lineBuilder.append((char) byteRead);
            return Ending.ANY;
        }
    }

    

    public static void main(String[] args) throws Exception {
        FileClient2 client = new FileClient2();
        if (client.start()) {
            System.out.println("File client started!");

            // Request a file and receive it line by line
            client.request("example.txt");
            client.request("example2.txt");

            client.close();
        } else {
            System.out.println("Could not start client!");
        }
    }
}
