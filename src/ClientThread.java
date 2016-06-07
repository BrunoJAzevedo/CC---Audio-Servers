/*
 * ClientThread.java
 *
 * Thread responsável por esperar por respostas do servidor.
 * Não só espera por respostas do servidor como também é responsável por tratar
 * PDU de consulta (CONSULT_REQUEST) por parte do servidor como responder aos mesmos pedidos.
 * Além de responder ao servidor se têm ou não uma música, também pode enviar uma música
 * para outro utilizador.
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
  public ClientThread(BufferedReader reader, PrintWriter writer, String username) {
    try {
      this.reader   = reader;
      this.writer   = writer;
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
      case 2:   // CONSULT RESPONSE.
        consultRequest();
        break;
      case 3:   // CONSULT RESPONSE.
        consultResponse();
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
      System.out.println(f.toString());
      if (f.exists() && !f.isDirectory()) {
        System.out.println("Ficheiro encontrado.");
      }

    } catch (Exception e) {
      System.out.println(e);
    }
  }

  /** Parse the consult response PDU. */
  private void consultResponse() {
    try {
      int found       = Integer.parseInt(reader.readLine());
      int clients     = Integer.parseInt(reader.readLine());
      String id       = reader.readLine();
      String ip       = reader.readLine();
      int port        = Integer.parseInt(reader.readLine());

      if (found == 0) { System.out.println("Música não encontrada..."); }
      else  { System.out.println("Música encontrada no: " + ip + ":" + port); }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
