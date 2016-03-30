/**
 *  Class responsible for saving usernames and passwords.
 *
 *  @date   30032016
 *  @author ajfc
 */

import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Users {
  // Map between username and password.
  private HashMap<String, String> users;

  public Users() {
    users = new HashMap<String, String>();
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
        String hashedPassword = SHA256HashCalculator(password);
        users.put(user, hashedPassword);
        System.out.println((String) users.get(user));
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
   */
  public boolean loginUser(String username, String password) {
    String user = username.toLowerCase();

    if (!users.containsKey(user)) { return false; }
    else {
      try {
        String hashedPassword = SHA256HashCalculator(password);
        String savedPassword  = (String) users.get(user);

        return savedPassword.equals(hashedPassword);
      } catch (Exception e) {
        return false;
      }
    }
  }
}
