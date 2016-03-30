import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
        
public class serverT implements Hello {
        
    public serverT() {}

    //método teste
    public String sayHello() {
        return "Hello, world!";
    }

    //método register
    public String register() {
        return "Register";
    }
        
    //método consultRequest
    public String consultRequest() {
        return "Consult Request";
    }

    //método consultResponse
    public String consultResponse(){
        return "Consult Response";
    }

    //método probeRequest
    public String probeRequest(){
        return "Probe Request";
    }

    //método probeResponse
    public String probeResponse(){
        return "Probe Response";
    }

    //método request
    public String request(){
        return "Request";
    }

    //método data
    public String data(){
        return "Data";
    }

    //servidor
    public static void main(String args[]) {
        
        try {
            serverT obj = new serverT();
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