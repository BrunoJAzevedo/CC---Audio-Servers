/**
 *  PDU used to respond to a music file search.
 *
 *  @author ajfc
 *  @date   21032016
 */

package pdu;

class ConsultResponsePDU extends PDU {
  private int     found;
  private int     clients;
  private String  id;
  private String  ip;
  private int     port;

  /**
   *  Parameterized constructor.
   *
   *  @param  found   If the file exists and was found this should be set to 1, otherwise it
   *                  should be set to 0.
   *  @param  clients Number of clients that have the music file.
   *  @param  id      The id of the client who has the music file.
   *  @param  ip      The ip of the client who has the music file.
   *  @param  port    The client's port where PDUs should be sent.
   */
  public ConsultResponsePDU (int found, int clients, String id, String ip, int port) {
    super(PDUType.CONSULT_RESPONSE, new int[] {0,0,0,0});
    this.found    = found;
    this.clients  = clients;
    this.id       = id;
    this.ip       = ip;
    this.port     = port;
  }

  /**
   *  Parameterized constructor.
   *
   *  @param  version   The PDU version.
   *  @param  security  If set to 1 security will be used to transport the PDU. If set to
   *                    0 it won't.
   *  @param  options   PDU Option values, array with 4 ints.
   *  @param  found     If the file exists and was found this should be set to 1, otherwise it
   *                    should be set to 0.
   *  @param  clients   Number of clients that have the music file.
   *  @param  id        The id of the client who has the music file.
   *  @param  ip        The ip of the client who has the music file.
   *  @param  port      The client's port where PDUs should be sent.
   */
  public ConsultResponsePDU (int version, int security, int[] options, int found,
      int clients, String id, String ip, int port) {
    super(version, security, PDUType.CONSULT_RESPONSE, options);
    this.found    = found;
    this.clients  = clients;
    this.id       = id;
    this.ip       = ip;
    this.port     = port;
  }

  // Getters.

  /** @return True if the file was found. */
  public boolean wasFound() { return this.found == 1; }

  /** @return The number of clients that have the file. */
  public int getClients() { return this.clients; }

  /** @return The client's id. */
  public String getID() { return this.id.toString(); }

  /** @return The client's ip address. */
  public String getIP() { return this.ip.toString(); }

  /** @return The client's port. */
  public int getPort() { return this.port; }

  // Setters.

  /**
   *  Change the found value.
   *
   *  @param  found Set to 1 if the file was found, 0 otherwise.
   */
  public void setFound(int found) { this.found = found; }

  /**
   *  Change the number of clients that have the music file.
   *
   *  @param  client  Number of clients that have the music file.
   */
  public void setClients(int clients) { this.clients = clients; }

  /**
   *  Change the client's id.
   *
   *  @param  id  The client's id.
   */
  public void setID(String id) { this.id = id.toString(); }

  /**
   *  Change the client's ip.
   *
   *  @param  ip  The client's ip.
   */
  public void setIP(String ip) { this.ip = ip.toString(); }

  /**
   *  Change the client's port.
   *
   *  @param  port  The client's port.
   */
  public void setPort(int port) { this.port = port; }
}
