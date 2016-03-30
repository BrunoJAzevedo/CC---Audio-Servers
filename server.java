import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
        
public class server implements Hello {
        
    public server() {}

    //método teste
    public String sayHello() {
        return "Hello, world!";
    }

    //método register
    public String register() {
    
    }
        
    //método consultRequest
    public String consultRequest() {

    }

    //método consultResponse
    public String consultResponse(){

    }

    //método probeRequest
    public String probeRequest(){

    }

    //método probeResponse
    public String probeResponse(){

    }

    //método request
    public String request(){

    }

    //método data
    public String data(){

    }

    //servidor
    public static void main(String args[]) {
        
        try {
            server obj = new server();
            Hello stub = (Hello) UnicastRemoteObject.exportObject(obj, 0);
            System.setProperty("java.rmi.server.hostname","127.0.0.1");
            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("Hello", stub);
            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}