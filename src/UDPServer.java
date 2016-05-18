import java.io.*;
import java.net.*;

class UDPServer
{
    private static DatagramSocket serverSocket;
    private static byte[] receiveData = new byte[1024];
    private static byte[] sendData = new byte[1024];
    private static int port; 

    public UDPServer(int port) { 
        this.port = port;
    }

    public int getPort(){
        return this.port;
    }

    public static void main(String args[]) throws Exception
    {
        serverSocket = new DatagramSocket(port);
        while(true)
        {
           DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
           serverSocket.receive(receivePacket);
           String sentence = new String( receivePacket.getData());
           System.out.println("RECEIVED: " + sentence);
           InetAddress IPAddress = receivePacket.getAddress();
           int port = receivePacket.getPort();
           String capitalizedSentence = sentence.toUpperCase();
           sendData = capitalizedSentence.getBytes();
           DatagramPacket sendPacket =
           new DatagramPacket(sendData, sendData.length, IPAddress, port);
           serverSocket.send(sendPacket);
        }
    }
}