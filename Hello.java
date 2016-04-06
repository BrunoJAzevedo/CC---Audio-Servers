import java.rmi.Remote;
import java.rmi.RemoteException;

//Remote
public interface Hello extends Remote {
    String sayHello() throws RemoteException;

    
    String register(String login, String pass) throws RemoteException; 
    /*    
    String consultRequest() throws RemoteException;

	String consultResponse() throws RemoteException;
	
	String probeRequest() throws RemoteException;
	
	String probeResponse() throws RemoteException;
	
	String request() throws RemoteException;
	
	String data() throws RemoteException;*/

}