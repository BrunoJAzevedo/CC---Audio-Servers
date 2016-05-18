import java.io.*;
import java.net.*;

class transferClient
{
   public static void main(String args[]) throws Exception
   {
      FileInputStream in = null;
      FileOutputStream out = null;
      
      DatagramSocket clientSocket = new DatagramSocket();
      InetAddress IPAddress = InetAddress.getByName("localhost");
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      String sentence = "";
      int c;
      try{
         in = new FileInputStream("000008.mp3");
         while((c = in.read()) != -1)
         {
            
            StringBuilder sb = new StringBuilder();
            if(c >= 100){
               sb.append(c);
            }
            else if(c>=10){
               sb.append("0"+c);
            }
            else{
               sb.append("00"+c);
            }
            sentence = sb.toString();
            System.out.println("c ->" + c + " sentence ->" + sentence);
            sendData = sentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
         }
         sentence = "end";
         sendData = sentence.getBytes();
         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
         System.out.println("Ficheiro enviado");
         in.close();
         clientSocket.close();
      }catch(Exception e){
         System.err.println(e);
      }
   }
}