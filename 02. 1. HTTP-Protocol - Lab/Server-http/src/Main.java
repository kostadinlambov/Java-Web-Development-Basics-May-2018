import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(8007, 5 );
        Socket socket = serverSocket.accept();


        InputStream inputStream = socket.getInputStream();


        BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        inputStream
                )
        );

       // reader.lines().forEach(System.out::println);
        String line;
        while ((line = reader.readLine()) !=null){
            System.out.println(line);
        }

        socket.close();
    }
}
