import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import pdu.*;
import java.util.Arrays;

public class ServerImplementation extends UnicastRemoteObject implements Server {
  private Users users;

  public ServerImplementation(Users users) throws RemoteException {
    super();
    this.users = users;
  }

  public Boolean registerUser(String pdu) throws RemoteException {
    // Parse pdu data.
    String[] fields = pdu.split("\\r?\\n");
    int version     = Integer.parseInt(fields[0]);
    int security    = Integer.parseInt(fields[1]);
    String options  = fields[2];
    int type        = Integer.parseInt(fields[3]);
    String id       = fields[4];
    String password = fields[5];
    String ip       = fields[6];
    int port        = Integer.parseInt(fields[7]);

    // Register new user.
    try { users.registerUser(id, password); }
    catch (Exception e) {
      // Failed to register, username is already taken.
      return false;
    }

    // Sucessful registration.
    return true;
  }
}
