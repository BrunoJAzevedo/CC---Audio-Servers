import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import pdu.*;

public class AudioClient {
  private static Server stub = null;
  private AudioClient() {}

  public static void main(String args[]) {
    try {
      Registry reg = LocateRegistry.getRegistry("localhost");
      stub = (Server) reg.lookup("AudioServer");
      System.out.println(stub.sayHello());
      RegisterPDU pdu = new RegisterPDU(1, "User", "localhost", 3000);
      System.out.println(stub.registerUser(pdu.toString()));
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
