import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class Password {
  public static String SHA256HashCalculator(String password)
    throws UnsupportedEncodingException, NoSuchAlgorithmException {

    MessageDigest md  = MessageDigest.getInstance("SHA-256");
    md.update(password.getBytes("UTF-8"));
    byte[] digest = md.digest();
    return String.format("%064x", new java.math.BigInteger(1, digest));
  }
}
