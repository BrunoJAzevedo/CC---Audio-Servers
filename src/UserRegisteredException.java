public class UserRegisteredException extends Exception {
  public UserRegisteredException() {
    super("User is already registered.");
  }

  public UserRegisteredException(String message) {
    super(message);
  }
}
