import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import java.util.Scanner;

public class client {


    private client() {}

    //Cliente
    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        String command, operation;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Scanner scanner = new Scanner(System.in);
            String response;
            Hello stub = (Hello) registry.lookup("Hello");

            while(!(command = scanner.nextLine()).toUpperCase().equals("END")){
                operation = command.toUpperCase();
                switch(operation) {
                 case "HELLO":
                     response = stub.sayHello();
                     System.out.println("response: " + response);    
                     break; 
                                 } 
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}