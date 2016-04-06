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
  Boolean registerUser(String pdu) throws RemoteException;

  /*
     String consultRequest() throws RemoteException;

     String consultResponse() throws RemoteException;

     String probeRequest() throws RemoteException;

     String probeResponse() throws RemoteException;

     String request() throws RemoteException;

     String data() throws RemoteException;
     */

}
