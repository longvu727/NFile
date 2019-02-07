// A Java program for a Client
import java.net.*;
import java.io.*;
import java.util.Scanner;

public class NFileClient {
    // initialize socket and input output streams
    private Socket socket            = null;
    private Scanner  input   = null;
    private DataOutputStream out     = null;

    // constructor to put ip address and port
    public NFileClient(String address, int port)
    {
        // establish a connection
        try
        {
            socket = new Socket(address, port);
            System.out.println("Connected");

            // takes input from terminal
            input  = new Scanner(System.in);

            // sends output to the socket
            out    = new DataOutputStream(socket.getOutputStream());
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }

        // string to read message from input
        String line = "";

        // keep reading until "Over" is input
        while (!line.equals("Over")) {
            try {
                DataInputStream ois = new DataInputStream(socket.getInputStream());
                String message = ois.readUTF();
                System.out.println("Message: " + message);

                line = input.nextLine();
                out.writeUTF(line);
            }
            catch(IOException i) {
                System.out.println(i);
            }


        }
        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }

    public static void main(String args[])
    {
        NFileClient client = new NFileClient("127.0.0.1", 5000);
    }

}
