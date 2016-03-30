import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import java.util.Scanner;

public class client {


    private client() {}

    public  static void menu(String host, String command){
        String response;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Hello stub = (Hello) registry.lookup("Hello");
            switch(command) {
                case "HELLO":
                    response = stub.sayHello();
                    System.out.println("response: " + response);    
                    break; 
                case "REGISTER":
                    response = stub.register();
                    System.out.println("response: " + response); 
                    break;
                case "CONSULT REQUEST":
                    response = stub.consultRequest();
                    System.out.println("response: " + response); 
                    break;
                case "CONSULT RESPONSE":
                    response = stub.consultResponse();
                    System.out.println("response: " + response); 
                    break;
                case "PROBE REQUEST":
                    response = stub.probeRequest();
                    System.out.println("response: " + response); 
                    break;
                case "PROBE RESPONSE":
                    response = stub.probeResponse();
                    System.out.println("response: " + response); 
                    break;
                case "REQUEST":
                    response = stub.request();
                    System.out.println("response: " + response); 
                    break;
                case "DATA":
                    response = stub.data();
                    System.out.println("response: " + response); 
                    break;
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
    //Cliente
    public static void main(String[] args) {
        String host = (args.length < 1) ? null : args[0];
        String command, operation;
        try {
           
            Scanner scanner = new Scanner(System.in);

            while(!(command = scanner.nextLine()).toUpperCase().equals("END")){
                operation = command.toUpperCase();
                if (operation.equals("EXIT")) break;
                else menu(host,operation);
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}