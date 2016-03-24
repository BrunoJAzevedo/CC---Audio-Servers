import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.IOException;
import java.util.Scanner;

public class client {


    private client() {}

    /*public static void commands(String command) throws IOException{
        String[] args = command.split("\\s+");
        int nargs = args.length;
        String operation = args[0].toUpperCase();
        try {
            switch(operation) {
                case "HELLO":
                    Hello stub = (Hello) this.registry.lookup("Hello");
                    System.out.println("Entrou: "+operation);
                    String response = stub.sayHello();
                    System.out.println("response: " + response);    
            }
        }catch(Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }*/

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        String command, operation;
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            Scanner scanner = new Scanner(System.in);
            while(!(command = scanner.nextLine()).toUpperCase().equals("END")){
                operation = command.toUpperCase();
                switch(operation) {
                 case "HELLO":
                    Hello stub = (Hello) registry.lookup("Hello");
                    String response = stub.sayHello();
                    System.out.println("response: " + response);   
                }   
            }
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}