import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.Iterator;
import pdu.*;

public class ServerThread extends Thread {
  private final Socket          socket;
  private final Server          server;
  private final BufferedReader  reader;
  private final PrintWriter     writer;
  private String                username;

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

  /** Interpretar o PDU de registo, login e logout enviado por um cliente e responder
   *  de forma apropriada. */
  private void parseRegisterPDU() {
    try {
      int type;
      String password;
      String ip;
      int port;

      type      = Integer.parseInt(reader.readLine());
      username  = reader.readLine();
      password  = reader.readLine();
      ip        = reader.readLine();
      port      = Integer.parseInt(reader.readLine());

      if (type == 1) {  // Login/Registo.
        if (server.loginUser(username, password, socket)) {
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

  /** Interpreta um PDU do tipo CONSULT_REQUEST e pergunta aos seus clientes se têm
   *  o ficheiro que outro cliente necessita. */
  private void parseConsultRequestPDU() {
    try {
      String band;
      String song;
      String extension;

      band      = reader.readLine();
      song      = reader.readLine();
      extension = reader.readLine();

      System.out.println(band + " - " + song + "." + extension);

      // Create new CONSULT_REQUEST PDU to send to every connected user.
      ConsultRequestPDU request = new ConsultRequestPDU(band, song, extension);
      Set<String> usernames     = server.getUsernames();
      Iterator  it              = usernames.iterator();

      // Percorrer lista de usernames e enviar o consult request a todos.
      while (it.hasNext()) {
        String user     = (String) it.next();

        if (user != username) {
          Socket socket   = server.getUserSocket(user);
          System.out.println(socket.toString());

          if (socket != null) {
            PrintWriter w = new PrintWriter(socket.getOutputStream());

            System.out.println("A consultar user: " + user);
            w.println(request.toString());
            w.flush();
          }
        }
      }

      // Responde informando que não encontrou o ficheiro.
      ConsultResponsePDU response = new ConsultResponsePDU(0, 0, "", "", 0);
      System.out.println(response.toString());
      writer.println(response.toString());
      writer.flush();
    } catch (Exception e) {
      System.out.println(e);
      writer.println("Oops! Algo de errado se passou.");
      writer.flush();
    }
  }

}
