package pdu;

public class PDU {
  final public int version;
  final public int security;
  final public int type;
  final public int options[];

  /**
   *  Create a new PDU packet.
   *
   *  @param  version   The packet version.
   *  @param  security  If set to 1 pdu will use checksum.
   *  @param  type      The PDU type, check PDUType.java for the availabel pdu types.
   *  @param  options   Aditional pdu options.
   *  @return An instance of a PDU.
   */
  public PDU (int version, int security, PDUType type, int options[]) {
    this.version  = version;
    this.security = security;
    this.type     = type.value();
    this.options  = options;
  }
}
