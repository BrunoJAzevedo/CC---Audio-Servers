/**
 *  PDU for testing transfer conditions.
 *
 *  @author ajfc
 *  @date   21032016
 */

package pdu;

public class ProbeRequestPDU extends PDU {
  /**
   *  Parametrized constructor.
   *
   *  @param  version   The PDU version number.
   *  @param  security  If the PDU is to have security (1) or not (0).
   *  @param  options   PDU options.
   *  @param  message   Message to be sent with the PDU.
   */
  public ProbeRequestPDU(int version, int security, int[] options) {
    super(version, security, PDUType.REGISTER, options);
  }
}
