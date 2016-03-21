package pdu;

public class RegisterPDU extends PDU {
  int     registerType;
  String  id;
  String  ip;
  int     port;

  /**
   *  Parametrized constructor.
   *
   *  @param  version       The PDU version number.
   *  @param  security      If the PDU is to have security (1) or not (0).
   *  @param  options       PDU options.
   *  @param  registerType  Wether the user wants to login(1) or logout(0).
   *  @param  id            The client identifier.
   *  @param  ip            The client machine's IP address.
   *  @param  port          The port where to receive packets for the application.
   */
  public RegisterPDU(int version, int security, int[] options, int registerType, String id,
      String ip, int port) {
    super(version, security, PDUType.REGISTER, options);
    this.registerType = registerType;
    this.id           = id;
    this.ip           = ip;
    this.port         = port;
  }

  // Getters.

  /** @return PDU registration type. If the value is 1 it means the user is trying to
   *  login/register otherwise it is trying to logout from the system. */
  public int getRegisterType() { return this.registerType; }

  /** @return Client id. */
  public String getID() { return this.id.toString(); }

  /** @return Client machine's ip address. */
  public String getIP() { return this.ip.toString(); }

  /** @return Port where the response PDUs should be sent. */
  public int getPort() { return this.port; }

  // Setters.

  /**
   *  Set the registerType value.
   *  @param  type  If 1 the PDU will be used to register/login the current client. If
   *                0 it will be used to logout the current session. If any other value
   *                is passed the register type won't change.
   */
  public void setRegisterType(int type) {
    if (type == 0 || type == 1) { this.registerType = type; }
  }

  /**
   *  Set the client id.
   *  @param  id  The client unique identifier.
   */
  public void setID(String id) {
    this.id = id;
  }

  /**
   *  Set the ip address to where the response PDU should be sent.
   *  @param  ip  The machine's ip address.
   */
  public void setIP(String ip) {
    this.ip = ip;
  }

  /**
   *  Set the port to where the PDU should be delivered.
   *  @param  port  The client's application port to deliver the response PDU.
   */
  public void setPort(int port) {
    this.port = port;
  }
}
