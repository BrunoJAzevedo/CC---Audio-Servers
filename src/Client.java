import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.InetAddress;
import pdu.*;

public class Client {
  private static final int TCP_PORT = 8080;
  private static final String HOST  = "localhost";

  // Regex related variables.
  private static final String regex[] = {
    "LOGIN ([A-Za-z0-9]*) ([A-Za-z0-9]*)",
    "LOGOUT",
    "REQUEST \\(([A-Za-z0-9 ]*)\\) \\(([A-Za-z0-9 ]*)\\) ([A-Za-z0-9]*)"
  };
  private static Matcher matcher;
  private static Pattern pattern;
  public static final int CLIENT_LOGIN      = 0;
  public static final int CLIENT_LOGOUT     = 1;
  public static final int CONSULT_REQUEST   = 2;

  public static void main(String args[]) throws IOException {
    String          ip        = InetAddress.getLocalHost().getHostAddress().toString();
    String          username  = "";
    boolean         logged    = false;
    Socket          socket;
    PrintWriter     writer;
    BufferedReader  reader;
    Scanner         scanner;
    ClientThread    thread;

    try {
      socket  = new Socket(HOST, TCP_PORT);
      writer  = new PrintWriter(socket.getOutputStream());
      reader  = new BufferedReader(new InputStreamReader(
          socket.getInputStream(), "UTF-8"));
      scanner = new Scanner(System.in);

      String command;
      Integer result;

      while(scanner.hasNextLine()) {
        command = scanner.nextLine();
        result  = parseCommand(command);

        switch(result) {
        case CLIENT_LOGIN:
          writer.println(new RegisterPDU(1, matcher.group(1),
            Password.SHA256HashCalculator(matcher.group(2)), ip,
            socket.getPort()).toString());
          writer.flush();
          username  = matcher.group(1);
          if (reader.readLine().equals("OK")) {
            logged  = true;
            thread  = new ClientThread(reader, writer, username);
            System.out.println("Conectado com sucesso!");
            thread.start();
          } else {
            System.out.println("Erro no login.");
          }
          break;
        case CLIENT_LOGOUT:
          if (logged) {
            writer.println(new RegisterPDU(0, username, "", ip,
              socket.getPort()).toString());
            writer.flush();
            System.out.println("Logout com sucesso!");
            logged = false;
          } else {
            System.out.println("Sem sessão iniciada!");
          }
          // TODO: Matar a thread que está a correr em background.
          break;
        case CONSULT_REQUEST:
          if (logged) {
            writer.println(new ConsultRequestPDU(matcher.group(1),
                  matcher.group(2), matcher.group(3)).toString());
            writer.flush();
          } else {
            System.out.println("Sem sessão iniciada!");
          }
          break;
        default:
          System.out.println("Comando Inválido.");
          break;
        }

        System.out.println("----------");
      }

      writer.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /** Parse a given command according to the REGEXs present in the regex array.
   *
   *  @param  command The command to be parse.
   *  @return An integer different from -1 if the command mathes any available REGEx.
   *          -1 if it doesn't.
   */
  public static int parseCommand(String c) {
    for(int i = 0; i < regex.length; i++) {
      pattern = Pattern.compile(regex[i]);
      matcher = pattern.matcher(c);

      if (matcher.find()) { return i; }
    }

    return -1;
  }
}
