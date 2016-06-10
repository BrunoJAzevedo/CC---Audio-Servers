import java.io.*;
import java.net.*;

class UDPClient
{
   public static void main(String args[]) throws Exception
   {
      DatagramSocket serverSocket = new DatagramSocket(9876);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      FileOutputStream out = null;
      String begin = new String("begin");
      int i = 0;
      serverSocket.setSoTimeout(100000);
      
      out = new FileOutputStream("teste.mp3");
      DatagramPacket receiveAck = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receiveAck);
      int port = receiveAck.getPort();
      InetAddress IPAddress = receiveAck.getAddress();
      while(true){
         try{
            String ack = new String(receiveAck.getData());
            if(ack.contains("begin")){
               System.out.println(ack);
               String ackAnswer = new String("ok");
               sendData = ackAnswer.getBytes();
               DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
               serverSocket.send(sendPacket);
               while(true){
                  //System.out.println("waiting for packet");
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String(receivePacket.getData(),0,receivePacket.getLength());
                  if(!sentence.contains("end")){
                     int c = Integer.parseInt(sentence.trim());
                     //System.out.println(c);
                     out.write(c);

                     sendData = ackAnswer.getBytes();
                     sendPacket =
                     new DatagramPacket(sendData, sendData.length, IPAddress, port);
                     serverSocket.send(sendPacket);
                  }
                  else break;
               }
               break;
            }
         }catch(SocketTimeoutException e){
            serverSocket = new DatagramSocket(9876);
            String timeout = new String("timeout");
            sendData = timeout.getBytes();
            DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
         }
      }
      serverSocket.close();
   }
}  