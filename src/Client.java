import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import pdu.RegisterPDU;
import java.net.InetAddress;

public class Client {
  private static final int TCP_PORT = 8080;

  private static final String regex[] = {
    "LOGIN ([A-Za-z0-9]*) ([A-Za-z0-9]*)",
    "LOGOUT"
  };
  public static final int CLIENT_LOGIN     = 0;
  public static final int CLIENT_LOGOUT    = 1;

  private static Matcher matcher;
  private static Pattern pattern;

  public static void main(String args[]) throws IOException {
    String          ip = InetAddress.getLocalHost().getHostAddress().toString();
    String          username = "";
    Socket          socket;
    PrintWriter     writer;
    BufferedReader  reader;
    Scanner         scanner;

    try {
      socket  = new Socket("localhost", TCP_PORT);
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
          System.out.println(reader.readLine());
          break;
        case CLIENT_LOGOUT:
          writer.println(new RegisterPDU(0, username, "", ip,
            socket.getPort()).toString());
          writer.flush();
          System.out.println(reader.readLine());
          break;
        default:
          System.out.println("Comando Inválido.");
          break;
        }
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
