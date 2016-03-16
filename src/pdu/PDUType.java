package pdu;

public enum PDUType {
  REGISTER(1), CONSULT_REQUEST(2), CONSULT_RESPONSE(3), PROBE_REQUEST(4),
  PROBE_RESPONSE(5), REQUEST(6), DATA(7);

  final public int id;

  private PDUType(int id) {
    this.id = id;
  }
}
