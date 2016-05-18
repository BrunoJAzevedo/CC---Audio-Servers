import java.io.*;
import java.net.*;

class UDPServidor
{
   public static void main(String args[]) throws Exception
   {
      DatagramSocket serverSocket = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      FileOutputStream out = null;
      int i = 1;
      try{
         out = new FileOutputStream("testar.mp3");
         while(i == 1)
         {
            StringBuilder sb = new StringBuilder();
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            sb.append(sentence);
            int c = Integer.parseInt(sentence.trim());
            System.out.println(c);
            if(sentence.equals("end")) i = 0;   
            else out.write(c);
         }     
         out.close();
      }finally{
         if (out != null) {
            out.close();
         }
      }
   }
}

