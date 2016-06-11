/*
 * ClientThread.java
 *
 * Thread responsável por processar CONSULT_REQUEST provenientes do servidor e
 * responder se têm ou não o ficheiro que se procura.
 *
 * @date  24052016
 */

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.File;
import java.net.Socket;
import pdu.*;

public class ClientThread extends Thread {
  private Socket          socket;
  private String          username;
  private BufferedReader  reader;
  private PrintWriter     writer;

  /** Construtor parametrizado.
   *  @param  socket  Socket pela qual a thread deverá comunicar.
   */
  public ClientThread(Socket socket, String username) {
    try {
      this.writer  = new PrintWriter(socket.getOutputStream());
      this.reader  = new BufferedReader(new InputStreamReader(
          socket.getInputStream(), "UTF-8"));
      this.username = username;
    }
    catch (Exception e) {
      System.out.println("Error ao iniciar a thread! Reiniciar o programa!");
    }
  }

  public void run() {
    int i = 0;        // Utilizado para saber a ordem dos parametros do PDU.
    int version   = 1;
    int security  = 0;
    int type      = 0;
    String message, options;

    // Esperar por PDUs e fazer o parsing dos mesmos.
    try {
      while ((message = reader.readLine()) != null) {
        // Verificar se é mensagem de logout.
        if ((i == 0) && message.equals("OK")) { writer.close(); reader.close(); break; }

        switch(i) {
        case 0: // Version.
          version   = Integer.parseInt(message); i++; break;
        case 1: // Security.
          security  = Integer.parseInt(message); i++; break;
        case 2: // Type.
          type      = Integer.parseInt(message); i++; break;
        case 3: // Options.
          options   = message.toString(); i = 0;
          parsePDUData(version, security, type, options); break;
        default:
          break;
        }
      }
    } catch (Exception e) {
      System.out.println(e);
      System.out.println("Erro a interpretar PDU recebido!");
    }
  }

  /** Conforme o tipo do PDU interpretá-lo e invocar a função apropriada para interpretar
   *  o PDU. */
  private void parsePDUData(int version, int security, int type, String options) {
    switch(type) {
      case 2:   // CONSULT REQUEST.
        consultRequest();
        break;
      case 6:   // REQUEST.
        request();
        break;
      default:
        System.out.println(type);
        break;
    }
  }

  /** Analisar um PDU do tipo CONSULT_REQUEST.
   *  Verificar se o ficheiro existe dentro da pasta '/audio' e responder se
   *  o cliente tem o ficheiro ou não. */
  private void consultRequest() {
    try {
      String band;
      String song;
      String extension;

      band      = reader.readLine();
      song      = reader.readLine();
      extension = reader.readLine();

      // Verificar se a música está na pasta /music.
      File f = new File("music/" + band + " - " + song + "." + extension);
      if (f.exists() && !f.isDirectory()) {
        // Avisar servidor que ficheiro existe.
        writer.println("FOUND");
        writer.flush();
      } else {
        // Avisar servidor que ficheiro não existe.
        writer.println("NOT FOUND");
        writer.flush();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  private void request() {
    try {
      String  band;
      String  song;
      String  extension;
      String  id;
      int     port;

      band      = reader.readLine();
      song      = reader.readLine();
      extension = reader.readLine();
      id        = reader.readLine();
      port      = Integer.parseInt(reader.readLine());

      UDPServidor udp_servidor  = new UDPServidor(band + " - " + song + "." + extension, port);
      udp_servidor.start();
    } catch (Exception e) {
      System.out.println(e);
    }
  }

}
