import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import pdu.*;

public class ServerImplementation extends UnicastRemoteObject implements Server {
  private String name;

  public ServerImplementation(String s) throws RemoteException {
    super();
    name = s;
  }

  public String sayHello() throws RemoteException { return "Hello, World!"; }

  public String registerUser(String registerString) throws RemoteException {
    System.out.println(registerString);
    return "OK";
  }
}
