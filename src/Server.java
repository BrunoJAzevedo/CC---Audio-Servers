/**
 *  Server interface, defines the methods that should be
 *  implemented by a class that whishes to serve as the server.
 *
 *  @author joaofcosta.
 *  @date   06042016.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import pdu.*;

public interface Server extends Remote {
  /** Register a new user.
   *
   *  @param  pdu A string from the RegisterPDU `toString`.
   *  @return True if registration was sucessfull, false otherwise.
   */
  Boolean registerUser(String pdu) throws RemoteException;

  /** Login a user.
   *
   *  @param  pdu A string from the RegisterPDU `toString`.
   *  @return True if login was sucessfull, false otherwise.
   */
  Boolean loginUser(String pdu) throws RemoteException;

  /** Logout a user.
   *
   *  @param  pdu A string from the RegisterPDU `toString`.
   */
  void logoutUser(String pdu) throws RemoteException;

  /*
     String consultRequest() throws RemoteException;

     String consultResponse() throws RemoteException;

     String probeRequest() throws RemoteException;

     String probeResponse() throws RemoteException;

     String request() throws RemoteException;

     String data() throws RemoteException;
     */

}
