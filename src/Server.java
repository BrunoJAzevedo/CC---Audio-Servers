import java.rmi.Remote;
import java.rmi.RemoteException;
import pdu.*;

//Remote
public interface Server extends Remote {
  String sayHello() throws RemoteException;

  String registerUser(String registerString) throws RemoteException;

  /*
     String consultRequest() throws RemoteException;

     String consultResponse() throws RemoteException;

     String probeRequest() throws RemoteException;

     String probeResponse() throws RemoteException;

     String request() throws RemoteException;

     String data() throws RemoteException;
     */

}
