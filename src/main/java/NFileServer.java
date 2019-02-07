// A Java program for a Server
import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
//import org.apache.commons.cli.*;

public class NFileServer extends Thread
{
    //initialize socket and input stream 
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       = null;
    private DataOutputStream out     = null;

    public boolean _DEBUG = false;

    public  NFileServer() {} //unit test

    // constructor with port 
    public NFileServer( Socket socket )
    {
        this.socket = socket;
        // starts server and waits for a connection
        try
        {
            this._debug("Server started");
            this._debug("Waiting for a client ...");

            // takes input from the client socket 
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream())
            );

            out = new DataOutputStream( socket.getOutputStream() );
            out.writeUTF("Hi Client ");

            //this.startService();

        }
        catch(IOException i)
        {
            this._debug(i.toString());
        }
    }

    private void _debug(String msg) {
        if(this._DEBUG) System.out.println(msg);
    }

    public void run() {
        this.startService();
    }

    private void stopService() {
        this._debug("Closing connection");

        try {
            // close connection
            if(socket != null) socket.close() ;
            if(in != null) in.close();
            if(out != null) out.close();
        }
        catch (IOException e) {
            ;
        }
    }

    private void startService() {
        String line = "";

        this._debug("Client accepted");

        // reads message from client until "Over" is sent
        while (line != null && !line.equals("Over")) {
            try {
                line = in.readUTF();
                this._debug(line);

                StringBuilder stringBuilder = this.handlers(line);

                this._debug(stringBuilder.toString());
                out.writeUTF(stringBuilder.toString());
            }
            catch (IOException i) {
                this.stopService();
                break;
            }
        }
    }

    private HashMap<String, String> parseCommand(String command) {
        HashMap<String, String> option = new HashMap<>();

        String[] parsedCommand = command.split("\\s");
        String[] commandFormat = {"function", "arg"};

        int i = 0;
        for( String format : commandFormat ){
            if( i == parsedCommand.length ) break;

            option.put( format, parsedCommand[i] );

            i++;
        }

        return option;
    }

    private StringBuilder getHandler( HashMap<String, String> parsedCommand ) {
        if( parsedCommand.size() != 2 ) return new StringBuilder("error");

        String fileName = parsedCommand.get("arg");
        StringBuilder stringBuilder = null;
        try {
            stringBuilder = new StringBuilder(
                Files.readString(Paths.get(fileName))
            );
        }
        catch (IOException e) {
            return new StringBuilder("error");
        }

        return stringBuilder;
    }
    private StringBuilder indexHandler( HashMap<String, String> parsedCommand ) {
        StringBuilder str = new StringBuilder();
        File file = new File("./");
        for( String fileName : file.list() ) {
            str.append(fileName).append("\n");
        }

        return str;
    }

    private StringBuilder defaultHandler() {
        return new StringBuilder("Unknown Command");
    }

    private StringBuilder handlers( String command ) {
        HashMap<String, String> parsedCommand = this.parseCommand( command );
        String function = parsedCommand.get("function");

        StringBuilder stringBuilder = null;

        if(function.equals("get")) {
            stringBuilder = this.getHandler(parsedCommand);
        }
        else if(function.equals("index")) {
            stringBuilder = this.indexHandler(parsedCommand);
        }
        else if(function.equals("Over")) {
            return new StringBuilder("Over");
        }
        else {
            stringBuilder = this.defaultHandler();
        }

        return stringBuilder;
    }

    public static void main_multi(String args[]) {
        int i =0;
        Boolean _DEBUG = false;

        Socket socket = null;
        ServerSocket server = null;

        try {
            server = new ServerSocket(5000);
        }
        catch (IOException e) {
            ;
        }

        while (true)
        {
            try
            {
                if(_DEBUG) System.out.println("New service created");
                socket = server.accept();

                // create a new thread object
                Thread t = new NFileServer(socket);
                ((NFileServer) t)._DEBUG = _DEBUG;

                // Invoking the start() method
                t.start();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main_single(String args[]) {
        ServerSocket server = null;
        Socket socket = null;
        try {
             server = new ServerSocket( 5000 );
            socket = server.accept();
        }
        catch (IOException e) {
            ;
        }

        Thread thread = new NFileServer(socket);
        thread.start();
    }

    public static void main(String args[]){
        main_multi(args);
    }

}