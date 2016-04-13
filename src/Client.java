import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
  private static final int TCP_PORT = 8080;

  private static final String regex[] = {
    "REGISTER ([A-Za-z0-9]*) ([A-Za-z0-9]*)",
    "LOGIN ([A-Za-z0-9]*) ([A-Za-z0-9]*)",
    "LOGOUT"
  };
  private static final int CLIENT_REGISTER  = 0;
  private static final int CLIENT_LOGIN     = 1;
  private static final int CLIENT_LOGOUT    = 2;

  private static Pattern pattern;
  private static Matcher matcher;

  public static void main(String args[]) throws IOException {
    Socket      socket;
    PrintWriter writer;
    Scanner     scanner;

    try {
      socket  = new Socket("localhost", TCP_PORT);
      writer  = new PrintWriter(socket.getOutputStream());
      scanner = new Scanner(System.in);

      String command;
      Integer result;

      while(scanner.hasNextLine()) {
        command = scanner.nextLine();
        result  = parseCommand(command);

        switch(result) {
        case CLIENT_REGISTER:
          writer.println(matcher.group(1) + " - " + matcher.group(2));
          writer.flush();
          break;
        case CLIENT_LOGIN:
          writer.println(matcher.group(1) + " - " + matcher.group(2));
          writer.flush();
          break;
        case CLIENT_LOGOUT:
          System.out.println("LOGOUT");
          break;
        default: System.out.println("Comando Inválido."); break;
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
  private static int parseCommand(String c) {
    for(int i = 0; i < regex.length; i++) {
      pattern = Pattern.compile(regex[i]);
      matcher = pattern.matcher(c);

      if (matcher.find()) { return i; }
    }

    return -1;
  }
}
