import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Client
{
  public static void main(String[] args) throws Exception {
    Scanner sc = new Scanner(System.in);
    int serverport = Integer.parseInt(args[0]);
	int clientport = Integer.parseInt(args[1]);
    while(true){
    	if(sc.hasNextLine()){
    		if(sc.nextLine().equals("server")) UDPServer servidor = 
    	}
	}
  }
}