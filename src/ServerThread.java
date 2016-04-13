import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ServerThread extends Thread {
  private final Socket          socket;
  private final Server          server;
  private final BufferedReader  reader;

  public ServerThread(Socket socket, Server server) throws IOException {
    this.socket = socket;
    this.server = server;
    this.reader = new BufferedReader(new InputStreamReader(
          socket.getInputStream(), "UTF-8"));
  }

  public void run() {
    String message;

    try {
      while ((message = reader.readLine()) != null) {
        System.out.println(message);
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
