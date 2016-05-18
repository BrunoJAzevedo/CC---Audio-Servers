import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import pdu.*;

public class ServerThread extends Thread {
  private final Socket          socket;
  private final Server          server;
  private final BufferedReader  reader;
  private final PrintWriter     writer;

  public ServerThread(Socket socket, Server server) throws IOException {
    this.socket = socket;
    this.server = server;
    this.reader = new BufferedReader(new InputStreamReader(
          socket.getInputStream(), "UTF-8"));
    this.writer = new PrintWriter(socket.getOutputStream(), true);
  }

  public void run() {
    int version     = 0;
    int security    = 0;
    int type        = 0;
    int i           = 0;
    String options  = "";
    String message;

    try {
      // Parse first 4 parameters to check what is the PDU type.
      while ((message = reader.readLine()) != null) {
        System.out.println(message);
        switch(i) {
        case 0: // Version.
          version   = Integer.parseInt(message); i++; break;
        case 1: // Security.
          security  = Integer.parseInt(message); i++; break;
        case 2: // Type.
          type      = Integer.parseInt(message); i++; break;
        case 3: // Options.
          options   = message.toString(); i = 0;
          parsePDUData(version, security, type, options); break;
        default:
          break;
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void parsePDUData(int version, int security, int type, String options) {
    switch(type) {
    case 1:   // FIXME: Utilizar a classe PDUType.
      parseRegisterPDU();
      break;
    case 2:
      parseConsultRequestPDU();
      break;
    default:  // FIXME: <-- Limpar o reader caso não seja um PDU.
      break;
    }
  }

  private void parseRegisterPDU() {
    try {
      int type;
      String username;
      String password;
      String ip;
      int port;

      type      = Integer.parseInt(reader.readLine());
      username  = reader.readLine();
      password  = reader.readLine();
      ip        = reader.readLine();
      port      = Integer.parseInt(reader.readLine());

      if (type == 1) {  // Login/Registo.
        if (server.loginUser(username, password)) {
          writer.println("OK");
          writer.flush();
        } else {
          writer.println("Erro");
          writer.flush();
        }
      } else {          // Logout.
        server.logoutUser(username);
        writer.println("OK");
        writer.flush();
      }
    } catch (Exception e) {
      System.out.println(e);
      writer.println("Oops! Algo de errado se passou.");
      writer.flush();
    }
  }

  private void parseConsultRequestPDU() {
    try {
      String band;
      String song;
      String extension;

      band      = reader.readLine();
      song      = reader.readLine();
      extension = reader.readLine();

      // Responde informando que não encontrou o ficheiro.
      ConsultResponsePDU response = new ConsultResponsePDU(0, 0, "", "", 0);
      writer.println(response.toString());
      writer.flush();
    } catch (Exception e) {
      System.out.println(e);
      writer.println("Oops! Algo de errado se passou.");
      writer.flush();
    }
  }

}
