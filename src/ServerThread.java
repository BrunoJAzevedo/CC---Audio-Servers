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
  private final Socket          consult_socket;
  private final Server          server;
  private final BufferedReader  reader;
  private final PrintWriter     writer;
  private String                username;

  public ServerThread(Socket consult_socket, Socket socket, Server server) throws IOException {
    this.socket         = socket;
    this.consult_socket = consult_socket;
    this.server         = server;
    this.reader         = new BufferedReader(new InputStreamReader(
      socket.getInputStream(), "UTF-8"));
    this.writer         = new PrintWriter(socket.getOutputStream(), true);
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
        if (server.loginUser(username, password, consult_socket)) {
          writer.println("OK");
          writer.flush();
        } else {
          writer.println("Erro");
          writer.flush();
        }
      } else { // Logout.
        Socket s      = server.getUserSocket(username);
        PrintWriter w = new PrintWriter(s.getOutputStream());
        w.println("OK"); w.flush();
        server.logoutUser(username);
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
      String    ip = "", user = "", current_user, found;
      ConsultResponsePDU response;
      Integer users = 0, port = 0;
      long begin, end, time_elapsed = 1000000000;

      // Percorrer lista de usernames e enviar o consult request a todos.
      while (it.hasNext()) {
        current_user = (String) it.next();

        if (current_user != username) {
          Socket socket = server.getUserSocket(current_user);

          if (socket != null) {
            // Enviar consult request ao cliente e verificar se têm música.
            PrintWriter w     = new PrintWriter(socket.getOutputStream());
            BufferedReader r  = new BufferedReader(new InputStreamReader(
              socket.getInputStream(), "UTF-8"));

            begin = System.nanoTime();
            System.out.println("A consultar user: " + current_user + " -- " + socket.toString());
            w.println(request.toString());
            w.flush();

            // Verificar se cliente diz "FOUND" (tem ficheiro) ou "NOT FOUND" não tem ficheiro.
            found = r.readLine();
            end   = System.nanoTime();
            System.out.println(current_user + " -> " + found + " | " + (end - begin));

            if (found.equals("FOUND")){
              users++;
              if ((end - begin) < time_elapsed) {
                // Ficheiro encontradoe e reposta mais rápida, trocar ip.
                ip            = socket.getLocalAddress().toString();
                user          = current_user;
                time_elapsed  = end - begin;
                port          = socket.getPort();
              }
            }
          }
        }
      }

      // Responde informando que não encontrou o ficheiro.
      if (ip.equals("")) {
        response = new ConsultResponsePDU(0, 0, "", "", 0);
      } else {
        response = new ConsultResponsePDU(1, users, user, ip, port);
      }
      writer.println(response.toString());
      writer.flush();
    } catch (Exception e) {
      System.out.println(e);
      writer.println("Oops! Algo de errado se passou.");
      writer.flush();
    }
  }
}
