import java.io.*;
import java.net.*;

class UDPClient extends Thread {
  String filename;
  int client_port;

  public UDPClient(String filename, int port) {
    this.filename     = filename;
    this.client_port  = port;
  }

  public void run() {
    System.out.println("A transferir o ficheiro: music/" + filename);
    try {
      DatagramSocket serverSocket = new DatagramSocket(client_port);
      byte[] receiveData = new byte[1024];
      byte[] sendData = new byte[1024];
      FileOutputStream out = null;
      String begin = new String("begin");
      int i = 0;
      serverSocket.setSoTimeout(100000);

      out = new FileOutputStream("music/" + filename);
      DatagramPacket receiveAck = new DatagramPacket(receiveData, receiveData.length);
      serverSocket.receive(receiveAck);
      int port = receiveAck.getPort();
      InetAddress IPAddress = receiveAck.getAddress();
      while(true){
        try{
          String ack = new String(receiveAck.getData());
          if(ack.contains("begin")){
            System.out.println("A inicar transferência...");
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
          serverSocket = new DatagramSocket(client_port);
          String timeout = new String("timeout");
          sendData = timeout.getBytes();
          DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, IPAddress, port);
          serverSocket.send(sendPacket);
        }
      }
      System.out.println("Transferência terminada...");
      serverSocket.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
