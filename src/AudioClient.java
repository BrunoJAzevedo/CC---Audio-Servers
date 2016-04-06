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

      RegisterPDU pdu = new RegisterPDU(1, "User", "password", "localhost", 3000);
      if (stub.registerUser(pdu.toString())) {  // Sucessful registration.
        System.out.println("Registration Sucessful.");
      } else {
        System.out.println("Username already taken.");
      }

      System.out.println(stub.loginUser(pdu.toString()));
      pdu.setRegisterType(0);
      stub.logoutUser(pdu.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
