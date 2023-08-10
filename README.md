# BTforPL-Course

## Description

This is the final assignment for the course “Behavioural Types for Mainstream PLs” given in ECI2023.

## Requeriments

### FileServer

- The aim is that clients can request files from it.
- The communication will be implemented using java.net.Socket or java.nio.channels classes and will follow a specific byte protocol:
    1. the client sends the string “REQUEST\n” to start a request;
    2. the client sends the name of the file followed by “\n”;
    3. if the file exists, the server responds by sending to the client each one of the bytes of the file;
    4. if the file does not exist, or when the end of the file is reached, the server sends the 0 byte;
    5. after the client receives the 0 byte, it can start a new request (see steps 1 and 2), or send the string “CLOSE\n” to finish the protocol.

### Typestates - Part one

1. Design protocols for classes FileClient and FileServer. Correct usages of the protocols imply that both parties will communicate according to the description above.
2. The server’s protocol should be specified so that each byte of a file is sent at a time (i.e. creating a method that accepts a string with the full contents of the file at once is not the
assignment).
3. In the same manner, the client’s protocol should have methods to retrieve the contents of the file one by one. After specifying the typestates, implement the classes (complete the code skeletons provided, assuming socket communication is always stable – no exceptions from reading and writing to input or output streams).

### Typestates - Part two

- Create a FileClient2 class that extends FileClient with a method to retrieve the file contents line by line instead of byte by byte.
- For this part, you need to create a new method and protocol for FileClient2, correctly extending the protocol of FileClient. The FileServer class should remain as implemented in Part 1.

## Designing the Typestates

### FileServer

<img src="images/automaton.png" alt="File Server" width="400"/>

The behavior is stated in FileServer.protocol

### FileClient

<img src="images/automaton2.png" alt="File Client" width="400"/>

The behavior is stated in FileClient.protocol

### FileClient2

<img src="images/automaton3.png" alt="File Client 2" width="400"/>

The behavior is stated in FileClient2.protocol
