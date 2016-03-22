/**
 *  Response to a Probe Request.
 *
 *  @author ajfc
 *  @date   22032016
 */

package pdu;

import java.sql.Timestamp;

public class ProbeResponsePDU extends PDU {
  Timestamp timestamp;

  /**
   *  Parametrized constructor.
   *  @param  timestamp Timestamp of when the PDU is created.
   */
  public ProbeResponsePDU(Timestamp timestamp) {
    super(PDUType.REGISTER, new int[] {0,0,0,0});
    this.timestamp = (Timestamp) timestamp.clone();
  }

  /**
   *  Parametrized constructor.
   *  @param  version   The PDU version number.
   *  @param  security  If the PDU is to have security (1) or not (0).
   *  @param  options   PDU options.
   *  @param  message   Message to be sent with the PDU.
   *  @param  timestamp Timestamp of when the PDU is created.
   */
  public ProbeResponsePDU(int version, int security, int[] options, Timestamp timestamp) {
    super(version, security, PDUType.REGISTER, options);
    this.timestamp = (Timestamp) timestamp.clone();
  }

  // Getters.

  /** @return The PDU timestamp. */
  public Timestamp getTimestamp() { return (Timestamp) this.timestamp.clone(); }

  // Setters.

  /**
   * Change the PDU timestamp.
   * @param timestamp The new timestamp to be used.
   */
  public void setTimestamp(Timestamp ts) {
    this.timestamp = ts;
  }
}
