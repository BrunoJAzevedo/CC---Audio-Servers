package pdu;

public abstract class PDU {
  private int version;
  private int security;
  private int type;
  private int options[];

  /**
   *  Create a new PDU packet with some default version to 1 and default
   *  security to 0 (none).
   *
   *  @param  type      The PDU type, check PDUType.java for the availabel pdu types.
   *  @param  options   Aditional pdu options.
   *  @return An instance of a PDU.
   */
  public PDU(int type, int options[]) {
    this.version  = 1;
    this.security = 0;
    this.type     = type;
    this.options  = options;
  }

  /**
   *  Create a new PDU packet.
   *
   *  @param  version   The packet version.
   *  @param  security  If set to 1 pdu will use checksum.
   *  @param  type      The PDU type, check PDUType.java for the availabel pdu types.
   *  @param  options   Aditional pdu options.
   *  @return An instance of a PDU.
   */
  public PDU(int version, int security, int type, int options[]) {
    this.version  = version;
    this.security = security;
    this.type     = type;
    this.options  = options;
  }

  /**
   *  @return PDU version value.
   */
  public int getVersion() { return this.version; }

  /**
   *  @return PDU security parameter.
   */
  public int getSecurity() { return this.security; }

  /**
   *  @return PDU type value.
   */
  public int getType() { return this.type; }

  /**
   *  @return PDU options values.
   */
  public int[] getOptions () { return this.options; }

}
