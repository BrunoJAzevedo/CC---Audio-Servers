import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.io.*;

public class AudioServer {
  public AudioServer() {}

  public static void main(String args[]) {
    try {
      ServerImplementation sImp = new ServerImplementation("Server");

      Registry registry = LocateRegistry.getRegistry();
      registry.bind("AudioServer", sImp);
      System.out.println("AudioServer is listening...");
    } catch (Exception e) {
      System.err.println("Server exception thrown: " + e.toString());
      e.printStackTrace();
    }
  }
}
