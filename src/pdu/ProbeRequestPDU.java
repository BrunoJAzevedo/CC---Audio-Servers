/**
 *  PDU for testig transfer conditions.
 *
 *  @author ajfc
 *  @date   21032016
 */

package pdu;

public class ProbeRequestPDU extends PDU {
  private String message;

  /**
   *  Parametrized constructor.
   *
   *  @param  message Message to be sent.
   */
  public ProbeRequestPDU(String message) {
    super(PDUType.PROBE_REQUEST, new int[] {0,0,0,0});
    this.message = message.toString();
  }

  /**
   *  Parametrized constructor.
   *
   *  @param  version   The PDU version number.
   *  @param  security  If the PDU is to have security (1) or not (0).
   *  @param  options   PDU options.
   *  @param  message   Message to be sent with the PDU.
   */
  public ProbeRequestPDU(int version, int security, int[] options, String message) {
    super(version, security, PDUType.REGISTER, options);
    this.message = message.toString();
  }

  // Getters.

  /** @return message The PDU message. */
  public String getMessage() { return this.message.toString(); }

  // Setters.

  /**
   *  Set the PDU message to be sent.
   *  @param  message Message to be sent.
   */
  public void setMessage(String message) {
    this.message = message.toString();
  }
}
