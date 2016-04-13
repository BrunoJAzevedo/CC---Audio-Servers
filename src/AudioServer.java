public class AudioServer {
  public final static int TCP_PORT  = 8080;

  public static void main(String args[]) {
    try {
      // Criar servidor e correr.
      Server server = new Server(TCP_PORT);
      server.run();
    } catch (Exception e) {
      System.out.println("Erro: " + e);
    }
  }
}
