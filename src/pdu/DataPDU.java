/**
 *  PDU used to transmit a music file.
 *
 *  @author ajfc
 *  @date   22032016
 */

package pdu;

class DataPDU extends PDU {
  /**
   *  Parameterized constructor.
   *
   *  @param  version   The PDU version.
   *  @param  security  If set to 1 security will be used to transport the PDU. If set to
   *                    0 it won't.
   *  @param  options   PDU Option values, array with 4 ints.
   *  @param  band      The band's name.
   *  @param  song      The song's title.
   *  @param  extension The audio file extension, e.g., "mp3".
   */
  public RequestPDU (int version, int security, int[] options) {
    super(version, security, PDUType.DATA, options);
  }
}
