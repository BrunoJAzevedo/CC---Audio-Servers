/**
 *  Server interface, defines the methods that should be
 *  implemented by a class that whishes to serve as the server.
 *
 *  @author joaofcosta.
 *  @date   06042016.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Set;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.System;
import java.lang.Runtime;

public class Server {
  private final Users users;
  private final ServerSocket ssocket;

  public Server(int port) throws IOException {
    this.ssocket  = new ServerSocket(port);
    this.users    = new Users();
  }

  public void run() {
    Socket        socket;
    Socket        consult_socket;
    ServerThread  sthread;

    try {
      // Aceitar ligações dos users e atribuir uma thread a cada um.
      System.out.println("Servidor Online.");

      while ((socket = ssocket.accept()) != null) {
        consult_socket  = ssocket.accept();
        sthread         = new ServerThread(consult_socket, socket, this);
        System.out.println("Nova ligação de: " + socket.toString());
        sthread.start();
      }
    } catch (Exception e) {
      System.out.println("Erro: " + e);
    }
  }

  public synchronized Boolean loginUser(String username, String password, Socket socket)
    throws UserRegisteredException, Exception {
    return this.users.loginUser(username, password, socket);
  }

  public synchronized void logoutUser(String username) {
    this.users.logoutUser(username);
  }

  public synchronized Set<String> getUsernames() {
    return users.getUsernames();
  }

  public synchronized Socket getUserSocket(String username) {
    return users.getUserSocket(username);
  }

}
