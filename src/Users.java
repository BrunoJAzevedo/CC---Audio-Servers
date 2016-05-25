/**
 *  Class responsible for saving the mapping between usernames and passwords,
 *  usernames and sockets and usernames and the connection state.
 *
 *  @date   30032016
 *  @author ajfc
 */

import java.security.NoSuchAlgorithmException;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;
import java.net.Socket;

public class Users {
  // Map between username and password.
  private HashMap<String, String> users;
  // Map between username and socket.
  private HashMap<String, Socket> sockets;
  // Map between username and session state, the state is set to true if the user is logged in.
  private HashMap<String, Boolean> state;

  public Users() {
    users   = new HashMap<String, String>();
    sockets = new HashMap<String, Socket>();
    state   = new HashMap<String, Boolean>();
  }


  /** Use SHA-256 to hash the password.
   *
   *  @param  string  String to be hashed.
   *  @return The hashed string.
   */
  private String SHA256HashCalculator(String string)
    throws UnsupportedEncodingException, NoSuchAlgorithmException {

    MessageDigest md  = MessageDigest.getInstance("SHA-256");
    md.update(string.getBytes("UTF-8"));
    byte[] digest = md.digest();
    return String.format("%064x", new java.math.BigInteger(1, digest));
  }

  /** Add a new user to the system.
   *  A UserRegisteredException will be throw if the user is already
   *  registered in the system.
   *
   *  @param  username  The user username, it isn't case senstive, everything is converted
   *                    to lowercase, so "John" is converted to "john".
   *  @param  password  The password which is to be used by the user.
   */
  public void registerUser(String username, String password)
    throws UserRegisteredException, Exception {
    String user = username.toLowerCase();

    if (users.containsKey(user)) {
      throw new UserRegisteredException("This username is already taken.");
    } else {
      try {
        //String hashedPassword = SHA256HashCalculator(password);
        users.put(user, password);
      } catch (Exception e) {
        throw new Exception("Password Hashing Failed");
      }
    }
  }

  /** Login a user in the system.
   *
   *  @param  usernmae  The user's username. The username is case insentitive, so, for example
   *                    "John" is transformed to "john".
   *  @param  passowrd  The user's password.
   *  @return true if the login suceeded, false if the user does not exist or if the user
   *          is currently logged in.
   */
  public Boolean loginUser(String username, String password, Socket socket)
    throws UserRegisteredException, Exception {
    String user = username.toLowerCase();

    if (!users.containsKey(user))     { this.registerUser(username, password); }
    else if (state.containsKey(user)) { this.registerUser(username, password); }

    try {
      //String hashedPassword = SHA256HashCalculator(password);
      String savedPassword  = (String) users.get(user);

      if (savedPassword.equals(password)) {
        // Mark the user as logged in.
        state.put(user, true);
        // Save the user's socket.
        sockets.put(user, socket);
        return true;
      } else { return false; }
    } catch (Exception e) {
      return false;
    }
  }

  /** Get the username socket.
   *
   *  @param  username  The user's username, case insentive.
   */
  public Socket getUserSocket(String username) {
    if (!sockets.containsKey(username)) { return null; }
    Socket s = sockets.get(username);
    return s;
  }

  /** Logout the user from the system.
   *
   *  @param  username  The user's username, case insenstive.
   */
  public void logoutUser(String username) {
    String user = username.toLowerCase();

    if (state.containsKey(user)) { state.remove(user); }
  }

  /** Get list of all usernames. */
  public Set<String> getUsernames() {
    return sockets.keySet();
  }
}
