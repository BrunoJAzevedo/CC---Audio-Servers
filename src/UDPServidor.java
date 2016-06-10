import java.io.*;
import java.net.*;
import java.util.*;

class UDPServidor
{
   public static void main(String args[]) throws Exception
   {
      FileInputStream in = null;
      FileOutputStream out = null;
      
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[100];
      byte[] receiveData = new byte[1024];
      int c, i = 0, j = 0;
      ArrayList<Integer> al = new ArrayList<Integer>();
      try{
         in = new FileInputStream("000001.mp3");
         while((c = in.read()) != -1){ 
            al.add(c); 
            j++;
         }

         String ack = "begin";
         sendData = ack.getBytes();
         DatagramPacket sendAck = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
         clientSocket.send(sendAck);
         System.out.println("ACK -> "+ack);
         //answer ack
         DatagramPacket receiveAck = new DatagramPacket(receiveData, receiveData.length);
         clientSocket.receive(receiveAck);
         String answer = new String(receiveAck.getData(),0,receiveAck.getLength());
         //begin transfer
         if(answer.contains("ok")){
            while(true){
               System.out.println(i + " " + j);
               if(i<j){
                  sendData = al.get(i).toString().getBytes();
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                  clientSocket.send(sendPacket);

                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  clientSocket.receive(receivePacket);
                  answer = new String(receivePacket.getData(),0,receivePacket.getLength() );

                  if(answer.contains("ok")) i++;
               } else{
                  System.out.println("end");
                  sendData = "end".getBytes();
                  DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                  clientSocket.send(sendPacket);

                  break;
               }
            }
         }
         clientSocket.close();
      }catch(Exception e){
         System.err.println(e);
      }
   }
}

